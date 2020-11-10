package com.sd.www.androidspan;

import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.sd.lib.adapter.FSimpleRecyclerAdapter;
import com.sd.lib.adapter.viewholder.FRecyclerViewHolder;
import com.sd.lib.span.FViewSpan;
import com.sd.lib.span.utils.FSpanUtil;
import com.sd.www.androidspan.databinding.ActRecyclerViewBinding;
import com.sd.www.androidspan.databinding.ItemRecyclerViewBinding;
import com.sd.www.androidspan.view.UserInfoView;

public class RecyclerViewActivity extends AppCompatActivity implements View.OnClickListener
{
    private ActRecyclerViewBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mBinding = ActRecyclerViewBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mBinding.rvContent.setAdapter(mAdapter);
    }

    private final FSimpleRecyclerAdapter<String> mAdapter = new FSimpleRecyclerAdapter<String>()
    {
        @Override
        public int getLayoutId(ViewGroup parent, int viewType)
        {
            return R.layout.item_recycler_view;
        }

        @Override
        public void onBindData(FRecyclerViewHolder<String> holder, int position, String model)
        {
            final ItemRecyclerViewBinding binding = ItemRecyclerViewBinding.bind(holder.itemView);
            final SpannableStringBuilder builder = new SpannableStringBuilder();

            final UserInfoView view = new UserInfoView(RecyclerViewActivity.this);

            final FViewSpan viewSpan = new FViewSpan(view, binding.tvContent);
            viewSpan.setHeightType(FViewSpan.HeightType.ascent);
            viewSpan.setAlignType(FViewSpan.AlignType.baseline);

            FSpanUtil.appendSpan(builder, "span", viewSpan);

            builder.append("hello world!");
            binding.tvContent.setText(builder);
        }
    };

    @Override
    public void onClick(View v)
    {
        if (v == mBinding.btnAdd)
        {
            mAdapter.getDataHolder().addData("1");
        }
    }
}
