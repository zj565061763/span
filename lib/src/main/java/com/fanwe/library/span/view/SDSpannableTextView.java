package com.fanwe.library.span.view;

import android.content.Context;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.widget.TextView;

import com.fanwe.library.span.SDSpannableStringBuilder;

public class SDSpannableTextView extends TextView
{
    public SDSpannableTextView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public SDSpannableTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public SDSpannableTextView(Context context)
    {
        super(context);
        init();
    }

    private void init()
    {
    }

    public void setSpanClickable(boolean clickable)
    {
        if (clickable)
        {
            setMovementMethod(LinkMovementMethod.getInstance());
        } else
        {
            setMovementMethod(getDefaultMovementMethod());
        }
    }

    @Override
    public void setText(CharSequence text, BufferType type)
    {
        if (text instanceof SDSpannableStringBuilder)
        {
            SDSpannableStringBuilder builder = (SDSpannableStringBuilder) text;
            processSpannableStringBuilder(builder);
        }
        super.setText(text, type);
    }

    public void processSpannableStringBuilder(SDSpannableStringBuilder builder)
    {

    }

}
