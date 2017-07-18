package com.fanwe.www.androidspan;

import android.os.Bundle;

import com.fanwe.library.activity.SDBaseActivity;

/**
 * Created by Administrator on 2017/7/18.
 */

public class PatternActivity extends SDBaseActivity
{
    private CustomTextView tv;

    @Override
    protected void init(Bundle savedInstanceState)
    {
        setContentView(R.layout.act_pattern);
        tv = (CustomTextView) findViewById(R.id.tv);
        tv.setText("fdkfsofosi[face]fdsfsdf[face]");
    }
}
