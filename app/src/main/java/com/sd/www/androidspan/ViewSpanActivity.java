package com.sd.www.androidspan;

import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.sd.lib.span.FViewSpan;
import com.sd.lib.span.utils.FSpanUtil;
import com.sd.www.androidspan.databinding.ActViewSpanBinding;
import com.sd.www.androidspan.view.UserInfoView;

/**
 * 渲染View的span
 */
public class ViewSpanActivity extends AppCompatActivity implements View.OnClickListener
{
    private ActViewSpanBinding mBinding;
    private final SpannableStringBuilder mBuilder = new SpannableStringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mBinding = ActViewSpanBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
    }

    private void showSpan()
    {
        final SpannableStringBuilder builder = mBuilder;
        builder.clear();

        builder.append("before");
        addUserInfoView();
        builder.append("after");

        mBinding.tvContent.setText(builder);
    }

    /**
     * 用户信息
     */
    private void addUserInfoView()
    {
        final UserInfoView view = new UserInfoView(ViewSpanActivity.this);
        FSpanUtil.appendSpan(mBuilder, "span", new FViewSpan(view, mBinding.tvContent));
    }

    /**
     * 添加网络图片
     */
    private void addImageView()
    {
        final ImageView imageView = new ImageView(ViewSpanActivity.this);
        imageView.setImageResource(R.drawable.face);
        final FViewSpan imageViewSpan = new FViewSpan(imageView, mBinding.tvContent)
        {
            @Override
            protected void onPrepared()
            {
                super.onPrepared();
                Glide.with(ViewSpanActivity.this).load("https://www.baidu.com/img/bd_logo1.png").into(imageView);
            }
        };
        FSpanUtil.appendSpan(mBuilder, "span", imageViewSpan);
    }

    @Override
    public void onClick(View v)
    {
        if (v == mBinding.btnShow)
        {
            showSpan();
        } else if (v == mBinding.btnAdd)
        {
            mBuilder.append("after");
            mBinding.tvContent.setText(mBuilder);
        }
    }
}
