package com.fanwe.www.androidspan;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.fanwe.library.activity.SDBaseActivity;
import com.fanwe.library.span.SDImageSpan;
import com.fanwe.library.span.view.SDSpannableEditText;

/**
 * Created by Administrator on 2017/7/18.
 */

public class EditTextActivity extends SDBaseActivity
{
    private SDSpannableEditText et;

    private Button btn_add, btn_remove;

    @Override
    protected void init(Bundle savedInstanceState)
    {
        setContentView(R.layout.act_edittext);
        et = (SDSpannableEditText) findViewById(R.id.et);
        btn_add = (Button) findViewById(R.id.btn_add);
        btn_remove = (Button) findViewById(R.id.btn_remove);

        btn_add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SDImageSpan span = new SDImageSpan(getApplicationContext(), R.drawable.face);
                et.insertSpan(span, "face"); //插入span
            }
        });

        btn_remove.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                et.removeSpan(); //删除span
            }
        });
    }
}
