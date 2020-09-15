package com.sd.lib.span.utils;

import android.text.Editable;
import android.text.Spannable;
import android.text.TextUtils;
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
                removeSpanInfo(list, false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int beforeCount, int afterCount)
            {
                final String content = s.toString();
                if (TextUtils.isEmpty(content))
                    mMapSpan.clear();
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

        final SpanInfo spanInfo = new SpanInfo(span);
        spanInfo.start = start;
        spanInfo.end = end;

        mMapSpan.put(span, "");
        onSpanInsert(spanInfo);

        return spanInfo;
    }

    /**
     * 移除span
     *
     * @param span
     * @param removeText true-移除span对应的文字
     * @return
     */
    public final boolean removeSpan(Object span, boolean removeText)
    {
        return removeSpanObject(span, removeText);
    }

    /**
     * 移除光标或者光标前面位置的span
     *
     * @return
     */
    public final boolean removeCursorSpan()
    {
        final SpanInfo spanInfo = getCursorSpanInfo();
        if (spanInfo != null)
            return removeSpanObject(spanInfo.getSpan(), true);

        return false;
    }

    /**
     * 返回光标或者光标前面的span信息
     *
     * @return
     */
    public final SpanInfo getCursorSpanInfo()
    {
        final int selectionStart = getEditText().getSelectionStart();
        final int selectionEnd = getEditText().getSelectionEnd();
        if (selectionStart == selectionEnd)
            return getSpanInfo(selectionStart);

        return null;
    }

    /**
     * 返回指定位置或者指定位置前面的span信息
     *
     * @param index
     * @return
     */
    private SpanInfo getSpanInfo(int index)
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

    /**
     * 模拟点击删除按钮
     *
     * @return
     */
    public boolean pressDeleteKey()
    {
        final int length = getEditText().getText().length();
        if (length <= 0)
            return false;

        if (removeCursorSpan())
        {
            // span被移除
        } else
        {
            final int selectionStart = getEditText().getSelectionStart();
            final int selectionEnd = getEditText().getSelectionEnd();
            if (selectionStart == selectionEnd)
            {
                getEditText().getText().delete(selectionStart - 1, selectionStart);
            } else
            {
                getEditText().getText().delete(selectionStart, selectionEnd);
            }
        }
        return true;
    }

    /**
     * 选中光标之前的span
     *
     * @return true-选中
     */
    public boolean selectCursorSpan()
    {
        final SpanInfo spanInfo = getCursorSpanInfo();
        if (spanInfo != null)
        {
            getEditText().setSelection(spanInfo.getStart(), spanInfo.getEnd());
            return true;
        }
        return false;
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

            if (checkSpanBounds(spanStart, spanEnd, selectionStart, selectionEnd, includeEnd))
            {
                final SpanInfo spanInfo = new SpanInfo(span);
                spanInfo.start = spanStart;
                spanInfo.end = spanEnd;
                list.add(spanInfo);
            }
        }
        return list;
    }

    private int removeSpanInfo(List<SpanInfo> list, boolean removeText)
    {
        int count = 0;
        for (SpanInfo item : list)
        {
            if (removeSpanObject(item.getSpan(), removeText))
                count++;
        }
        return count;
    }

    private boolean removeSpanObject(Object span, boolean removeText)
    {
        if (span == null)
            return false;

        if (mMapSpan.remove(span) != null)
        {
            final int start = getEditText().getText().getSpanStart(span);
            final int end = getEditText().getText().getSpanEnd(span);
            if (start >= 0 && end >= 0)
            {
                getEditText().getText().removeSpan(span);
                if (removeText)
                    getEditText().getText().delete(start, end);

                onSpanRemove(span);
                return true;
            }
        }
        return false;
    }

    /**
     * span添加回调
     *
     * @param spanInfo
     */
    protected void onSpanInsert(SpanInfo spanInfo)
    {
    }

    /**
     * span移除回调
     *
     * @param span
     */
    protected void onSpanRemove(Object span)
    {
    }

    /**
     * 检查光标范围是否包含span
     *
     * @param spanStart
     * @param spanEnd
     * @param selectionStart
     * @param selectionEnd
     * @param includeEnd
     * @return
     */
    private static boolean checkSpanBounds(int spanStart, int spanEnd, int selectionStart, int selectionEnd, boolean includeEnd)
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

    public static final class SpanInfo
    {
        private final Object span;
        private int start;
        private int end;

        public SpanInfo(Object span)
        {
            this.span = span;
        }

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
        public int hashCode()
        {
            return span.hashCode();
        }

        @Override
        public boolean equals(Object obj)
        {
            if (obj == this) return true;
            if (obj == null) return false;
            if (obj.getClass() != getClass()) return false;

            final SpanInfo other = (SpanInfo) obj;
            return getSpan().equals(other.getSpan());
        }
    }
}
