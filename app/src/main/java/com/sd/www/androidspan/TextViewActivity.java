package com.sd.www.androidspan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sd.lib.span.FImageSpan;
import com.sd.lib.span.FImageSpanHelper;
import com.sd.lib.span.FSpannableStringBuilder;

/**
 * Created by Administrator on 2017/7/18.
 */
public class TextViewActivity extends AppCompatActivity
{
    private TextView tv;
    private Button btn_add_bottom, btn_add_baseline;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_textview);
        tv = findViewById(R.id.tv);
        btn_add_bottom = findViewById(R.id.btn_add_bottom);
        btn_add_baseline = findViewById(R.id.btn_add_baseline);

        btn_add_bottom.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FSpannableStringBuilder sb = new FSpannableStringBuilder();
                sb.append("f");

                FImageSpan span = new FImageSpan(getApplicationContext(), R.drawable.face);
                span.getImageSpanHelper().setVerticalAlignType(FImageSpanHelper.VerticalAlignType.ALIGN_BOTTOM); //设置对齐字体底部
                span.getImageSpanHelper().setWidth(100); //设置图片宽度，内部会按比例缩放
                span.getImageSpanHelper().setMarginLeft(10); //设置左边间距
                span.getImageSpanHelper().setMarginRight(10); //设置右边间距

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
                span.getImageSpanHelper().setVerticalAlignType(FImageSpanHelper.VerticalAlignType.ALIGN_BASELINE); //设置对齐字体基准线
                span.getImageSpanHelper().setWidth(100); //设置图片宽度，内部会按比例缩放
                span.getImageSpanHelper().setMarginLeft(10); //设置左边间距
                span.getImageSpanHelper().setMarginRight(10); //设置右边间距

                sb.appendSpan(span, "launcher");
                tv.setText(sb);
            }
        });


    }
}
