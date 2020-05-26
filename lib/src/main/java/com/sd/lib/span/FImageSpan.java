package com.sd.lib.span;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.style.ImageSpan;

public class FImageSpan extends ImageSpan implements ImageSpanHelper
{
    public FImageSpan(Bitmap b)
    {
        super(b);
    }

    public FImageSpan(Bitmap b, int verticalAlignment)
    {
        super(b, verticalAlignment);
    }

    public FImageSpan(Context context, Bitmap b)
    {
        super(context, b);
    }

    public FImageSpan(Context context, Bitmap b, int verticalAlignment)
    {
        super(context, b, verticalAlignment);
    }

    public FImageSpan(Drawable d)
    {
        super(d);
    }

    public FImageSpan(Drawable d, int verticalAlignment)
    {
        super(d, verticalAlignment);
    }

    public FImageSpan(Drawable d, String source)
    {
        super(d, source);
    }

    public FImageSpan(Drawable d, String source, int verticalAlignment)
    {
        super(d, source, verticalAlignment);
    }

    public FImageSpan(Context context, Uri uri)
    {
        super(context, uri);
    }

    public FImageSpan(Context context, Uri uri, int verticalAlignment)
    {
        super(context, uri, verticalAlignment);
    }

    public FImageSpan(Context context, int resourceId)
    {
        super(context, resourceId);
    }

    public FImageSpan(Context context, int resourceId, int verticalAlignment)
    {
        super(context, resourceId, verticalAlignment);
    }

    private SimpleImageSpanHelper mImageSpanHelper;

    private SimpleImageSpanHelper getImageSpanHelper()
    {
        if (mImageSpanHelper == null)
            mImageSpanHelper = new SimpleImageSpanHelper(this);
        return mImageSpanHelper;
    }

    @Override
    public Drawable getDrawable()
    {
        final Drawable drawable = super.getDrawable();
        getImageSpanHelper().processDrawable(drawable);
        return drawable;
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint)
    {
        getImageSpanHelper().draw(canvas, text, start, end, x, top, y, bottom, paint);
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm)
    {
        return getImageSpanHelper().getSize(paint, text, start, end, fm);
    }

    @Override
    public void setWidth(Integer width)
    {
        getImageSpanHelper().setWidth(width);
    }

    @Override
    public void setHeight(Integer height)
    {
        getImageSpanHelper().setHeight(height);
    }

    @Override
    public void setMarginLeft(int marginLeft)
    {
        getImageSpanHelper().setMarginLeft(marginLeft);
    }

    @Override
    public void setMarginRight(int marginRight)
    {
        getImageSpanHelper().setMarginRight(marginRight);
    }

    @Override
    public void setMarginBottom(int marginBottom)
    {
        getImageSpanHelper().setMarginBottom(marginBottom);
    }

    @Override
    public void setVerticalAlignType(VerticalAlignType verticalAlignType)
    {
        getImageSpanHelper().setVerticalAlignType(verticalAlignType);
    }
}
