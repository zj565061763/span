package com.fanwe.library.span;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.style.ImageSpan;


/**
 * Created by Administrator on 2017/7/10.
 */

public class SDImageSpan extends ImageSpan implements IImageSpanHelper
{
    public SDImageSpan(Bitmap b)
    {
        super(b);
    }

    public SDImageSpan(Bitmap b, int verticalAlignment)
    {
        super(b, verticalAlignment);
    }

    public SDImageSpan(Context context, Bitmap b)
    {
        super(context, b);
    }

    public SDImageSpan(Context context, Bitmap b, int verticalAlignment)
    {
        super(context, b, verticalAlignment);
    }

    public SDImageSpan(Drawable d)
    {
        super(d);
    }

    public SDImageSpan(Drawable d, int verticalAlignment)
    {
        super(d, verticalAlignment);
    }

    public SDImageSpan(Drawable d, String source)
    {
        super(d, source);
    }

    public SDImageSpan(Drawable d, String source, int verticalAlignment)
    {
        super(d, source, verticalAlignment);
    }

    public SDImageSpan(Context context, Uri uri)
    {
        super(context, uri);
    }

    public SDImageSpan(Context context, Uri uri, int verticalAlignment)
    {
        super(context, uri, verticalAlignment);
    }

    public SDImageSpan(Context context, int resourceId)
    {
        super(context, resourceId);
    }

    public SDImageSpan(Context context, int resourceId, int verticalAlignment)
    {
        super(context, resourceId, verticalAlignment);
    }

    private ImageSpanHelper mImageSpanHelper;

    private ImageSpanHelper getImageSpanHelper()
    {
        if (mImageSpanHelper == null)
        {
            mImageSpanHelper = new ImageSpanHelper(this);
        }
        return mImageSpanHelper;
    }

    @Override
    public Drawable getDrawable()
    {
        Drawable drawable = super.getDrawable();
        getImageSpanHelper().processSize(drawable);
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
    public void setWidth(int width)
    {
        getImageSpanHelper().setWidth(width);
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
    public void setVerticalAlignType(VerticalAlignType alignType)
    {
        getImageSpanHelper().setVerticalAlignType(alignType);
    }
}
