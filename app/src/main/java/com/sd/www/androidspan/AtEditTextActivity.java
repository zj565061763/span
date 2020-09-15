package com.sd.www.androidspan;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
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

    private TextView tv_content;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_at_edittext);
        et = findViewById(R.id.et);
        tv_content = findViewById(R.id.tv_content);

        et.setOnKeyListener(new View.OnKeyListener()
        {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (mSpanHandler.dispatchKeyEvent(keyCode, event))
                    return true;
                return false;
            }
        });

        mSpanHandler = new FAtEditTextSpanHandler(et);
        mSpanHandler.setCallback(new FAtEditTextSpanHandler.Callback()
        {
            @Override
            public void onInputAt()
            {
                Log.i(TAG, "onInputAt");
            }

            @Override
            public void onUserAdd(String userId)
            {
                Log.i(TAG, "onUserAdd:" + userId);
                showAll();
            }

            @Override
            public void onUserRemove(String userId)
            {
                Log.i(TAG, "onUserRemove:" + userId);
                showAll();
            }
        });
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_add:

                final String userId = String.valueOf(new Random().nextInt(10000));
                final String userName = userId;

                if (mSpanHandler.addUser(userId, userName))
                    et.getText().append(" ");

                break;
            default:
                break;
        }
    }

    private void showAll()
    {
        final StringBuilder builder = new StringBuilder();
        final List<FAtEditTextSpanHandler.UserInfo> list = mSpanHandler.getAllUser();
        for (FAtEditTextSpanHandler.UserInfo item : list)
        {
            builder.append(item.getUserId()).append("\r\n");
        }

        tv_content.setText(builder.toString());
    }
}
