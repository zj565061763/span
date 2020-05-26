package com.sd.lib.span;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.style.DynamicDrawableSpan;

/**
 * draw和getSize方法参考安卓原生的{@link DynamicDrawableSpan}实现
 */
class SimpleImageSpanHelper implements ImageSpanHelper
{
    private final DynamicDrawableSpan mSpan;

    private VerticalAlignType mVerticalAlignType = VerticalAlignType.ALIGN_BOTTOM;

    private Integer mWidth;
    private Integer mHeight;

    private int mMarginLeft;
    private int mMarginRight;
    private int mMarginBottom;

    public SimpleImageSpanHelper(DynamicDrawableSpan span)
    {
        mSpan = span;
    }

    @Override
    public void setWidth(Integer width)
    {
        mWidth = width;
    }

    @Override
    public void setHeight(Integer height)
    {
        mHeight = height;
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
    public void setMarginBottom(int marginBottom)
    {
        mMarginBottom = marginBottom;
    }

    @Override
    public void setVerticalAlignType(VerticalAlignType verticalAlignType)
    {
        if (verticalAlignType != null)
            mVerticalAlignType = verticalAlignType;
    }

    /**
     * 处理drawable的大小
     *
     * @param drawable
     */
    public void processDrawable(Drawable drawable)
    {
        if (drawable == null)
            return;

        if (mWidth != null && mHeight != null)
        {
            drawable.setBounds(0, 0, mWidth, mHeight);
        } else if (mWidth != null)
        {
            final int scaleWidth = drawable.getIntrinsicWidth();
            final int scaleHeight = drawable.getIntrinsicHeight();
            if (scaleWidth == 0)
                return;

            final int targetHeight = scaleHeight * mWidth / scaleWidth;
            drawable.setBounds(0, 0, mWidth, targetHeight);
        } else if (mHeight != null)
        {
            final int scaleWidth = drawable.getIntrinsicWidth();
            final int scaleHeight = drawable.getIntrinsicHeight();
            if (scaleHeight == 0)
                return;

            final int targetWidth = scaleWidth * mHeight / scaleHeight;
            drawable.setBounds(0, 0, targetWidth, mHeight);
        }
    }

    /**
     * {@link DynamicDrawableSpan#draw(Canvas, CharSequence, int, int, float, int, int, int, Paint)}
     */
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint)
    {
        Drawable b = mSpan.getDrawable();
        canvas.save();

        int transY = bottom - b.getBounds().bottom; //底部对齐的参数

        if (mVerticalAlignType == VerticalAlignType.ALIGN_BASELINE)
        {
            transY -= paint.getFontMetricsInt().descent;
        }

        transY -= mMarginBottom;

        canvas.translate(x + mMarginLeft, transY);
        b.draw(canvas);
        canvas.restore();
    }

    /**
     * {@link DynamicDrawableSpan#getSize(Paint, CharSequence, int, int, Paint.FontMetricsInt)}
     */
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm)
    {
        Drawable d = mSpan.getDrawable();
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
}
