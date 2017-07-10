package com.fanwe.library.span.view;

import android.content.Context;
import android.text.Spannable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.widget.EditText;

import com.fanwe.library.span.MatcherInfo;

public class SDSpannableEdittext extends EditText
{

    private SparseArray<SpanInfo> mArrSpan = new SparseArray<>();

    public SDSpannableEdittext(Context context)
    {
        this(context, null);
    }

    public SDSpannableEdittext(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    private void init()
    {
    }

    public void insertSpan(Object span, String key)
    {
        SpanInfo spanInfo = new SpanInfo(span);

        MatcherInfo matcherInfo = new MatcherInfo();
        matcherInfo.setKey(key);

        spanInfo.matcherInfo = matcherInfo;

        insertSpan(spanInfo);
    }

    private void insertSpan(SpanInfo spanInfo)
    {
        MatcherInfo matcherInfo = spanInfo.matcherInfo;
        Object span = spanInfo.span;

        matcherInfo.setStart(getSelectionStart());
        insert(matcherInfo.getKey());
        getText().setSpan(span, matcherInfo.getStart(), matcherInfo.getEnd(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mArrSpan.put(matcherInfo.getEnd(), spanInfo);
    }

    private void insert(String text)
    {
        int index = getSelectionStart();
        getText().insert(index, text);
    }

    public void delete()
    {
        int index = getSelectionStart();
        SpanInfo spanInfo = mArrSpan.get(index);
        if (spanInfo != null)
        {
            MatcherInfo matcherInfo = spanInfo.matcherInfo;
            getText().delete(matcherInfo.getStart(), matcherInfo.getEnd());
            mArrSpan.remove(index);
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
