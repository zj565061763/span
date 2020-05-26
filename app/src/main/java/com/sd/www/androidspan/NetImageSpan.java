package com.sd.www.androidspan;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
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
            Glide.with(getContext()).asBitmap().load(mUrl).into(new CustomTarget<Bitmap>()
            {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition)
                {
                    //demo演示简单在span内部保存，具体项目中应该把Bitmap对象存到app的缓存管理中
                    mBitmap = resource;
                    final View view = getView();
                    if (view != null)
                        view.postInvalidate();
                }

                @Override
                public void onLoadCleared(@Nullable Drawable placeholder)
                {

                }
            });
        }
        return mBitmap;
    }
}
