package com.sd.lib.span;

import android.content.Context;
import android.content.res.Resources;
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
        final Context context = getContext();
        if (context == null)
            return null;

        final Resources resources = context.getResources();
        Drawable drawable = null;

        final Bitmap bitmap = onGetBitmap();
        if (bitmap != null)
        {
            drawable = new BitmapDrawable(resources, bitmap);
        } else
        {
            final int defaultDrawableResId = getDefaultDrawableResId();
            if (defaultDrawableResId != 0)
                drawable = new BitmapDrawable(resources, resources.openRawResource(defaultDrawableResId));
        }

        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
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
