package com.sd.www.androidspan;

import android.graphics.Color;
import android.os.Bundle;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

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

    private EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_edittext);
        btn_add = findViewById(R.id.btn_add);
        btn_add_text = findViewById(R.id.btn_add_text);
        btn_remove = findViewById(R.id.btn_remove);
        et = findViewById(R.id.et);

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
                // 删除
                getEditTextSpanHandler().pressDeleteKey();
            }
        });

        et.setOnKeyListener(new View.OnKeyListener()
        {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    if (keyCode == KeyEvent.KEYCODE_DEL)
                    {
                        if (getEditTextSpanHandler().selectCursorSpan())
                            return true;
                    }
                }
                return false;
            }
        });
    }

    public FEditTextSpanHandler getEditTextSpanHandler()
    {
        if (mEditTextSpanHandler == null)
        {
            mEditTextSpanHandler = new FEditTextSpanHandler(et)
            {
                @Override
                protected void onSpanInsert(SpanInfo spanInfo)
                {
                    super.onSpanInsert(spanInfo);
                    Log.i(TAG, "onSpanInsert start:" + spanInfo.getStart() + " end:" + spanInfo.getEnd() + " span:" + spanInfo.getSpan());
                }

                @Override
                protected void onSpanRemove(SpanInfo spanInfo)
                {
                    super.onSpanRemove(spanInfo);
                    Log.i(TAG, "onSpanRemove start:" + spanInfo.getStart() + " end:" + spanInfo.getEnd() + " span:" + spanInfo.getSpan());
                }
            };
        }
        return mEditTextSpanHandler;
    }
}
