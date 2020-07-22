package com.sd.lib.span.utils;

import android.text.SpannableStringBuilder;
import android.text.TextUtils;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FTextPattern
{
    private final List<MatchCallback> mListCallback = new CopyOnWriteArrayList<>();

    /**
     * 添加正则表达式匹配回调
     *
     * @param callback 回调
     */
    public void addMatchCallback(MatchCallback callback)
    {
        if (callback == null)
            return;

        if (!mListCallback.contains(callback))
            mListCallback.add(callback);
    }

    /**
     * 移除正则表达式匹配回调
     *
     * @param callback
     */
    public void removeMatchCallback(MatchCallback callback)
    {
        mListCallback.remove(callback);
    }

    /**
     * 开始处理
     *
     * @param content
     * @return
     */
    public SpannableStringBuilder process(CharSequence content)
    {
        if (content == null || TextUtils.isEmpty(content.toString()))
            return null;

        if (mListCallback.isEmpty())
            return null;

        SpannableStringBuilder builder = null;
        if (content instanceof SpannableStringBuilder)
        {
            builder = (SpannableStringBuilder) content;
        } else
        {
            builder = new SpannableStringBuilder();
            builder.append(content);
        }

        for (MatchCallback item : mListCallback)
        {
            final String regex = item.getRegex();
            if (TextUtils.isEmpty(regex))
                continue;

            final Matcher matcher = Pattern.compile(regex).matcher(content);
            item.onMatchStart();
            while (matcher.find())
            {
                item.onMatch(matcher, builder);
            }
            item.onMatchFinish();
        }

        return builder;
    }

    public static abstract class MatchCallback
    {
        /**
         * 返回正则表达式
         *
         * @return
         */
        public abstract String getRegex();

        /**
         * 开始匹配回调
         */
        public void onMatchStart()
        {
        }

        /**
         * 正则表达式匹配回调
         *
         * @param matcher
         * @param builder
         */
        public abstract void onMatch(Matcher matcher, SpannableStringBuilder builder);

        /**
         * 结束匹配回调
         */
        public void onMatchFinish()
        {
        }
    }

    public static abstract class ReplaceAtNumberCallback extends MatchCallback
    {
        private int mDeltaStart = 0;

        @Override
        public String getRegex()
        {
            return "@\\d{1,}";
        }

        @Override
        public void onMatchStart()
        {
            super.onMatchStart();
            mDeltaStart = 0;
        }

        @Override
        public void onMatch(Matcher matcher, SpannableStringBuilder builder)
        {
            final String key = matcher.group();
            final int start = matcher.start() + mDeltaStart;
            final int end = matcher.end() + mDeltaStart;

            final String number = key.substring(1);
            final String replaceContent = getReplaceContent(number);
            if (!TextUtils.isEmpty(replaceContent))
            {
                final String replace = "@" + replaceContent;
                builder.replace(start, end, replace);
                final int replaceEnd = start + replace.length();

                final int delta = replace.length() - key.length();
                mDeltaStart += delta;

                processReplaceContent(number, start, replaceEnd, builder);
            }
        }

        /**
         * 返回要替换的内容
         *
         * @param number
         * @return
         */
        protected abstract String getReplaceContent(String number);

        /**
         * 处理替换内容
         *
         * @param number
         * @param start
         * @param end
         * @param builder
         */
        protected abstract void processReplaceContent(String number, int start, int end, SpannableStringBuilder builder);
    }
}
