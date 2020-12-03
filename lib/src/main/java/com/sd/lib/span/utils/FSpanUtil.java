package com.sd.lib.span.utils;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;

public class FSpanUtil
{
    private FSpanUtil()
    {
    }

    /**
     * 添加Span
     *
     * @param builder
     * @param key
     * @param spans
     */
    public static void appendSpan(SpannableStringBuilder builder, String key, Object... spans)
    {
        if (TextUtils.isEmpty(key))
            return;

        if (spans == null || spans.length <= 0)
            return;

        builder.append(key);
        final int end = builder.length();
        final int start = end - key.length();
        for (Object item : spans)
        {
            builder.setSpan(item, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }

    /**
     * 叠加span
     *
     * @param builder
     * @param oldSpan
     * @param spans
     */
    public static void overlyingSpan(SpannableStringBuilder builder, Object oldSpan, Object... spans)
    {
        if (oldSpan == null)
            return;

        if (spans == null || spans.length <= 0)
            return;

        final int end = builder.getSpanEnd(oldSpan);
        final int start = builder.getSpanStart(oldSpan);
        for (Object item : spans)
        {
            builder.setSpan(item, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }
}
