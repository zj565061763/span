package com.sd.lib.span;

public class MatcherInfo
{
    private String key;
    private int start = -1;
    private int end = -1;
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
