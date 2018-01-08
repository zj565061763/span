package com.fanwe.www.androidspan;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fanwe.library.activity.SDBaseActivity;
import com.fanwe.lib.span.FIImageSpanHelper;
import com.fanwe.lib.span.FImageSpan;
import com.fanwe.lib.span.FSpannableStringBuilder;

/**
 * Created by Administrator on 2017/7/18.
 */
public class TextViewActivity extends SDBaseActivity
{
    private TextView tv;
    private Button btn_add_bottom, btn_add_baseline;

    @Override
    protected void init(Bundle savedInstanceState)
    {
        setContentView(R.layout.act_textview);
        tv = (TextView) findViewById(R.id.tv);
        btn_add_bottom = (Button) findViewById(R.id.btn_add_bottom);
        btn_add_baseline = (Button) findViewById(R.id.btn_add_baseline);

        btn_add_bottom.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FSpannableStringBuilder sb = new FSpannableStringBuilder();
                sb.append("f");

                FImageSpan span = new FImageSpan(getApplicationContext(), R.drawable.face);
                span.setVerticalAlignType(FIImageSpanHelper.VerticalAlignType.ALIGN_BOTTOM); //设置对齐字体底部
                span.setWidth(100); //设置图片宽度，内部会按比例缩放
                span.setMarginLeft(10); //设置左边间距
                span.setMarginRight(10); //设置右边间距

                sb.appendSpan(span, "launcher");
                tv.setText(sb);
            }
        });

        btn_add_baseline.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FSpannableStringBuilder sb = new FSpannableStringBuilder();
                sb.append("f");

                FImageSpan span = new FImageSpan(getApplicationContext(), R.drawable.face);
                span.setVerticalAlignType(FIImageSpanHelper.VerticalAlignType.ALIGN_BASELINE); //设置对齐字体基准线
                span.setWidth(100); //设置图片宽度，内部会按比例缩放
                span.setMarginLeft(10); //设置左边间距
                span.setMarginRight(10); //设置右边间距

                sb.appendSpan(span, "launcher");
                tv.setText(sb);
            }
        });


    }
}
