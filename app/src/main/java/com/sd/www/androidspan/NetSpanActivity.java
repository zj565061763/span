package com.sd.www.androidspan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.widget.TextView;

import com.sd.lib.span.utils.FSpanUtil;

/**
 * Created by Administrator on 2017/7/18.
 */

public class NetSpanActivity extends AppCompatActivity
{
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_netspan);
        tv = findViewById(R.id.tv);

        NetImageSpan span = new NetImageSpan(tv);
        span.setUrl("https://www.baidu.com/img/bd_logo1.png");
        span.getImageSpanHelper().setWidth(200);

        SpannableStringBuilder builder = new SpannableStringBuilder();
        FSpanUtil.appendSpan("span", span, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE, builder);
        tv.setText(builder);
    }
}
