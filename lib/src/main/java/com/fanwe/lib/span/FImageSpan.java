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
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.style.ImageSpan;


/**
 * Created by zhengjun on 2017/7/10.
 */
public class FImageSpan extends ImageSpan implements FIImageSpanHelper
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

    private FImageSpanHelper mImageSpanHelper;

    private FImageSpanHelper getImageSpanHelper()
    {
        if (mImageSpanHelper == null)
        {
            mImageSpanHelper = new FImageSpanHelper(this);
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
