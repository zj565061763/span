package com.sd.www.androidspan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.sd.lib.span.ext.FAtEditTextSpanHandler;

import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2017/7/18.
 */
public class AtEditTextActivity extends AppCompatActivity implements View.OnClickListener
{
    public static final String TAG = AtEditTextActivity.class.getSimpleName();

    private FAtEditTextSpanHandler mSpanHandler;
    private EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_at_edittext);
        et = findViewById(R.id.et);

        et.setOnKeyListener(new View.OnKeyListener()
        {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (getSpanHandler().dispatchKeyEvent(keyCode, event))
                    return true;
                return false;
            }
        });
    }

    private FAtEditTextSpanHandler getSpanHandler()
    {
        if (mSpanHandler == null)
        {
            mSpanHandler = new FAtEditTextSpanHandler(et);
            mSpanHandler.setCallback(new FAtEditTextSpanHandler.Callback()
            {
                @Override
                public void onInputAt()
                {
                    Log.i(TAG, "onInputAt");
                }
            });
        }
        return mSpanHandler;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_add:
                final String userId = String.valueOf(new Random().nextInt(10000));
                final String userName = userId;

                if (getSpanHandler().addUser(userId, userName))
                    et.getText().append(" ");
                break;
            case R.id.btn_show_all:
                final StringBuilder builder = new StringBuilder();
                final List<FAtEditTextSpanHandler.UserInfoWrapper> list = getSpanHandler().getAllUser();
                for (FAtEditTextSpanHandler.UserInfoWrapper item : list)
                {
                    builder.append(item.getUserId()).append("\r\n");
                }

                final TextView tv = (TextView) v;
                tv.setText(builder.toString());
                break;
            default:
                break;
        }
    }
}
