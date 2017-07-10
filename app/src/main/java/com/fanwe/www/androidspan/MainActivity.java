package com.fanwe.www.androidspan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.TextView;

import com.fanwe.library.span.SDSpannableStringBuilder;
import com.fanwe.library.span.view.SDSpannableEditText;

public class MainActivity extends AppCompatActivity
{
    private TextView tv;
    private SDSpannableEditText et_span;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
        et_span = (SDSpannableEditText) findViewById(R.id.et_span);

        testTextView();
        testEditText();
    }

    private void testEditText()
    {
        tv.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                et_span.removeSpan();
            }
        });

        ImageSpan imageSpan = new ImageSpan(this, R.drawable.test);
        et_span.insertSpan(imageSpan, "key");
    }

    private void testTextView()
    {
        SDSpannableStringBuilder sb = new SDSpannableStringBuilder();
        sb.append("hello");

        CustomImageSpan imageSpan = new CustomImageSpan(this, R.drawable.test, DynamicDrawableSpan.ALIGN_BOTTOM);

        sb.appendSpan(imageSpan, "launcher");
        tv.setText(sb);
    }

}
