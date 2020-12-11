package com.sd.lib.span;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.style.ReplacementSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.TextView;

public class FViewSpan extends ReplacementSpan
{
    private static final String TAG = FViewSpan.class.getSimpleName();

    private final TextView mTextView;
    private final InternalLayout mLayout;

    private AlignType mAlignType = AlignType.bottom;
    private HeightType mHeightType = HeightType.match;

    private boolean mIsPrepared = false;
    private boolean mIsDirty = false;

    public FViewSpan(View view, TextView textView)
    {
        if (view == null || textView == null)
            throw new NullPointerException("null");

        mTextView = textView;

        removeViewFromParent(view);
        mLayout = new InternalLayout(view.getContext());
        mLayout.addView(view);
    }

    /**
     * 设置对齐类型
     *
     * @param alignType
     */
    public void setAlignType(AlignType alignType)
    {
        if (alignType == null)
            return;

        if (mAlignType != alignType)
        {
            mAlignType = alignType;
            update();
        }
    }

    /**
     * 设置高度类型
     *
     * @param heightType
     */
    public void setHeightType(HeightType heightType)
    {
        if (heightType == null)
            return;

        if (mHeightType != heightType)
        {
            mHeightType = heightType;
            update();
        }
    }

    /**
     * 更新span
     */
    public void update()
    {
        if (mIsPrepared)
        {
            Log.i(TAG, "update " + FViewSpan.this);
            final CharSequence text = mTextView.getText();
            mTextView.setText(text);
        }
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint)
    {
        Log.i(TAG, "draw " + FViewSpan.this);

        canvas.save();

        int transY = top;
        if (mAlignType == AlignType.baseline)
        {
            final int descent = Math.abs(paint.getFontMetricsInt().descent);
            if (mHeightType == HeightType.match)
            {
                transY -= descent;
            } else if (mHeightType == HeightType.ascent)
            {
                final int height = Math.abs(paint.getFontMetricsInt().bottom - paint.getFontMetricsInt().top);
                final int ascent = Math.abs(paint.getFontMetricsInt().ascent);
                final int delta = height - descent - ascent;
                transY += delta;
            }
        } else if (mAlignType == AlignType.bottom)
        {
            if (mHeightType == HeightType.match)
            {
            } else if (mHeightType == HeightType.ascent)
            {
                final int height = Math.abs(paint.getFontMetricsInt().bottom - paint.getFontMetricsInt().top);
                final int ascent = Math.abs(paint.getFontMetricsInt().ascent);
                final int delta = height - ascent;
                transY += delta;
            }
        }

        canvas.translate(x, transY);
        mLayout.draw(canvas);
        canvas.restore();
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm)
    {
        final int height = getLineHeight(fm);
        if (mIsDirty)
        {
            measureLayout(height);
            Log.i(TAG, "getSize width:" + mLayout.getMeasuredWidth() + " height:" + height + " " + FViewSpan.this);
        }
        return mLayout.getMeasuredWidth();
    }

    private int getLineHeight(Paint.FontMetricsInt fm)
    {
        int height = 0;
        final Paint.FontMetricsInt fmTextView = mTextView.getPaint().getFontMetricsInt();
        if (fmTextView != null)
            height = getFontMetricsIntHeight(fmTextView);

        if (height <= 0)
        {
            if (fm != null)
                height = getFontMetricsIntHeight(fm);
        }

        final int measuredHeight = mLayout.getMeasuredHeight();
        if (height != measuredHeight)
            setDirty(true);

        return height;
    }

    private int getFontMetricsIntHeight(Paint.FontMetricsInt fm)
    {
        int height = 0;
        if (mHeightType == HeightType.match)
        {
            height = Math.abs(fm.bottom - fm.top);
        } else if (mHeightType == HeightType.ascent)
        {
            height = Math.abs(fm.ascent);
        }
        return height;
    }

    private boolean setDirty(boolean dirty)
    {
        if (mIsDirty != dirty)
        {
            mIsDirty = dirty;
            return true;
        }
        return false;
    }

    private void measureLayout(int height)
    {
        final int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        final int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);
        mLayout.measure(widthMeasureSpec, heightMeasureSpec);
        mLayout.layout(0, 0, mLayout.getMeasuredWidth(), mLayout.getMeasuredHeight());
        setDirty(false);
    }

    private final class InternalLayout extends FrameLayout
    {
        public InternalLayout(Context context)
        {
            super(context);
        }

        @Override
        public void requestLayout()
        {
            super.requestLayout();
            if (setDirty(true))
            {
                Log.i(TAG, "requestLayout " + FViewSpan.this);
                update();
            }
        }

        @Override
        protected void onDraw(Canvas canvas)
        {
            super.onDraw(canvas);
            setPrepared(true);
        }
    }

    private void setPrepared(boolean prepared)
    {
        if (mIsPrepared != prepared)
        {
            mIsPrepared = prepared;
            Log.i(TAG, "setPrepared:" + prepared + " " + FViewSpan.this);
            if (prepared)
            {
                mTextView.addOnAttachStateChangeListener(mOnAttachStateChangeListener);
                onPrepared();
            } else
            {
                mTextView.removeOnAttachStateChangeListener(mOnAttachStateChangeListener);
                onDestroy();
            }
        }
    }

    private final View.OnAttachStateChangeListener mOnAttachStateChangeListener = new View.OnAttachStateChangeListener()
    {
        @Override
        public void onViewAttachedToWindow(View v)
        {
        }

        @Override
        public void onViewDetachedFromWindow(View v)
        {
            setPrepared(false);
        }
    };

    protected void onPrepared()
    {
    }

    protected void onDestroy()
    {
    }

    public enum AlignType
    {
        bottom,
        baseline
    }

    public enum HeightType
    {
        match,
        ascent
    }

    private static void removeViewFromParent(final View view)
    {
        if (view == null)
            return;

        final ViewParent parent = view.getParent();
        if (parent == null)
            return;

        try
        {
            final ViewGroup viewGroup = (ViewGroup) parent;
            viewGroup.removeView(view);
        } catch (Exception e)
        {
        }
    }
}
