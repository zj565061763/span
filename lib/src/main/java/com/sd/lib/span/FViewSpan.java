package com.sd.lib.span;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.style.ReplacementSpan;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public class FViewSpan extends ReplacementSpan
{
    private static final String TAG = FViewSpan.class.getSimpleName();

    private final TextView mTextView;
    private final InternalLayout mLayout;
    private Paint.FontMetricsInt mFontMetricsInt;

    private volatile boolean mIsPrepared = false;
    private volatile boolean mIsDirty = false;

    public FViewSpan(View view, TextView textView)
    {
        if (view == null || textView == null)
            throw new NullPointerException("null");

        mTextView = textView;
        mLayout = new InternalLayout(view.getContext());
        mLayout.addView(view);

        textView.addOnAttachStateChangeListener(mOnAttachStateChangeListener);
    }

    /**
     * 更新span
     */
    public void update()
    {
        Log.i(TAG, "update " + FViewSpan.this);
        final CharSequence text = mTextView.getText();
        mTextView.setText(text);
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
            mIsPrepared = false;
            onDestroy();
        }
    };

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint)
    {
        Log.i(TAG, "draw " + FViewSpan.this);

        canvas.save();
        canvas.translate(x, 0);
        mLayout.draw(canvas);
        canvas.restore();
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm)
    {
        if (fm != null)
            mFontMetricsInt = fm;

        final int height = getLineHeight();
        if (mIsDirty)
        {
            measureLayout(height);
            Log.i(TAG, "getSize width:" + mLayout.getMeasuredWidth() + " height:" + height + " " + FViewSpan.this);
        }

        return mLayout.getMeasuredWidth();
    }

    private int getLineHeight()
    {
        final int measuredHeight = mLayout.getMeasuredHeight();

        int height = 0;
        final Paint.FontMetricsInt fm = mFontMetricsInt;
        if (fm != null)
            height = fm.bottom - fm.top;

        if (height != measuredHeight)
            setDirty(true);

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
            if (!mIsPrepared)
            {
                mIsPrepared = true;
                onPrepared();
            }
        }
    }

    protected void onPrepared()
    {
        Log.i(TAG, "onPrepared " + FViewSpan.this);
    }

    protected void onDestroy()
    {
        Log.i(TAG, "onDestroy " + FViewSpan.this);
    }
}
