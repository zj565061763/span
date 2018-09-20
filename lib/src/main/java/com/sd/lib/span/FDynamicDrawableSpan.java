package com.sd.lib.span;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.style.DynamicDrawableSpan;
import android.view.View;

import java.lang.ref.WeakReference;

public abstract class FDynamicDrawableSpan extends DynamicDrawableSpan implements ImageSpanHelper
{
    private final WeakReference<View> mView;
    private SimpleImageSpanHelper mImageSpanHelper;

    public FDynamicDrawableSpan(View view)
    {
        mView = new WeakReference<>(view);
    }

    /**
     * span依附的view
     *
     * @return
     */
    public View getView()
    {
        return mView.get();
    }

    public Context getContext()
    {
        final View view = getView();
        return view == null ? null : view.getContext();
    }

    private SimpleImageSpanHelper getImageSpanHelper()
    {
        if (mImageSpanHelper == null)
            mImageSpanHelper = new SimpleImageSpanHelper(this);
        return mImageSpanHelper;
    }

    /**
     * 返回默认的图片资源id
     *
     * @return
     */
    protected abstract int getDefaultDrawableResId();

    /**
     * 返回图片的bitmap对象
     *
     * @return
     */
    protected abstract Bitmap onGetBitmap();

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

        getImageSpanHelper().processDrawable(drawable);
        return drawable;
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint)
    {
        getImageSpanHelper().draw(canvas, text, start, end, x, top, y, bottom, paint);
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, FontMetricsInt fm)
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
