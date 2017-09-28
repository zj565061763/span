/*
 * Copyright (C) 2017 zhengjun, fanwe (http://www.fanwe.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fanwe.lib.span.view;

import android.content.Context;
import android.text.Spannable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.widget.EditText;

import com.fanwe.lib.span.MatcherInfo;

public class SDSpannableEditText extends EditText
{
    public SDSpannableEditText(Context context)
    {
        super(context);
    }

    public SDSpannableEditText(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    private SparseArray<SpanInfo> mArrSpan = new SparseArray<>();

    /**
     * 在光标位置插入span
     *
     * @param span span对象
     * @param key  对应的key
     */
    public void insertSpan(Object span, String key)
    {
        SpanInfo spanInfo = new SpanInfo(span);

        MatcherInfo matcherInfo = new MatcherInfo();
        matcherInfo.setKey(key);

        spanInfo.matcherInfo = matcherInfo;

        insertSpan(spanInfo);
    }

    private void insertSpan(SpanInfo spanInfo)
    {
        MatcherInfo matcherInfo = spanInfo.matcherInfo;
        Object span = spanInfo.span;

        matcherInfo.setStart(getSelectionStart());
        append(matcherInfo.getKey());
        getText().setSpan(span, matcherInfo.getStart(), matcherInfo.getEnd(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mArrSpan.put(matcherInfo.getEnd(), spanInfo);
    }

    /**
     * 移除光标前面的span
     */
    public boolean removeSpan()
    {
        int index = getSelectionStart();
        SpanInfo spanInfo = mArrSpan.get(index);
        if (spanInfo != null)
        {
            MatcherInfo matcherInfo = spanInfo.matcherInfo;
            getText().delete(matcherInfo.getStart(), matcherInfo.getEnd());
            mArrSpan.remove(index);
            return true;
        } else
        {
            return false;
        }
    }

    private class SpanInfo
    {
        public Object span;
        public MatcherInfo matcherInfo;

        public SpanInfo(Object span)
        {
            this.span = span;
        }
    }
}
