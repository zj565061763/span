package com.sd.lib.span.utils;

import android.text.Spannable;
import android.widget.EditText;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FEditTextSpanHandler
{
    private final EditText mEditText;
    private final Map<Object, String> mMapSpan = new ConcurrentHashMap<>();

    public FEditTextSpanHandler(EditText editText)
    {
        if (editText == null)
            throw new NullPointerException();
        mEditText = editText;
    }

    private EditText getEditText()
    {
        return mEditText;
    }

    /**
     * 在光标位置插入span
     *
     * @param span span对象
     * @param key  对应的key
     */
    public void insertSpan(String key, Object span)
    {
        final int selectionStart = getEditText().getSelectionStart();
        final int selectionEnd = getEditText().getSelectionEnd();

        if (selectionStart == selectionEnd)
        {
            getEditText().getText().insert(selectionStart, key);
        } else
        {
            getEditText().getText().replace(selectionStart, selectionEnd, key);
        }

        final int start = selectionStart;
        final int end = start + key.length();
        getEditText().getText().setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        mMapSpan.put(span, "");
    }

    /**
     * 移除光标前面的span
     *
     * @return
     */
    public int removeSpan()
    {
        final int selectionStart = getEditText().getSelectionStart();
        final int selectionEnd = getEditText().getSelectionEnd();

        int count = 0;
        for (Object item : mMapSpan.keySet())
        {
            final int spanStart = getEditText().getText().getSpanStart(item);
            if (spanStart >= selectionStart && spanStart <= selectionEnd)
            {
                getEditText().getText().removeSpan(item);
                mMapSpan.remove(item);
                count++;
                continue;
            }

            final int spanEnd = getEditText().getText().getSpanEnd(item);
            if (spanEnd >= selectionStart && spanEnd <= selectionEnd)
            {
                getEditText().getText().removeSpan(item);
                mMapSpan.remove(item);
                count++;
                continue;
            }
        }

        return count;
    }
}
