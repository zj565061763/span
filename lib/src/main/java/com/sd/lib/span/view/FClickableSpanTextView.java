package com.sd.lib.span.view;

import android.content.Context;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.method.Touch;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.emoji.widget.EmojiAppCompatTextView;

public class FClickableSpanTextView extends EmojiAppCompatTextView
{
    public FClickableSpanTextView(Context context)
    {
        super(context);
        init();
    }

    public FClickableSpanTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public FClickableSpanTextView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private boolean mIsClickSpan;
    private OnClickListener mOnClickListener;

    private void init()
    {
        final MovementMethod movementMethod = getMovementMethod();
        if (movementMethod == null)
            setMovementMethod(InternalLinkMovementMethod.getInstance());
    }

    @Override
    public void setOnClickListener(OnClickListener listener)
    {
        mOnClickListener = listener;
        if (!mIsClickSpan)
            super.setOnClickListener(listener);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        boolean result = false;

        mIsClickSpan = false;
        final boolean superResult = super.onTouchEvent(event);

        if (mIsClickSpan)
        {
            result = true;
            removeOnClickListener();
        } else
        {
            result = superResult;
        }

        if (event.getAction() == MotionEvent.ACTION_UP)
            restoreOnClickListener();

        return result;
    }

    private void removeOnClickListener()
    {
        removeCallbacks(mRestoreClickRunnable);
        super.setOnClickListener(null);
    }

    private void restoreOnClickListener()
    {
        removeCallbacks(mRestoreClickRunnable);
        post(mRestoreClickRunnable);
    }

    private final Runnable mRestoreClickRunnable = new Runnable()
    {
        @Override
        public void run()
        {
            FClickableSpanTextView.super.setOnClickListener(mOnClickListener);
        }
    };

    private static class InternalLinkMovementMethod extends LinkMovementMethod
    {
        private static InternalLinkMovementMethod sInstance;

        public static InternalLinkMovementMethod getInstance()
        {
            if (sInstance == null)
                sInstance = new InternalLinkMovementMethod();
            return sInstance;
        }

        @Override
        public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event)
        {
            int action = event.getAction();

            if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_DOWN)
            {
                int x = (int) event.getX();
                int y = (int) event.getY();

                x -= widget.getTotalPaddingLeft();
                y -= widget.getTotalPaddingTop();

                x += widget.getScrollX();
                y += widget.getScrollY();

                Layout layout = widget.getLayout();
                int line = layout.getLineForVertical(y);
                int off = layout.getOffsetForHorizontal(line, x);

                ClickableSpan[] links = buffer.getSpans(off, off, ClickableSpan.class);

                if (links.length != 0)
                {
                    ClickableSpan link = links[0];
                    if (action == MotionEvent.ACTION_UP)
                    {
                        link.onClick(widget);
                    } else if (action == MotionEvent.ACTION_DOWN)
                    {
                        Selection.setSelection(buffer,
                                buffer.getSpanStart(link),
                                buffer.getSpanEnd(link));
                    }

                    if (widget instanceof FClickableSpanTextView)
                    {
                        ((FClickableSpanTextView) widget).mIsClickSpan = true;
                    }

                    return true;
                } else
                {
                    Selection.removeSelection(buffer);
                    Touch.onTouchEvent(widget, buffer, event);
                    return false;
                }
            }

            return super.onTouchEvent(widget, buffer, event);
        }
    }
}
