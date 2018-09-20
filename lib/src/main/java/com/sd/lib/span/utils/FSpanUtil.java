package com.sd.lib.span.utils;

import android.text.SpannableStringBuilder;
import android.text.Spanned;

public class FSpanUtil
{
    private FSpanUtil()
    {
    }

    /**
     * 添加span
     *
     * @param key
     * @param span
     * @param flags   {@link Spanned#SPAN_EXCLUSIVE_EXCLUSIVE}
     * @param builder
     */
    public static void appendSpan(String key, Object span, int flags, SpannableStringBuilder builder)
    {
        builder.append(key);
        final int end = builder.length();
        final int start = end - key.length();
        builder.setSpan(span, start, end, flags);
    }

    /**
     * 叠加span
     *
     * @param oldSpan
     * @param newSpan
     * @param flags   {@link Spanned#SPAN_EXCLUSIVE_EXCLUSIVE}
     * @param builder
     */
    public void overlyingSpan(Object oldSpan, Object newSpan, int flags, SpannableStringBuilder builder)
    {
        final int end = builder.getSpanEnd(oldSpan);
        final int start = builder.getSpanStart(oldSpan);
        builder.setSpan(newSpan, start, end, flags);
    }
}
