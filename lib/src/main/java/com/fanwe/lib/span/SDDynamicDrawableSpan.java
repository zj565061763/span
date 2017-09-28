/*
 * Copyright (C) 2017 zhengjun, fanwe (http://www.fanwe.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fanwe.lib.span;

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

public abstract class SDDynamicDrawableSpan extends DynamicDrawableSpan implements IImageSpanHelper
{
    private WeakReference<View> mView;

    /**
     * @param view span要依附的view
     */
    public SDDynamicDrawableSpan(View view)
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
        View view = getView();
        if (view != null)
        {
            return view.getContext();
        } else
        {
            return null;
        }
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

        getImageSpanHelper().processSize(drawable);
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
