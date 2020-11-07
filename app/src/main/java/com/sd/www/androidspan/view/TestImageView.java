package com.sd.www.androidspan.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

public class TestImageView extends AppCompatImageView
{
    public static final String TAG = TestImageView.class.getSimpleName();

    public TestImageView(@NonNull Context context)
    {
        super(context);
    }

    @Override
    public void setImageResource(int resId)
    {
        Log.i(TAG, "setImageDrawable resId:" + resId);
        super.setImageResource(resId);
    }

    @Override
    public void setImageDrawable(@Nullable Drawable drawable)
    {
        Log.i(TAG, "setImageDrawable drawable:" + drawable);
        super.setImageDrawable(drawable);
    }

    @Override
    public void setImageBitmap(Bitmap bm)
    {
        Log.i(TAG, "setImageBitmap Bitmap:" + bm);
        super.setImageBitmap(bm);
    }
}
