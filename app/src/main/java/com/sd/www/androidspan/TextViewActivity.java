package com.sd.www.androidspan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sd.lib.span.FImageSpan;
import com.sd.lib.span.ImageSpanHelper;
import com.sd.lib.span.utils.FSpanUtil;

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
                SpannableStringBuilder builder = new SpannableStringBuilder();
                builder.append("f");

                FImageSpan span = new FImageSpan(getApplicationContext(), R.drawable.face);
                // 设置对齐字体底部
                span.setVerticalAlignType(ImageSpanHelper.VerticalAlignType.ALIGN_BOTTOM);
                // 设置图片宽度，内部会按比例缩放
                span.setWidth(100);
                // 设置左边间距
                span.setMarginLeft(10);
                // 设置右边间距
                span.setMarginRight(10);
                // 将span添加到SpannableStringBuilder
                FSpanUtil.appendSpan(builder, "launcher", span);
                tv.setText(builder);
            }
        });

        btn_add_baseline.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SpannableStringBuilder builder = new SpannableStringBuilder();
                builder.append("f");

                FImageSpan span = new FImageSpan(getApplicationContext(), R.drawable.face);
                span.setVerticalAlignType(ImageSpanHelper.VerticalAlignType.ALIGN_BASELINE); //设置对齐字体基准线
                span.setWidth(100); //设置图片宽度，内部会按比例缩放
                span.setMarginLeft(10); //设置左边间距
                span.setMarginRight(10); //设置右边间距

                FSpanUtil.appendSpan(builder, "launcher", span);
                tv.setText(builder);
            }
        });
    }
}
