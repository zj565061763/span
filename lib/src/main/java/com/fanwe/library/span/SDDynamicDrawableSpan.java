package com.fanwe.library.span;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.style.DynamicDrawableSpan;
import android.view.View;

public abstract class SDDynamicDrawableSpan extends DynamicDrawableSpan
{
    private View mView;

    public SDDynamicDrawableSpan(View view)
    {
        mView = view;
    }

    public View getView()
    {
        return mView;
    }

    public Context getContext()
    {
        return mView.getContext();
    }

    protected abstract int getDefaultDrawableResId();

    protected abstract Bitmap onGetBitmap();

    protected void beforeReturnDrawable(Drawable drawable)
    {

    }

    @Override
    public Drawable getDrawable()
    {
        Drawable drawable = null;

        Bitmap bitmap = onGetBitmap();
        if (bitmap != null)
        {
            drawable = new BitmapDrawable(getContext().getResources(), bitmap);
        } else
        {
            int drawableResIdDefault = getDefaultDrawableResId();
            if (drawableResIdDefault != 0)
            {
                drawable = new BitmapDrawable(getContext().getResources(), getContext().getResources().openRawResource(drawableResIdDefault));
            }
        }

        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        drawable.setBounds(0, 0, width, height);
        beforeReturnDrawable(drawable);
        return drawable;
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint)
    {
        Drawable d = getDrawable();
        Rect rect = d.getBounds();
        canvas.save();
        int transY = bottom - rect.bottom;
        if (mVerticalAlignment == ALIGN_BASELINE)
        {
            transY -= paint.getFontMetricsInt().descent;
        }
        canvas.translate(x, transY);
        d.draw(canvas);
        canvas.restore();
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, FontMetricsInt fm)
    {
        Drawable d = getDrawable();
        Rect rect = d.getBounds();
        if (fm != null)
        {
            fm.ascent = -rect.bottom;
            fm.descent = 0;

            fm.top = fm.ascent;
            fm.bottom = 0;
        }
        return rect.right;
    }

}
