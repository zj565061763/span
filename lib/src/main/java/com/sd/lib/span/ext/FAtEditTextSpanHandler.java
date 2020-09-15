package com.sd.lib.span.ext;

import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.widget.EditText;

import com.sd.lib.span.utils.FEditTextSpanHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FAtEditTextSpanHandler extends FEditTextSpanHandler
{
    private final String mMaskChar = "@";
    private final Map<String, UserInfo> mMapUserInfo = new ConcurrentHashMap<>();
    private final Map<Object, String> mMapAtSpan = new HashMap<>();

    private Callback mCallback;

    public FAtEditTextSpanHandler(EditText editText)
    {
        super(editText);
        editText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int beforeCount, int afterCount)
            {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int beforeCount, int afterCount)
            {
                if (beforeCount == 0 && afterCount == 1)
                {
                    final String newChar = String.valueOf(s.charAt(start));
                    if (mMaskChar.equals(newChar))
                    {
                        if (mCallback != null)
                            mCallback.onInputAt();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {
            }
        });
    }

    /**
     * 设置回调
     *
     * @param callback
     */
    public final void setCallback(Callback callback)
    {
        mCallback = callback;
    }

    /**
     * 添加用户
     *
     * @param userId
     * @param userName
     * @return true-添加成功；false-添加失败或者已添加
     */
    public final boolean addUser(String userId, String userName)
    {
        if (TextUtils.isEmpty(userId) || TextUtils.isEmpty(userName))
            return false;

        if (mMapUserInfo.containsKey(userId))
            return false;

        final String preChar = getPreChar();
        if (mMaskChar.equals(preChar))
        {
            final int deleteIndex = getEditText().getSelectionStart();
            getEditText().getText().delete(deleteIndex - 1, deleteIndex);
        }

        final String spanText = mMaskChar + userName;

        final Object span = createUserSpan();
        if (span == null)
            throw new RuntimeException("createUserSpan return null");

        final SpanInfo spanInfo = insertSpan(spanText, span);
        if (spanInfo != null)
        {
            mMapAtSpan.put(spanInfo.getSpan(), "");

            final UserInfo userInfo = new UserInfo();
            userInfo.userId = userId;
            userInfo.userName = userName;
            userInfo.span = spanInfo.getSpan();
            mMapUserInfo.put(userId, userInfo);

            if (mCallback != null)
                mCallback.onUserAdd(userId);

            return true;
        }

        return false;
    }

    /**
     * 是否包含某个用户
     *
     * @param userId
     * @return
     */
    public final boolean hasUser(String userId)
    {
        if (TextUtils.isEmpty(userId))
            return false;

        return mMapUserInfo.containsKey(userId);
    }

    /**
     * 移除用户
     *
     * @param userId
     * @return
     */
    public final boolean removeUser(String userId)
    {
        if (TextUtils.isEmpty(userId))
            return false;

        final UserInfo userInfo = mMapUserInfo.get(userId);
        if (userInfo == null)
            return false;

        return removeSpan(userInfo.span, true);
    }

    @Override
    protected void onSpanRemove(Object span)
    {
        super.onSpanRemove(span);
        mMapAtSpan.remove(span);
        for (UserInfo item : mMapUserInfo.values())
        {
            if (item.span.equals(span))
            {
                mMapUserInfo.remove(item.userId);
                if (mCallback != null)
                    mCallback.onUserRemove(item.userId);

                break;
            }
        }
    }

    /**
     * 返回所有用户信息
     *
     * @return
     */
    public final List<UserInfo> getAllUser()
    {
        return new ArrayList<>(mMapUserInfo.values());
    }

    /**
     * 返回光标前面的字符
     *
     * @return
     */
    private String getPreChar()
    {
        if (getEditText().getText().length() <= 0)
            return "";

        final int selectionStart = getEditText().getSelectionStart();
        final int index = selectionStart - 1;
        if (index < 0)
            return "";

        return String.valueOf(getEditText().getText().charAt(index));
    }

    /**
     * 分发按键事件
     *
     * @param keyCode
     * @param event
     * @return
     */
    public final boolean dispatchKeyEvent(int keyCode, KeyEvent event)
    {
        if (event.getAction() == KeyEvent.ACTION_DOWN)
        {
            if (keyCode == KeyEvent.KEYCODE_DEL)
            {
                if (shouldSelectAtSpan())
                    return true;
            }
        }

        return false;
    }

    @Override
    public boolean pressDeleteKey()
    {
        if (shouldSelectAtSpan())
            return true;
        return super.pressDeleteKey();
    }

    private boolean shouldSelectAtSpan()
    {
        final SpanInfo spanInfo = getCursorSpanInfo();
        if (spanInfo != null && mMapAtSpan.containsKey(spanInfo.getSpan()))
        {
            getEditText().setSelection(spanInfo.getStart(), spanInfo.getEnd());
            return true;
        }
        return false;
    }

    /**
     * 创建用户span
     *
     * @return
     */
    protected Object createUserSpan()
    {
        return new ForegroundColorSpan(Color.RED);
    }

    public interface Callback
    {
        void onInputAt();

        void onUserAdd(String userId);

        void onUserRemove(String userId);
    }

    public static final class UserInfo
    {
        private String userId;
        private String userName;
        private Object span;

        public String getUserId()
        {
            return userId;
        }

        public String getUserName()
        {
            return userName;
        }

        public Object getSpan()
        {
            return span;
        }
    }
}
