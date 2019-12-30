package com.sd.lib.span.utils;

import android.text.Editable;
import android.text.Spannable;
import android.text.TextWatcher;
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

        mEditText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
                final int delta = after - count;
                final int end = start + Math.abs(delta);
                removeSpanInternal(start, end);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });
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
        return removeSpanInternal(selectionStart, selectionEnd);
    }

    private int removeSpanInternal(int selectionStart, int selectionEnd)
    {
        int count = 0;
        for (Object item : mMapSpan.keySet())
        {
            final int spanStart = getEditText().getText().getSpanStart(item);
            final int spanEnd = getEditText().getText().getSpanEnd(item);

            if (checkBounds(spanStart, spanEnd, selectionStart, selectionEnd))
            {
                getEditText().getText().removeSpan(item);
                mMapSpan.remove(item);
                count++;

                if (mCallback != null)
                    mCallback.onSpanRemove(item, spanStart, spanEnd);
            }
        }
        return count;
    }

    private static boolean checkBounds(int spanStart, int spanEnd, int selectionStart, int selectionEnd)
    {
        if (selectionStart == selectionEnd)
        {
            final int index = selectionStart;
            return index > spanStart && index < spanEnd;
        } else
        {
            return !(selectionEnd <= spanStart || spanEnd <= selectionStart);
        }
    }

    public interface Callback
    {
        void onSpanInsert(Object span, int start, int end);

        void onSpanRemove(Object span, int start, int end);
    }
}
