package com.sd.lib.span.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.sd.lib.span.FSpannableStringBuilder;

public abstract class FSpannableTextView extends TextView
{
    public FSpannableTextView(Context context)
    {
        super(context);
    }

    public FSpannableTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    public void setText(CharSequence text, BufferType type)
    {
        if (text != null)
        {
            FSpannableStringBuilder sb = null;
            if (text instanceof FSpannableStringBuilder)
            {
                sb = (FSpannableStringBuilder) text;
            } else
            {
                sb = new FSpannableStringBuilder();
                sb.append(text);
            }
            onProcessSpannableStringBuilder(sb);
            text = sb;
        }

        super.setText(text, type);
    }

    protected abstract void onProcessSpannableStringBuilder(FSpannableStringBuilder builder);
}
