package com.sd.lib.span.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FTextPattern
{
    private final Map<MatchCallback, String> mCallbacks = new ConcurrentHashMap<>();

    /**
     * 添加正则表达式匹配回调
     *
     * @param callback 回调
     */
    public void addMatchCallback(MatchCallback callback)
    {
        if (callback == null)
            return;

        mCallbacks.put(callback, "");
    }

    /**
     * 移除正则表达式匹配回调
     *
     * @param callback
     */
    public void removeMatchCallback(MatchCallback callback)
    {
        mCallbacks.remove(callback);
    }

    /**
     * 开始处理
     *
     * @param content
     * @return
     */
    public SpannableStringBuilder process(CharSequence content)
    {
        if (mCallbacks.isEmpty())
            return null;

        if (content == null || TextUtils.isEmpty(content.toString()))
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

        for (MatchCallback item : mCallbacks.keySet())
        {
            final String regex = item.getRegex();
            if (TextUtils.isEmpty(regex))
                continue;

            final Matcher matcher = Pattern.compile(regex).matcher(builder);
            item.onMatchStart(this);
            while (matcher.find())
            {
                item.onMatch(matcher, builder);
            }
            item.onMatchFinish(this);
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
         *
         * @param pattern
         */
        public void onMatchStart(FTextPattern pattern)
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
         *
         * @param pattern
         */
        public void onMatchFinish(FTextPattern pattern)
        {
        }
    }

    /**
     * 替换字符串回调
     */
    public static abstract class ReplaceCallback extends MatchCallback
    {
        private int mDeltaStart = 0;

        @Override
        public void onMatchStart(FTextPattern pattern)
        {
            super.onMatchStart(pattern);
            mDeltaStart = 0;
        }

        @Override
        public void onMatch(Matcher matcher, SpannableStringBuilder builder)
        {
            final String key = matcher.group();
            final int start = matcher.start() + mDeltaStart;
            final int end = matcher.end() + mDeltaStart;

            final String replaceContent = getReplaceContent(key);
            if (!TextUtils.isEmpty(replaceContent))
            {
                builder.replace(start, end, replaceContent);

                final int delta = replaceContent.length() - key.length();
                mDeltaStart += delta;

                final int replaceEnd = start + replaceContent.length();
                processReplaceContent(key, start, replaceEnd, builder);
            }
        }

        /**
         * 返回要替换的内容
         *
         * @param target
         * @return
         */
        protected abstract String getReplaceContent(String target);

        /**
         * 处理替换内容
         *
         * @param target  被替换的字符串
         * @param start   替换后的目标字符串开始位置
         * @param end     替换后的目标字符串结束位置
         * @param builder
         */
        protected abstract void processReplaceContent(String target, int start, int end, SpannableStringBuilder builder);
    }

    /**
     * 替换@数字的内容
     */
    public static abstract class ReplaceAtNumberCallback extends ReplaceCallback
    {
        @Override
        public String getRegex()
        {
            return "@\\d{1,}";
        }
    }

    /**
     * 匹配中括号内容
     */
    public static abstract class BracketCallback extends MatchCallback
    {
        @Override
        public String getRegex()
        {
            return "\\[([^\\[\\]]+)\\]";
        }

        @Override
        public void onMatch(Matcher matcher, SpannableStringBuilder builder)
        {
            final String key = matcher.group();
            final int start = matcher.start();
            final int end = matcher.end();

            // 截取中括号中的名称
            final String name = key.substring(1, key.length() - 1);
            onMatchBracket(name, start, end, builder);
        }

        protected abstract void onMatchBracket(String name, int start, int end, SpannableStringBuilder builder);
    }

    /**
     * 匹配中括号中的内容对应的图片资源id
     */
    public static abstract class BracketResDrawableCallback extends BracketCallback
    {
        private final Context mContext;

        public BracketResDrawableCallback(Context context)
        {
            mContext = context;
        }

        private static int getIdentifierForDrawable(String drawableName, Context context)
        {
            try
            {
                return context.getResources().getIdentifier(drawableName, "drawable", context.getPackageName());
            } catch (Exception e)
            {
                e.printStackTrace();
                return 0;
            }
        }

        @Override
        protected final void onMatchBracket(String name, int start, int end, SpannableStringBuilder builder)
        {
            final int resId = getIdentifierForDrawable(name, mContext);
            if (resId != 0)
            {
                Drawable drawable = null;
                try
                {
                    drawable = mContext.getResources().getDrawable(resId);
                } catch (Exception e)
                {
                    e.printStackTrace();
                }

                if (drawable != null)
                    onMatchResDrawable(name, start, end, resId, builder);
            }
        }

        protected abstract void onMatchResDrawable(String name, int start, int end, int resId, SpannableStringBuilder builder);
    }
}
