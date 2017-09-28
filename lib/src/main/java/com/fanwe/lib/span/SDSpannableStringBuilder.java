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
package com.fanwe.lib.span;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;

public class SDSpannableStringBuilder extends SpannableStringBuilder
{

    public static final int DEFAULT_FLAG = Spannable.SPAN_EXCLUSIVE_EXCLUSIVE;

    /**
     * 在末尾添加span
     *
     * @param span span对象
     * @param key  span对应的key
     */
    public void appendSpan(Object span, CharSequence key)
    {
        appendSpan(span, key, DEFAULT_FLAG);
    }

    /**
     * 在末尾添加span
     *
     * @param span  span对象
     * @param key   span对应的key
     * @param flags
     */
    public void appendSpan(Object span, CharSequence key, int flags)
    {
        if (span != null && !TextUtils.isEmpty(key))
        {
            append(key);
            int end = length();
            int start = end - key.length();
            setSpan(span, start, end, flags);
        }
    }

    /**
     * 在旧的span上面叠加一个span内容
     *
     * @param spanOld 旧的已添加span
     * @param spanNew 新的span
     */
    public void addSpan(Object spanOld, Object spanNew)
    {
        addSpan(spanOld, spanNew, DEFAULT_FLAG);
    }

    /**
     * 在旧的span上面叠加一个span内容
     *
     * @param spanOld 旧的已添加span
     * @param spanNew 新的span
     * @param flags
     */
    public void addSpan(Object spanOld, Object spanNew, int flags)
    {
        if (spanNew != null && spanOld != null)
        {
            int end = getSpanEnd(spanOld);
            int start = getSpanStart(spanOld);

            setSpan(spanNew, start, end, flags);
        }
    }

    /**
     * 设置span
     *
     * @param what  span对象
     * @param start 开始位置
     * @param end   结束位置
     */
    public void setSpan(Object what, int start, int end)
    {
        setSpan(what, start, end, DEFAULT_FLAG);
    }

    /**
     * 设置span
     *
     * @param what        span对象
     * @param matcherInfo 匹配信息对象
     */
    public void setSpan(Object what, MatcherInfo matcherInfo)
    {
        if (matcherInfo != null)
        {
            setSpan(what, matcherInfo.getStart(), matcherInfo.getEnd());
        }
    }
}
