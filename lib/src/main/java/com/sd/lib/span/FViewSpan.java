package com.sd.lib.span;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.style.ReplacementSpan;
import android.view.View;
import android.widget.FrameLayout;

public class FViewSpan extends ReplacementSpan
{
    private final FrameLayout mLayout;
    private Paint.FontMetricsInt mFontMetricsInt;

    public FViewSpan(View view)
    {
        mLayout = new FrameLayout(view.getContext());
        mLayout.addView(view);
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint)
    {
        canvas.save();
        canvas.translate(x, 0);
        mLayout.draw(canvas);
        canvas.restore();
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm)
    {
        if (fm != null)
            mFontMetricsInt = fm;

        final int height = getLineHeight();
        measureLayout(height);

        final int viewWidth = mLayout.getMeasuredWidth();
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
}
