package com.sd.lib.span;

public class MatcherInfo
{
    /**
     * 匹配到的内容
     */
    private String key;
    /**
     * 内容的起始位置
     */
    private int start = -1;
    /**
     * 内容的结束位置
     */
    private int end = -1;
    /**
     * 正则表达式
     */
    private String pattern;

    public MatcherInfo(String key)
    {
        if (key == null)
            throw new NullPointerException();

        this.key = key;
    }

    public void setStart(int start)
    {
        this.start = start;
        this.end = start + key.length();
    }

    public String getKey()
    {
        return key;
    }

    public int getStart()
    {
        return start;
    }

    public int getEnd()
    {
        return end;
    }

    public String getPattern()
    {
        return pattern;
    }

    public void setPattern(String pattern)
    {
        this.pattern = pattern;
    }

    public static String createDefaultKey()
    {
        final String key = "[KEY_" + System.currentTimeMillis() + "]";
        return key;
    }
}
