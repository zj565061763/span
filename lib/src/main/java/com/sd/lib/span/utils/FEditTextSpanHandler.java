package com.sd.lib.span.utils;

import android.text.Editable;
import android.text.Spannable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;
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

        mEditText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
                final int delta = after - count;
                final int end = start + Math.abs(delta);
                removeSpanInternal(start, end, false);
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

    private EditText getEditText()
    {
        return mEditText;
    }

    /**
     * 在光标位置插入span
     *
     * @param span span对象
     * @param key  对应的key
     * @return
     */
    public final SpanInfo insertSpan(String key, Object span)
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

        final SpanInfo spanInfo = new SpanInfo();
        spanInfo.span = span;
        spanInfo.start = start;
        spanInfo.end = end;

        mMapSpan.put(span, "");
        onSpanInsert(spanInfo);

        return spanInfo;
    }

    /**
     * 移除光标或者光标前面位置的span
     *
     * @return
     */
    public final boolean removeSpan()
    {
        final int selectionStart = getEditText().getSelectionStart();
        final int selectionEnd = getEditText().getSelectionEnd();
        if (selectionStart == selectionEnd)
        {
            final int count = removeSpanInternal(selectionStart, selectionEnd, true);
            return count > 0;
        }

        return false;
    }

    private int removeSpanInternal(int selectionStart, int selectionEnd, boolean removeText)
    {
        int count = 0;
        final List<SpanInfo> list = getSpanInfo(selectionStart, selectionEnd);
        for (SpanInfo item : list)
        {
            getEditText().getText().removeSpan(item.getSpan());
            if (removeText)
                getEditText().getText().delete(item.getStart(), item.getEnd());

            mMapSpan.remove(item);
            count++;

            onSpanRemove(item);
        }
        return count;
    }

    /**
     * 返回光标或者光标前面位置的span信息
     *
     * @param index
     * @return
     */
    public final SpanInfo getSpanInfo(int index)
    {
        final List<SpanInfo> list = getSpanInfo(index, index);
        if (list == null || list.isEmpty())
            return null;

        return list.get(0);
    }

    /**
     * 返回所有span信息
     *
     * @return
     */
    public final List<SpanInfo> getAllSpanInfo()
    {
        final int selectionStart = 0;
        final int selectionEnd = getEditText().getText().length();
        return getSpanInfo(selectionStart, selectionEnd);
    }

    private List<SpanInfo> getSpanInfo(int selectionStart, int selectionEnd)
    {
        final List<SpanInfo> list = new ArrayList<>();
        for (Object item : mMapSpan.keySet())
        {
            final int spanStart = getEditText().getText().getSpanStart(item);
            final int spanEnd = getEditText().getText().getSpanEnd(item);
            if (spanStart < 0 || spanEnd < 0)
            {
                mMapSpan.remove(item);
                continue;
            }

            if (checkBounds(spanStart, spanEnd, selectionStart, selectionEnd))
            {
                final SpanInfo spanInfo = new SpanInfo();
                spanInfo.span = item;
                spanInfo.start = spanStart;
                spanInfo.end = spanEnd;
                list.add(spanInfo);
            }
        }
        return list;
    }

    protected void onSpanRemove(SpanInfo spanInfo)
    {
    }

    protected void onSpanInsert(SpanInfo spanInfo)
    {
    }

    private static boolean checkBounds(int spanStart, int spanEnd, int selectionStart, int selectionEnd)
    {
        if (selectionStart == selectionEnd)
        {
            final int index = selectionStart;
            return index > spanStart && index <= spanEnd;
        } else
        {
            return !(selectionEnd <= spanStart || spanEnd <= selectionStart);
        }
    }

    public static class SpanInfo
    {
        private Object span;
        private int start;
        private int end;

        public Object getSpan()
        {
            return span;
        }

        public int getStart()
        {
            return start;
        }

        public int getEnd()
        {
            return end;
        }
    }
}
