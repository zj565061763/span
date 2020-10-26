package com.sd.www.androidspan;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.sd.lib.span.utils.FRegexHandler;

/**
 * Created by Administrator on 2017/7/18.
 */

public class RegexActivity extends AppCompatActivity
{
    private static final String CONTENT = "fdkfsofosi[face]fdsfsdf[face]54654655[face]654654 @78956dfsfs12345@66666ofisidf";

    private FRegexHandler mRegexHandler;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_regex);
        tv = findViewById(R.id.tv);
        tv.setText(CONTENT);

        findViewById(R.id.fl_text).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(RegexActivity.this, "click FrameLayout", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.btn_match).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // 匹配内容
                final CharSequence result = getRegexHandler().process(CONTENT);
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

    private FRegexHandler getRegexHandler()
    {
        if (mRegexHandler == null)
        {
            mRegexHandler = new FRegexHandler();
            mRegexHandler.addMatchCallback(mBracketResDrawableCallback);
            mRegexHandler.addMatchCallback(mReplaceAtNumberCallback);
        }
        return mRegexHandler;
    }

    /**
     * 匹配中括号表情
     */
    private final FRegexHandler.BracketResDrawableCallback mBracketResDrawableCallback = new FRegexHandler.BracketResDrawableCallback(RegexActivity.this)
    {
        @Override
        protected void onMatchResDrawable(String name, int start, int end, int resId, SpannableStringBuilder builder)
        {
            final ImageSpan imageSpan = new ImageSpan(RegexActivity.this, resId);
            builder.setSpan(imageSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    };

    /**
     * 替换@数字的内容
     */
    private final FRegexHandler.ReplaceAtNumberCallback mReplaceAtNumberCallback = new FRegexHandler.ReplaceAtNumberCallback()
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
                    Toast.makeText(RegexActivity.this, "click span target:" + target, Toast.LENGTH_SHORT).show();
                }
            };

            builder.setSpan(colorSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    };
}
