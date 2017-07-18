package com.fanwe.www.androidspan;

import android.content.Context;
import android.util.AttributeSet;

import com.fanwe.library.span.MatcherInfo;
import com.fanwe.library.span.SDImageSpan;
import com.fanwe.library.span.SDPatternUtil;
import com.fanwe.library.span.SDSpannableStringBuilder;
import com.fanwe.library.span.view.SDSpannableTextView;
import com.fanwe.library.utils.SDPackageUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/7/18.
 */

public class CustomTextView extends SDSpannableTextView
{
    public CustomTextView(Context context)
    {
        super(context);
    }

    public CustomTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    protected void onProcessSpannableStringBuilder(SDSpannableStringBuilder builder)
    {
        List<MatcherInfo> list = SDPatternUtil.findMatcherInfo("\\[([^\\[\\]]+)\\]", builder.toString());
        for (final MatcherInfo info : list)
        {
            String key = info.getKey();
            key = key.substring(1, key.length() - 1);
            int resId = getIdentifierDrawable(key);
            if (resId != 0)
            {
                SDImageSpan span = new SDImageSpan(getContext(), resId);
                builder.setSpan(span, info);
            }
        }
    }

    public int getIdentifierDrawable(String name)
    {
        return getResources().getIdentifier(name, "drawable", SDPackageUtil.getPackageName());
    }
}
