package com.sd.www.androidspan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Administrator on 2017/7/18.
 */

public class PatternActivity extends AppCompatActivity
{
    private CustomTextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_pattern);
        tv = (CustomTextView) findViewById(R.id.tv);
        tv.setText("fdkfsofosi[face]fdsfsdf[face]");
    }
}
