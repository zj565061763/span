package com.sd.www.androidspan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.sd.lib.span.FSpannableStringBuilder;

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

        FSpannableStringBuilder sb = new FSpannableStringBuilder();

        NetImageSpan span = new NetImageSpan(tv);
        span.setUrl("https://www.baidu.com/img/bd_logo1.png");
        span.getImageSpanHelper().setWidth(200);
        sb.appendSpan(span, "span");

        tv.setText(sb);
    }
}
