package com.sd.lib.span.utils;

import android.text.Spannable;
import android.util.SparseArray;
import android.widget.EditText;

public class FEditTextSpanHandler
{
    private final EditText mEditText;
    private final SparseArray<Object> mArrSpan = new SparseArray<>();

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
        final int start = getEditText().getSelectionStart();
        final int end = start + key.length();

        getEditText().getText().append(key);
        getEditText().getText().setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        mArrSpan.put(end, span);
    }

    /**
     * 移除光标前面的span
     *
     * @return
     */
    public boolean removeSpan()
    {
        final int index = getEditText().getSelectionStart();
        final Object span = mArrSpan.get(index);
        if (span == null)
            return false;

        final int start = getEditText().getText().getSpanStart(span);
        final int end = getEditText().getText().getSpanEnd(span);

        getEditText().getText().delete(start, end);
        mArrSpan.remove(index);

        return true;
    }
}
