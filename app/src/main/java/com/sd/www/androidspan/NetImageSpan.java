package com.sd.www.androidspan;

import android.graphics.Bitmap;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.sd.lib.span.FDynamicDrawableSpan;

/**
 * Created by Administrator on 2017/7/18.
 */

public class NetImageSpan extends FDynamicDrawableSpan
{
    private String mUrl; //图片url地址
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
        //返回图片未加载成功之前的占位图片
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
                    mBitmap = resource; //demo演示简单在span内部保存，具体项目中应该把Bitmap对象存到app的缓存管理中
                    getView().postInvalidate(); //加载成功后，刷新View
                }
            });
        }
        return mBitmap;
    }
}
