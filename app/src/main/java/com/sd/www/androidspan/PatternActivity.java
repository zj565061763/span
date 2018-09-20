package com.sd.www.androidspan;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sd.lib.span.utils.FTextViewPattern;

import java.util.regex.Matcher;

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

        tv.setMovementMethod(LinkMovementMethod.getInstance());
        tv.setHighlightColor(Color.TRANSPARENT);
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
                public void onMatch(Matcher matcher, SpannableStringBuilder builder)
                {
                    final String key = matcher.group();
                    final int start = matcher.start();
                    final int end = matcher.end();

                    // 截取中括号中的名称
                    final String name = key.substring(1, key.length() - 1);
                    // 根据名称获得资源id
                    final int resId = getResources().getIdentifier(name, "drawable", getPackageName());
                    if (resId != 0)
                    {
                        // 添加表情span
                        builder.setSpan(new ImageSpan(PatternActivity.this, resId), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }

                    builder.setSpan(new ClickableSpan()
                    {
                        @Override
                        public void onClick(View widget)
                        {
                            Toast.makeText(PatternActivity.this, "span clicked", Toast.LENGTH_SHORT).show();
                        }
                    }, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                }
            });
        }
        return mTextViewPattern;
    }
}
