package com.fanwe.www.androidspan;

import android.content.Context;
import android.util.AttributeSet;

import com.fanwe.lib.span.MatcherInfo;
import com.fanwe.lib.span.FImageSpan;
import com.fanwe.lib.span.FPatternUtil;
import com.fanwe.lib.span.FSpannableStringBuilder;
import com.fanwe.lib.span.view.FSpannableTextView;
import com.fanwe.library.utils.SDPackageUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/7/18.
 */

public class CustomTextView extends FSpannableTextView
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
    protected void onProcessSpannableStringBuilder(FSpannableStringBuilder builder)
    {
        //正则表达式匹配[***]中括号这种规则的字符串
        List<MatcherInfo> list = FPatternUtil.findMatcherInfo("\\[([^\\[\\]]+)\\]", builder.toString());
        for (final MatcherInfo info : list)
        {
            String key = info.getKey(); //获得匹配的字符串
            key = key.substring(1, key.length() - 1); //移除中括号，得到文件名
            int resId = getIdentifierDrawable(key); //根据文件名获得图片资源id
            if (resId != 0)
            {
                FImageSpan span = new FImageSpan(getContext(), resId);
                builder.setSpan(span, info); //用span，替换匹配到的字符串
            }
        }
    }

    public int getIdentifierDrawable(String name)
    {
        return getResources().getIdentifier(name, "drawable", SDPackageUtil.getPackageName());
    }
}
