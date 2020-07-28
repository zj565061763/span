package com.sd.www.androidspan;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.sd.lib.span.utils.FTextPattern;

/**
 * Created by Administrator on 2017/7/18.
 */

public class PatternActivity extends AppCompatActivity
{
    private static final String CONTENT = "fdkfsofosi[face]fdsfsdf[face]54654655[face]654654 @78956dfsfs12345@66666ofisidf";

    private FTextPattern mTextPattern;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_pattern);
        tv = findViewById(R.id.tv);

        tv.setMovementMethod(LinkMovementMethod.getInstance());
        tv.setHighlightColor(Color.TRANSPARENT);
        tv.setText(CONTENT);

        findViewById(R.id.btn_match).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // 匹配内容
                final CharSequence result = getTextPattern().process(CONTENT);
                tv.setText(result);
            }
        });
        findViewById(R.id.btn_restore).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                tv.setText(CONTENT);
            }
        });
    }

    private FTextPattern getTextPattern()
    {
        if (mTextPattern == null)
        {
            mTextPattern = new FTextPattern();
            mTextPattern.addMatchCallback(mBracketResDrawableCallback);
            mTextPattern.addMatchCallback(mReplaceAtNumberCallback);
        }
        return mTextPattern;
    }

    /**
     * 匹配中括号表情
     */
    private final FTextPattern.BracketResDrawableCallback mBracketResDrawableCallback = new FTextPattern.BracketResDrawableCallback(PatternActivity.this)
    {
        @Override
        protected void onMatchResDrawable(String name, int start, int end, int resId, SpannableStringBuilder builder)
        {
            final ImageSpan imageSpan = new ImageSpan(PatternActivity.this, resId);
            builder.setSpan(imageSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    };

    /**
     * 替换@数字的内容
     */
    private final FTextPattern.ReplaceAtNumberCallback mReplaceAtNumberCallback = new FTextPattern.ReplaceAtNumberCallback()
    {
        @Override
        protected String getReplaceContent(String target)
        {
            return "@昵称";
        }

        @Override
        protected void processReplaceContent(final String target, int start, int end, SpannableStringBuilder builder)
        {
            final ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.RED);
            final ClickableSpan clickableSpan = new ClickableSpan()
            {
                @Override
                public void onClick(@NonNull View widget)
                {
                    Toast.makeText(PatternActivity.this, "clicked target:" + target, Toast.LENGTH_SHORT).show();
                }
            };

            builder.setSpan(colorSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    };
}
