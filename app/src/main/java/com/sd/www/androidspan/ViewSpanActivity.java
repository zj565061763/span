package com.sd.www.androidspan;

import android.os.Bundle;
import android.text.SpannableStringBuilder;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.sd.lib.span.FViewSpan;
import com.sd.lib.span.utils.FSpanUtil;
import com.sd.www.androidspan.databinding.ActViewSpanBinding;
import com.sd.www.androidspan.databinding.IncludeViewSpanBinding;

/**
 * 渲染View的span
 */
public class ViewSpanActivity extends AppCompatActivity
{
    private ActViewSpanBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mBinding = ActViewSpanBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        final SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append("before");

        final IncludeViewSpanBinding binding = IncludeViewSpanBinding.inflate(getLayoutInflater());
        binding.tvContent.setText("text in view");

        final FViewSpan viewSpan = new FViewSpan(binding.getRoot(), mBinding.tvContent)
        {
            @Override
            protected void onPrepared()
            {
                super.onPrepared();
                Glide.with(ViewSpanActivity.this).load("https://www.baidu.com/img/bd_logo1.png").into(binding.ivNetImage);
            }
        };
        FSpanUtil.appendSpan(builder, "span", viewSpan);

        builder.append("after");
        mBinding.tvContent.setText(builder);

    }
}
