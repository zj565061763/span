package com.sd.www.androidspan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.widget.TextView;

import com.sd.lib.span.FTextViewPattern;

/**
 * Created by Administrator on 2017/7/18.
 */

public class PatternActivity extends AppCompatActivity
{
    private FTextViewPattern mTextViewPattern;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_pattern);
        tv = findViewById(R.id.tv);
        tv.setText("fdkfsofosi[face]fdsfsdf[face]54654655[face]654654");

        getTextViewPattern().setTextView(tv);
    }

    public FTextViewPattern getTextViewPattern()
    {
        if (mTextViewPattern == null)
        {
            mTextViewPattern = new FTextViewPattern();
            mTextViewPattern.addMatchCallback(new FTextViewPattern.MatchCallback()
            {
                @Override
                public String getRegex()
                {
                    /**
                     * 匹配中括号的内容
                     */
                    return "\\[([^\\[\\]]+)\\]";
                }

                @Override
                public void onMatch(String key, int start, int end, SpannableStringBuilder builder)
                {
                    // 截取中括号中的名称
                    final String name = key.substring(1, key.length() - 1);
                    // 根据名称获得资源id
                    final int resId = getResources().getIdentifier(name, "drawable", getPackageName());

                    final ImageSpan span = new ImageSpan(PatternActivity.this, resId);
                    builder.setSpan(span, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            });
        }
        return mTextViewPattern;
    }
}
