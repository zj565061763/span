package com.fanwe.www.androidspan;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.text.style.ImageSpan;

/**
 * Created by Administrator on 2017/7/10.
 */

public class CustomImageSpan extends ImageSpan
{
    public CustomImageSpan(Bitmap b)
    {
        super(b);
    }

    public CustomImageSpan(Bitmap b, int verticalAlignment)
    {
        super(b, verticalAlignment);
    }

    public CustomImageSpan(Context context, Bitmap b)
    {
        super(context, b);
    }

    public CustomImageSpan(Context context, Bitmap b, int verticalAlignment)
    {
        super(context, b, verticalAlignment);
    }

    public CustomImageSpan(Drawable d)
    {
        super(d);
    }

    public CustomImageSpan(Drawable d, int verticalAlignment)
    {
        super(d, verticalAlignment);
    }

    public CustomImageSpan(Drawable d, String source)
    {
        super(d, source);
    }

    public CustomImageSpan(Drawable d, String source, int verticalAlignment)
    {
        super(d, source, verticalAlignment);
    }

    public CustomImageSpan(Context context, Uri uri)
    {
        super(context, uri);
    }

    public CustomImageSpan(Context context, Uri uri, int verticalAlignment)
    {
        super(context, uri, verticalAlignment);
    }

    public CustomImageSpan(Context context, @DrawableRes int resourceId)
    {
        super(context, resourceId);
    }

    public CustomImageSpan(Context context, @DrawableRes int resourceId, int verticalAlignment)
    {
        super(context, resourceId, verticalAlignment);
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint)
    {
        Drawable b = getDrawable();
        canvas.save();

        int transY = bottom - b.getBounds().bottom;
        if (mVerticalAlignment == ALIGN_BASELINE)
        {
            transY -= paint.getFontMetricsInt().descent;
        }

        canvas.translate(x, transY);
        b.draw(canvas);
        canvas.restore();
    }
}
