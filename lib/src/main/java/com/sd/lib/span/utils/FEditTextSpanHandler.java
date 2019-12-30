package com.sd.lib.span.utils;

import android.text.Spannable;
import android.widget.EditText;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FEditTextSpanHandler
{
    private final EditText mEditText;
    private final Map<Object, String> mMapSpan = new ConcurrentHashMap<>();

    private Callback mCallback;

    public FEditTextSpanHandler(EditText editText)
    {
        if (editText == null)
            throw new NullPointerException();
        mEditText = editText;
    }

    public void setCallback(Callback callback)
    {
        mCallback = callback;
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

        if (mCallback != null)
            mCallback.onSpanInsert(span, start, end);
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
        if (selectionStart == selectionEnd)
        {
            for (Object item : mMapSpan.keySet())
            {
                final int spanStart = getEditText().getText().getSpanStart(item);
                final int spanEnd = getEditText().getText().getSpanEnd(item);

                if (selectionStart >= spanStart && selectionStart <= spanEnd)
                {
                    getEditText().getText().removeSpan(item);
                    mMapSpan.remove(item);
                    count++;

                    if (mCallback != null)
                        mCallback.onSpanRemove(item, spanStart, spanEnd);
                }
            }
        } else
        {
            for (Object item : mMapSpan.keySet())
            {
                final int spanStart = getEditText().getText().getSpanStart(item);
                final int spanEnd = getEditText().getText().getSpanEnd(item);

                if (selectionEnd < spanStart || spanEnd < selectionStart)
                {
                    continue;
                } else
                {
                    getEditText().getText().removeSpan(item);
                    mMapSpan.remove(item);
                    count++;

                    if (mCallback != null)
                        mCallback.onSpanRemove(item, spanStart, spanEnd);
                }
            }
        }

        return count;
    }

    public interface Callback
    {
        void onSpanInsert(Object span, int start, int end);

        void onSpanRemove(Object span, int start, int end);
    }
}
