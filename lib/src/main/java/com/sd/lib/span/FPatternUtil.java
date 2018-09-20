package com.sd.lib.span;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FPatternUtil
{
    /**
     * http连接
     */
    public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
    /**
     * 中括号
     */
    public static final String REGEX_BRACKETS_MIDDLE = "\\[([^\\[\\]]+)\\]";

    /**
     * 找到内容中所有匹配正则表达式的信息
     *
     * @param regex   正则表达式
     * @param content 内容
     * @return
     */
    public static List<MatcherInfo> findMatcherInfo(final String regex, String content)
    {
        final List<MatcherInfo> listModel = new ArrayList<>();

        final Matcher matcher = Pattern.compile(regex).matcher(content);
        if (matcher != null)
        {
            while (matcher.find())
            {
                final MatcherInfo info = new MatcherInfo(matcher.group());
                info.setPattern(regex);
                info.setStart(matcher.start());

                listModel.add(info);
            }
        }

        return listModel;
    }
}
