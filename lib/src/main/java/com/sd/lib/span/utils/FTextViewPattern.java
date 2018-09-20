package com.sd.lib.span.utils;

import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FTextViewPattern
{
    private TextView mTextView;
    private SpannableStringBuilder mBuilder;

    private final List<MatchCallback> mListCallback = new CopyOnWriteArrayList<>();

    /**
     * 添加正则表达式匹配回调
     *
     * @param callback 回调
     */
    public void addMatchCallback(MatchCallback callback)
    {
        if (callback == null)
            throw new NullPointerException();

        if (!mListCallback.contains(callback))
            mListCallback.add(callback);
    }

    /**
     * 移除正则表达式匹配回调
     *
     * @param regex
     */
    public void removeMatchCallback(String regex)
    {
        mListCallback.remove(regex);
    }

    /**
     * 设置要处理的对象
     *
     * @param textView
     */
    public void setTextView(TextView textView)
    {
        final TextView old = mTextView;
        if (old != textView)
        {
            if (old != null)
            {
                old.removeOnAttachStateChangeListener(mOnAttachStateChangeListener);
                addOnPreDrawListener(false, old);
            }

            mTextView = textView;
            if (mBuilder != null)
                mBuilder.clear();

            if (textView != null)
            {
                textView.addOnAttachStateChangeListener(mOnAttachStateChangeListener);
                addOnPreDrawListener(true, textView);
            }
        }
    }

    private void addOnPreDrawListener(boolean add, View view)
    {
        final ViewTreeObserver observer = view.getViewTreeObserver();
        if (observer.isAlive())
        {
            observer.removeOnPreDrawListener(mOnPreDrawListener);
            if (add)
                observer.addOnPreDrawListener(mOnPreDrawListener);
        }
    }

    private final View.OnAttachStateChangeListener mOnAttachStateChangeListener = new View.OnAttachStateChangeListener()
    {
        @Override
        public void onViewAttachedToWindow(View v)
        {
            addOnPreDrawListener(true, v);
        }

        @Override
        public void onViewDetachedFromWindow(View v)
        {
            addOnPreDrawListener(false, v);
        }
    };

    private final ViewTreeObserver.OnPreDrawListener mOnPreDrawListener = new ViewTreeObserver.OnPreDrawListener()
    {
        @Override
        public boolean onPreDraw()
        {
            processPattern();
            return true;
        }
    };

    private SpannableStringBuilder getBuilder()
    {
        if (mBuilder == null)
            mBuilder = new SpannableStringBuilder();
        return mBuilder;
    }

    private void processPattern()
    {
        if (mListCallback.isEmpty())
            return;

        final String textViewContent = mTextView.getText().toString();
        if (getBuilder().toString().equals(textViewContent))
            return;

        getBuilder().clear();
        getBuilder().append(textViewContent);

        for (MatchCallback item : mListCallback)
        {
            final String regex = item.getRegex();
            if (TextUtils.isEmpty(regex))
                continue;

            final Matcher matcher = Pattern.compile(regex).matcher(textViewContent);
            if (matcher != null)
            {
                while (matcher.find())
                {
                    item.onMatch(matcher, getBuilder());
                }
            }
        }

        mTextView.setText(getBuilder());
    }

    public interface MatchCallback
    {
        /**
         * 返回正则表达式
         *
         * @return
         */
        String getRegex();

        /**
         * 正则表达式匹配回调
         *
         * @param matcher
         * @param builder
         */
        void onMatch(Matcher matcher, SpannableStringBuilder builder);
    }
}
