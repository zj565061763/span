package com.sd.www.androidspan;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
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
        span.setWidth(200);

        SpannableStringBuilder builder = new SpannableStringBuilder();
        FSpanUtil.appendSpan(builder, "span", span);
        tv.setText(builder);
    }
}
