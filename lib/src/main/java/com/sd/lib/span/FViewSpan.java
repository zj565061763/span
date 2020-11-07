package com.sd.lib.span;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.style.ReplacementSpan;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public class FViewSpan extends ReplacementSpan
{
    private static final String TAG = FViewSpan.class.getSimpleName();

    private final TextView mTextView;
    private final InternalLayout mLayout;
    private Paint.FontMetricsInt mFontMetricsInt;

    private boolean mHasDraw = false;

    public FViewSpan(View view, TextView textView)
    {
        mTextView = textView;
        mLayout = new InternalLayout(view.getContext());
        mLayout.addView(view);
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint)
    {
        Log.i(TAG, "draw " + FViewSpan.this);

        canvas.save();
        canvas.translate(x, 0);
        mLayout.draw(canvas);
        canvas.restore();

        mHasDraw = true;
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm)
    {
        if (fm != null)
            mFontMetricsInt = fm;

        final int height = getLineHeight();
        measureLayout(height);

        final int viewWidth = mLayout.getMeasuredWidth();

        Log.i(TAG, "getSize width" + viewWidth + " height:" + height + " " + FViewSpan.this);
        return viewWidth;
    }

    private int getLineHeight()
    {
        final Paint.FontMetricsInt fm = mFontMetricsInt;
        if (fm != null)
            return fm.bottom - fm.top;

        return 0;
    }

    private void measureLayout(int height)
    {
        final int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        final int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);
        mLayout.measure(widthMeasureSpec, heightMeasureSpec);
        mLayout.layout(0, 0, mLayout.getMeasuredWidth(), mLayout.getMeasuredHeight());
    }

    private final class InternalLayout extends FrameLayout
    {
        public InternalLayout(Context context)
        {
            super(context);
        }

        @Override
        public void requestLayout()
        {
            Log.i(TAG, "InternalLayout requestLayout mHasDraw:" + mHasDraw + " " + FViewSpan.this);
            super.requestLayout();

            if (mHasDraw)
            {
                final CharSequence text = mTextView.getText();
                mTextView.setText(text);
            }
        }
    }
}
