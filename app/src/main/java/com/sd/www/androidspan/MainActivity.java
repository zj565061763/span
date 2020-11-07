package com.sd.www.androidspan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.emoji.bundled.BundledEmojiCompatConfig;
import androidx.emoji.text.EmojiCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EmojiCompat.init(new BundledEmojiCompatConfig(this));
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
            case R.id.btn_net_span:
                startActivity(new Intent(MainActivity.this, NetSpanActivity.class));
                break;
            case R.id.btn_view_span:
                startActivity(new Intent(MainActivity.this, ViewSpanActivity.class));
                break;
            default:
                break;
        }
    }
}
