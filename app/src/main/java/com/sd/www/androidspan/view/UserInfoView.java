package com.sd.www.androidspan.view;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.sd.www.androidspan.databinding.ViewUserInfoBinding;

public class UserInfoView extends FrameLayout
{
    private final ViewUserInfoBinding mBinding;

    public UserInfoView(Context context)
    {
        super(context);
        mBinding = ViewUserInfoBinding.inflate(LayoutInflater.from(context), this, true);
        setBackgroundColor(Color.RED);
    }

    public ViewUserInfoBinding getBinding()
    {
        return mBinding;
    }
}
