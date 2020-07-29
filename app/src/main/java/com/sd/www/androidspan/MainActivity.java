package com.sd.www.androidspan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_textview:
                startActivity(new Intent(MainActivity.this, TextViewActivity.class));
                break;
            case R.id.btn_edittext:
                startActivity(new Intent(MainActivity.this, EditTextActivity.class));
                break;
            case R.id.btn_at_edittext:
                startActivity(new Intent(MainActivity.this, AtEditTextActivity.class));
                break;
            case R.id.btn_regex:
                startActivity(new Intent(MainActivity.this, RegexActivity.class));
                break;
            case R.id.btn_netspan:
                startActivity(new Intent(MainActivity.this, NetSpanActivity.class));
                break;
            default:
                break;
        }
    }
}
