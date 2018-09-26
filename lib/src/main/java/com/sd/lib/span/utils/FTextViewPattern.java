package com.sd.lib.span.utils;

import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FTextViewPattern
{
    private SpannableStringBuilder mBuilder;
    private final List<MatchCallback> mListCallback = new CopyOnWriteArrayList<>();

    private SpannableStringBuilder getBuilder()
    {
        if (mBuilder == null)
            mBuilder = new SpannableStringBuilder();
        return mBuilder;
    }

    /**
     * 添加正则表达式匹配回调
     *
     * @param callback 回调
     */
    public void addMatchCallback(MatchCallback callback)
    {
        if (callback == null)
            throw new NullPointerException();

        if (!mListCallback.contains(callback))
            mListCallback.add(callback);
    }

    /**
     * 移除正则表达式匹配回调
     *
     * @param regex
     */
    public void removeMatchCallback(String regex)
    {
        mListCallback.remove(regex);
    }

    /**
     * 开始处理
     */
    public void process(TextView textView)
    {
        if (textView == null)
            return;

        if (mListCallback.isEmpty())
            return;

        final String content = textView.getText().toString();

        getBuilder().clear();
        getBuilder().append(content);

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
                    item.onMatch(matcher, getBuilder());
                }
            }
        }

        textView.setText(getBuilder());
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
