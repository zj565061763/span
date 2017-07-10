package com.fanwe.www.androidspan;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.widget.TextView;

import com.fanwe.library.span.SDPatternUtil;
import com.fanwe.library.span.SDSpannableStringBuilder;

import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private static final String TAG_SDPATTERNUTIL = "tag_sdpatternutil";

    private TextView tv;
    private SDSpannableStringBuilder mBuilder = new SDSpannableStringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);

        mBuilder.append("hello");


        BackgroundColorSpan spanBg = new BackgroundColorSpan(Color.RED);


        mBuilder.setSpan(spanBg, 0, 4);
        tv.setText(mBuilder);

        testSDPatternUtil();
    }


    private void testSDPatternUtil()
    {
        String content = "hellohello";

        List<String> listResult = SDPatternUtil.find("ll", content);

        Log.i(TAG_SDPATTERNUTIL, "");
    }
}
