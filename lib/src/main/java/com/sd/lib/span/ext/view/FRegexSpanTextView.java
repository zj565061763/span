package com.sd.lib.span.ext.view;

import android.content.Context;
import android.util.AttributeSet;

import com.sd.lib.span.utils.FRegexHandler;

public class FRegexSpanTextView extends FClickableSpanTextView
{
    public FRegexSpanTextView(Context context)
    {
        super(context);
    }

    public FRegexSpanTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public FRegexSpanTextView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    private FRegexHandler mRegexHandler;

    /**
     * {@link FRegexHandler}
     *
     * @return
     */
    public final FRegexHandler getRegexHandler()
    {
        if (mRegexHandler == null)
        {
            mRegexHandler = new FRegexHandler();
            onCreateRegexHandler(mRegexHandler);
        }
        return mRegexHandler;
    }

    /**
     * {@link FRegexHandler}创建回调
     *
     * @param handler
     */
    protected void onCreateRegexHandler(FRegexHandler handler)
    {
    }

    @Override
    public void setText(CharSequence text, BufferType type)
    {
        final CharSequence result = getRegexHandler().process(text);
        if (result != null)
            super.setText(result, type);
        else
            super.setText(text, type);
    }
}
