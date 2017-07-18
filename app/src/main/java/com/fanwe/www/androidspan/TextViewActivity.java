package com.fanwe.www.androidspan;

import android.os.Bundle;
import android.widget.TextView;

import com.fanwe.library.activity.SDBaseActivity;
import com.fanwe.library.span.IImageSpanHelper;
import com.fanwe.library.span.SDImageSpan;
import com.fanwe.library.span.SDSpannableStringBuilder;

/**
 * Created by Administrator on 2017/7/18.
 */
public class TextViewActivity extends SDBaseActivity
{
    private TextView tv;

    @Override
    protected void init(Bundle savedInstanceState)
    {
        setContentView(R.layout.act_textview);
        tv = (TextView) findViewById(R.id.tv);

        SDSpannableStringBuilder sb = new SDSpannableStringBuilder();
        sb.append("0");

        SDImageSpan span = new SDImageSpan(this, R.drawable.face);
        span.setVerticalAlignType(IImageSpanHelper.VerticalAlignType.ALIGN_BASELINE); //设置对齐TextView基准线(默认对齐方式)
        span.setVerticalAlignType(IImageSpanHelper.VerticalAlignType.ALIGN_BOTTOM); //设置对齐TextView底部
        span.setWidth(100); //设置图片宽度，内部会按比例缩放
        span.setMarginLeft(10); //设置左边间距
        span.setMarginRight(10); //设置右边间距
        span.setMarginBottom(10); //设置底部间距

        sb.appendSpan(span, "launcher");
        tv.setText(sb);
    }
}
