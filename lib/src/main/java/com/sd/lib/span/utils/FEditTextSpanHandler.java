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
            public void beforeTextChanged(CharSequence s, int start, int beforeCount, int afterCount)
            {
                final int end = start + beforeCount;
                final List<SpanInfo> list = getSpanInfo(start, end, false);
                removeSpanInternal(list, false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int beforeCount, int afterCount)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });
    }

    public final EditText getEditText()
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
            final List<SpanInfo> list = getSpanInfo(selectionStart, selectionEnd, true);
            final int count = removeSpanInternal(list, true);
            return count > 0;
        }

        return false;
    }

    /**
     * 移除span
     *
     * @param span
     * @return
     */
    public final boolean removeSpan(Object span, boolean removeText)
    {
        if (span == null)
            return false;

        final SpanInfo spanInfo = new SpanInfo();
        spanInfo.span = span;
        spanInfo.start = getEditText().getText().getSpanStart(span);
        spanInfo.end = getEditText().getText().getSpanEnd(span);

        return removeSpanInternal(spanInfo, removeText);
    }

    /**
     * 返回光标或者光标前面位置的span信息
     *
     * @param index
     * @return
     */
    public final SpanInfo getSpanInfo(int index)
    {
        final List<SpanInfo> list = getSpanInfo(index, index, true);
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
        return getSpanInfo(selectionStart, selectionEnd, true);
    }

    private List<SpanInfo> getSpanInfo(int selectionStart, int selectionEnd, boolean includeEnd)
    {
        final List<SpanInfo> list = new ArrayList<>();
        for (Object span : mMapSpan.keySet())
        {
            final int spanStart = getEditText().getText().getSpanStart(span);
            final int spanEnd = getEditText().getText().getSpanEnd(span);
            if (spanStart < 0 || spanEnd < 0)
            {
                // notify onSpanRemove ?
                mMapSpan.remove(span);
                continue;
            }

            if (checkBounds(spanStart, spanEnd, selectionStart, selectionEnd, includeEnd))
            {
                final SpanInfo spanInfo = new SpanInfo();
                spanInfo.span = span;
                spanInfo.start = spanStart;
                spanInfo.end = spanEnd;
                list.add(spanInfo);
            }
        }
        return list;
    }

    private int removeSpanInternal(List<SpanInfo> list, boolean removeText)
    {
        int count = 0;
        for (SpanInfo item : list)
        {
            if (removeSpanInternal(item, removeText))
                count++;
        }
        return count;
    }

    private boolean removeSpanInternal(SpanInfo spanInfo, boolean removeText)
    {
        if (spanInfo == null)
            return false;

        final Object span = spanInfo.getSpan();
        if (mMapSpan.remove(span) != null)
        {
            getEditText().getText().removeSpan(span);
            if (removeText)
                getEditText().getText().delete(spanInfo.getStart(), spanInfo.getEnd());

            onSpanRemove(spanInfo);
            return true;
        }

        return false;
    }

    protected void onSpanRemove(SpanInfo spanInfo)
    {
    }

    protected void onSpanInsert(SpanInfo spanInfo)
    {
    }

    private static boolean checkBounds(int spanStart, int spanEnd, int selectionStart, int selectionEnd, boolean includeEnd)
    {
        if (selectionStart == selectionEnd)
        {
            final int index = selectionStart;

            final boolean checkStart = index > spanStart;
            final boolean checkEnd = includeEnd ? index <= spanEnd : index < spanEnd;
            return checkStart && checkEnd;
        } else
        {
            final boolean checkStart = selectionEnd <= spanStart;
            final boolean checkEnd = includeEnd ? selectionStart > spanEnd : selectionStart >= spanEnd;
            return !(checkStart || checkEnd);
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

        @Override
        public boolean equals(Object obj)
        {
            if (obj == this)
                return true;

            if (!(obj instanceof SpanInfo))
                return false;

            final SpanInfo other = (SpanInfo) obj;

            return getSpan().equals(other.getSpan());
        }
    }
}
