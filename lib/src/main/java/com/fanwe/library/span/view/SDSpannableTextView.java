package com.fanwe.library.span.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.fanwe.library.span.SDSpannableStringBuilder;

/**
 * Created by Administrator on 2017/7/13.
 */

public abstract class SDSpannableTextView extends TextView
{
    public SDSpannableTextView(Context context)
    {
        super(context);
    }

    public SDSpannableTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    public void setText(CharSequence text, BufferType type)
    {
        if (text != null)
        {
            SDSpannableStringBuilder sb = null;
            if (text instanceof SDSpannableStringBuilder)
            {
                sb = (SDSpannableStringBuilder) text;
            } else
            {
                sb = new SDSpannableStringBuilder();
                sb.append(text);
            }
            onProcessSpannableStringBuilder(sb);
            text = sb;
        }

        super.setText(text, type);
    }

    protected abstract void onProcessSpannableStringBuilder(SDSpannableStringBuilder builder);
}
