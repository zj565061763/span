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
                    return "\\[([^\\[\\]]+)\\]";
                }

                @Override
                public void onMatch(String regex, String key, int start, int end, SpannableStringBuilder builder)
                {
                    final String drawableName = key.substring(1, key.length() - 1);
                    final int drawableId = getResources().getIdentifier(drawableName, "drawable", getPackageName());

                    final ImageSpan span = new ImageSpan(PatternActivity.this, drawableId);
                    builder.setSpan(span, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            });
        }
        return mTextViewPattern;
    }
}
