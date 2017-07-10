package com.fanwe.library.span;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.style.DynamicDrawableSpan;

/**
 * Created by Administrator on 2017/7/10.
 */

class ImageSpanHelper implements IImageSpanHelper
{
    private int mMarginLeft;
    private int mMarginRight;
    private int mWidth;

    private DynamicDrawableSpan mSpan;

    public ImageSpanHelper(DynamicDrawableSpan span)
    {
        mSpan = span;
    }

    public DynamicDrawableSpan getSpan()
    {
        return mSpan;
    }

    /**
     * 处理drawable的大小
     *
     * @param drawable
     */
    public void processSize(Drawable drawable)
    {
        if (drawable != null && mWidth > 0)
        {
            int scaleWidth = drawable.getIntrinsicWidth();
            int scaleHeight = drawable.getIntrinsicHeight();

            int targetHeight = scaleHeight * mWidth / scaleWidth;

            drawable.setBounds(0, 0, mWidth, targetHeight);
        }
    }

    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint)
    {
        Drawable b = getSpan().getDrawable();
        canvas.save();

        int transY = bottom - b.getBounds().bottom;
        if (getSpan().getVerticalAlignment() == DynamicDrawableSpan.ALIGN_BASELINE)
        {
            transY -= paint.getFontMetricsInt().descent;
        }

        canvas.translate(x + mMarginLeft, transY);
        b.draw(canvas);
        canvas.restore();
    }

    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm)
    {
        Drawable d = getSpan().getDrawable();
        Rect rect = d.getBounds();

        if (fm != null)
        {
            fm.ascent = -rect.bottom;
            fm.descent = 0;

            fm.top = fm.ascent;
            fm.bottom = 0;
        }

        return rect.right + mMarginLeft + mMarginRight;
    }

    @Override
    public void setWidth(int width)
    {
        mWidth = width;
    }

    @Override
    public void setMarginLeft(int marginLeft)
    {
        mMarginLeft = marginLeft;
    }

    @Override
    public void setMarginRight(int marginRight)
    {
        mMarginRight = marginRight;
    }
}
