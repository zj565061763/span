package com.sd.lib.span.utils;

import android.text.Editable;
import android.text.Spannable;
import android.text.TextWatcher;
import android.view.KeyEvent;
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
    public final void insertSpan(String key, Object span)
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
    }

    /**
     * 移除光标前面的span
     *
     * @return
     */
    public final int removeSpan()
    {
        final int selectionStart = getEditText().getSelectionStart();
        final int selectionEnd = getEditText().getSelectionEnd();
        return removeSpanInternal(selectionStart, selectionEnd);
    }

    /**
     * 返回Span信息
     *
     * @param selectionStart
     * @param selectionEnd
     * @return
     */
    public final List<SpanInfo> getSpanInfo(int selectionStart, int selectionEnd)
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

    private int removeSpanInternal(int selectionStart, int selectionEnd)
    {
        int count = 0;
        final List<SpanInfo> list = getSpanInfo(selectionStart, selectionEnd);
        for (SpanInfo item : list)
        {
            getEditText().getText().removeSpan(item.getSpan());
            mMapSpan.remove(item);
            count++;

            onSpanRemove(item);
        }
        return count;
    }

    /**
     * 分发按键事件
     *
     * @param keyCode
     * @param event
     * @return
     */
    public final boolean dispatchKeyEvent(int keyCode, KeyEvent event)
    {
        if (event.getAction() != KeyEvent.ACTION_DOWN)
            return false;

        if (checkKeyDelete(keyCode))
            return true;

        return false;
    }

    private boolean checkKeyDelete(int keyCode)
    {
        if (keyCode != KeyEvent.KEYCODE_DEL)
            return false;

        final Editable text = getEditText().getText();
        if (text.length() <= 0)
            return false;

        final int selectionStart = getEditText().getSelectionStart();
        final int selectionEnd = getEditText().getSelectionEnd();
        if (selectionStart == selectionEnd)
        {
            final int index = selectionStart;
            final List<FEditTextSpanHandler.SpanInfo> listInfo = getSpanInfo(index, index);
            if (listInfo.size() > 0)
            {
                final FEditTextSpanHandler.SpanInfo spanInfo = listInfo.get(0);
                getEditText().setSelection(spanInfo.getStart(), spanInfo.getEnd());
                return true;
            }
        }
        return false;
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
