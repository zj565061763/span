package com.sd.lib.span;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.style.DynamicDrawableSpan;

/**
 * draw和getSize方法参考安卓原生的{@link DynamicDrawableSpan}实现
 */
public class FImageSpanHelper
{
    private final DynamicDrawableSpan mSpan;

    private VerticalAlignType mVerticalAlignType = VerticalAlignType.ALIGN_BOTTOM;
    private int mMarginLeft;
    private int mMarginRight;
    private int mMarginBottom;
    private int mWidth;

    public FImageSpanHelper(DynamicDrawableSpan span)
    {
        mSpan = span;
    }

    /**
     * 设置宽度
     *
     * @param width
     */
    public void setWidth(int width)
    {
        mWidth = width;
    }

    /**
     * 设置左边间距
     *
     * @param marginLeft
     */
    public void setMarginLeft(int marginLeft)
    {
        mMarginLeft = marginLeft;
    }

    /**
     * 设置右边间距
     *
     * @param marginRight
     */
    public void setMarginRight(int marginRight)
    {
        mMarginRight = marginRight;
    }

    /**
     * 设置底部边距
     *
     * @param marginBottom
     */
    public void setMarginBottom(int marginBottom)
    {
        mMarginBottom = marginBottom;
    }

    /**
     * 设置竖直方向对齐方式
     *
     * @param verticalAlignType
     */
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
        if (mWidth <= 0)
            return;

        final int scaleWidth = drawable.getIntrinsicWidth();
        final int scaleHeight = drawable.getIntrinsicHeight();
        final int targetHeight = scaleHeight * mWidth / scaleWidth;

        drawable.setBounds(0, 0, mWidth, targetHeight);
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

    public enum VerticalAlignType
    {
        /**
         * 对齐字体的底部
         */
        ALIGN_BOTTOM,
        /**
         * 对齐字体的基准线
         */
        ALIGN_BASELINE,
    }
}
