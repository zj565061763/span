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

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SDPatternUtil
{
    public static final String PATTERN_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";

    /**
     * 通过正则表达式匹配
     *
     * @param strPattern 正则表达式
     * @param strContent 内容
     * @return
     */
    private static Matcher matcher(String strPattern, String strContent)
    {
        Pattern pattern = Pattern.compile(strPattern);
        Matcher matcher = pattern.matcher(strContent);
        return matcher;
    }

    /**
     * 获取所有匹配正则表达式的字符串
     *
     * @param strPattern 正则表达式
     * @param strContent 内容
     * @return
     */
    public static List<String> find(String strPattern, String strContent)
    {
        List<String> listKeys = new ArrayList<>();

        Matcher matcher = matcher(strPattern, strContent);
        if (matcher != null)
        {
            while (matcher.find())
            {
                String key = matcher.group(0);
                listKeys.add(key);
            }
        }
        return listKeys;
    }


    /**
     * 找到关键字key在内容中的所有起始位置
     *
     * @param content 内容
     * @param key     关键字
     * @return
     */
    public static List<Integer> findPosition(String content, String key)
    {
        List<Integer> listPosition = new ArrayList<>();
        if (content != null && !TextUtils.isEmpty(key))
        {
            int searchIndex = 0;
            int index = content.indexOf(key, searchIndex);
            while (index >= 0)
            {
                listPosition.add(index);
                searchIndex = index + 1;
                index = content.indexOf(key, searchIndex);
            }
        }
        return listPosition;
    }

    /**
     * 找到内容中所有匹配正则表达式的信息
     *
     * @param strPattern 正则表达式
     * @param strContent 内容
     * @return
     */
    public static List<MatcherInfo> findMatcherInfo(final String strPattern, String strContent)
    {
        List<MatcherInfo> listModel = new ArrayList<>();
        List<String> listKey = find(strPattern, strContent);
        if (listKey != null && !listKey.isEmpty())
        {
            Integer position = null;
            Map<String, List<Integer>> mapKeyPositions = new HashMap<>();
            for (String key : listKey)
            {
                List<Integer> listPosition = null;
                if (!mapKeyPositions.containsKey(key))
                {
                    listPosition = findPosition(strContent, key);
                    mapKeyPositions.put(key, listPosition);
                } else
                {
                    listPosition = mapKeyPositions.get(key);
                }

                if (listPosition.size() > 0)
                {
                    position = listPosition.remove(0);
                    if (position != null)
                    {
                        MatcherInfo matcherInfo = new MatcherInfo();
                        matcherInfo.setKey(key);
                        matcherInfo.setStart(position);
                        matcherInfo.setPattern(strPattern);

                        listModel.add(matcherInfo);
                    }
                }
            }
        }
        return listModel;
    }
}
