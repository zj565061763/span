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
            if (matcher != null)
            {
                while (matcher.find())
                {
                    item.onMatch(matcher, builder);
                }
            }
        }

        return builder;
    }

    public interface MatchCallback
    {
        /**
         * 返回正则表达式
         *
         * @return
         */
        String getRegex();

        /**
         * 正则表达式匹配回调
         *
         * @param matcher
         * @param builder
         */
        void onMatch(Matcher matcher, SpannableStringBuilder builder);
    }
}
