package com.fanwe.www.androidspan;

import android.graphics.Bitmap;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.fanwe.library.span.SDDynamicDrawableSpan;

/**
 * Created by Administrator on 2017/7/18.
 */

public class NetImageSpan extends SDDynamicDrawableSpan
{
    private String mUrl;
    private Bitmap mBitmap;

    /**
     * @param view span要依附的view
     */
    public NetImageSpan(View view)
    {
        super(view);
    }

    public void setUrl(String url)
    {
        mUrl = url;
    }

    @Override
    protected int getDefaultDrawableResId()
    {
        return R.drawable.ic_default;
    }

    @Override
    protected Bitmap onGetBitmap()
    {
        if (mBitmap == null || mBitmap.isRecycled())
        {
            Glide.with(getContext()).load(mUrl).asBitmap().into(new SimpleTarget<Bitmap>()
            {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation)
                {
                    mBitmap = resource;
                    getView().postInvalidate();
                }
            });
        }
        return mBitmap;
    }
}
