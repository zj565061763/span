package com.sd.www.androidspan;

import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.sd.lib.span.FViewSpan;
import com.sd.lib.span.utils.FSpanUtil;
import com.sd.lib.utils.FViewUtil;
import com.sd.lib.utils.context.FResUtil;
import com.sd.www.androidspan.databinding.ActViewSpanBinding;
import com.sd.www.androidspan.view.TestImageView;
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
        mBuilder.clear();
        addUserInfoView();
        addImageView();
        mBuilder.append("hello world!");
        updateTextView();
    }

    /**
     * 更新TextView
     */
    private void updateTextView()
    {
        mBinding.tvContent.setText(mBuilder);
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
        final TestImageView imageView = new TestImageView(ViewSpanActivity.this);
        FViewUtil.setSize(imageView, FResUtil.dp2px(20), ViewGroup.LayoutParams.MATCH_PARENT);

        final FViewSpan imageViewSpan = new FViewSpan(imageView, mBinding.tvContent)
        {
            @Override
            protected void onPrepared()
            {
                super.onPrepared();
                Glide.with(ViewSpanActivity.this)
                        .load("https://www.baidu.com/img/bd_logo1.png")
                        .placeholder(R.drawable.ic_loading)
                        .error(R.drawable.ic_load_failed)
                        .into(imageView);
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
        } else if (v == mBinding.btnImage)
        {
            addImageView();
            updateTextView();
        }
    }
}
