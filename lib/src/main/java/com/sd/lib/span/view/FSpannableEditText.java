package com.sd.lib.span.view;

import android.content.Context;
import android.text.Spannable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.widget.EditText;

import com.sd.lib.span.MatcherInfo;

public class FSpannableEditText extends EditText
{
    public FSpannableEditText(Context context)
    {
        super(context);
    }

    public FSpannableEditText(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    private SparseArray<SpanInfo> mArrSpan = new SparseArray<>();

    /**
     * 在光标位置插入span
     *
     * @param span span对象
     * @param key  对应的key
     */
    public void insertSpan(Object span, String key)
    {
        MatcherInfo matcherInfo = new MatcherInfo(key);

        SpanInfo spanInfo = new SpanInfo(span);
        spanInfo.matcherInfo = matcherInfo;
        insertSpan(spanInfo);
    }

    private void insertSpan(SpanInfo spanInfo)
    {
        MatcherInfo matcherInfo = spanInfo.matcherInfo;
        Object span = spanInfo.span;

        matcherInfo.setStart(getSelectionStart());
        append(matcherInfo.getKey());
        getText().setSpan(span, matcherInfo.getStart(), matcherInfo.getEnd(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mArrSpan.put(matcherInfo.getEnd(), spanInfo);
    }

    /**
     * 移除光标前面的span
     */
    public boolean removeSpan()
    {
        int index = getSelectionStart();
        SpanInfo spanInfo = mArrSpan.get(index);
        if (spanInfo != null)
        {
            MatcherInfo matcherInfo = spanInfo.matcherInfo;
            getText().delete(matcherInfo.getStart(), matcherInfo.getEnd());
            mArrSpan.remove(index);
            return true;
        } else
        {
            return false;
        }
    }

    private class SpanInfo
    {
        public Object span;
        public MatcherInfo matcherInfo;

        public SpanInfo(Object span)
        {
            this.span = span;
        }
    }
}
