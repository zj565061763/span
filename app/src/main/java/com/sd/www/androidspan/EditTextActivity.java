package com.sd.www.androidspan;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sd.lib.span.FImageSpan;
import com.sd.lib.span.utils.FEditTextSpanHandler;

/**
 * Created by Administrator on 2017/7/18.
 */
public class EditTextActivity extends AppCompatActivity
{
    public static final String TAG = EditTextActivity.class.getSimpleName();

    private FEditTextSpanHandler mEditTextSpanHandler;
    private Button btn_add, btn_add_text, btn_remove;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_edittext);
        btn_add = findViewById(R.id.btn_add);
        btn_add_text = findViewById(R.id.btn_add_text);
        btn_remove = findViewById(R.id.btn_remove);

        btn_add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // 插入span
                getEditTextSpanHandler().insertSpan("face", new FImageSpan(EditTextActivity.this, R.drawable.face));
            }
        });

        btn_add_text.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // 插入span
                getEditTextSpanHandler().insertSpan("text", new ForegroundColorSpan(Color.RED));
            }
        });

        btn_remove.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // 删除span
                getEditTextSpanHandler().removeSpan();
            }
        });
    }

    public FEditTextSpanHandler getEditTextSpanHandler()
    {
        if (mEditTextSpanHandler == null)
        {
            mEditTextSpanHandler = new FEditTextSpanHandler((EditText) findViewById(R.id.et));
            mEditTextSpanHandler.setCallback(new FEditTextSpanHandler.Callback()
            {
                @Override
                public void onSpanInsert(Object span, int start, int end)
                {
                    Log.i(TAG, "onSpanInsert start:" + start + " end:" + end + " span:" + span);
                }

                @Override
                public void onSpanRemove(Object span, int start, int end)
                {
                    Log.i(TAG, "onSpanRemove start:" + start + " end:" + end + " span:" + span);
                }
            });
        }
        return mEditTextSpanHandler;
    }
}
