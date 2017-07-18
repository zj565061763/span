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
    private VerticalAlignType mVerticalAlignType = VerticalAlignType.ALIGN_BASELINE;

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

    /**
     * Draws the span into the canvas.
     *
     * @param canvas Canvas into which the span should be rendered.
     * @param text   Current text.
     * @param start  Start character index for span.
     * @param end    End character index for span.
     * @param x      Edge of the replacement closest to the leading margin.
     * @param top    Top of the line.
     * @param y      Baseline.
     * @param bottom Bottom of the line.
     * @param paint  Paint instance.
     */
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint)
    {
        Drawable b = getSpan().getDrawable();
        canvas.save();

        int transY = 0;

        if (mVerticalAlignType == VerticalAlignType.ALIGN_BASELINE)
        {
            //默认对齐方式，不处理
        } else if (mVerticalAlignType == VerticalAlignType.ALIGN_BOTTOM)
        {
            transY = bottom - b.getBounds().bottom;
        }

        canvas.translate(x + mMarginLeft, transY);
        b.draw(canvas);
        canvas.restore();
    }

    /**
     * Returns the width of the span. Extending classes can set the height of the span by updating
     * attributes of {@link android.graphics.Paint.FontMetricsInt}. If the span covers the whole
     * text, and the height is not set,
     * {@link #draw(Canvas, CharSequence, int, int, float, int, int, int, Paint)} will not be
     * called for the span.
     *
     * @param paint Paint instance.
     * @param text  Current text.
     * @param start Start character index for span.
     * @param end   End character index for span.
     * @param fm    Font metrics, can be null.
     * @return Width of the span.
     */
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

    @Override
    public void setVerticalAlignType(VerticalAlignType alignType)
    {
        if (alignType == null)
        {
            return;
        }
        mVerticalAlignType = alignType;
    }
}
