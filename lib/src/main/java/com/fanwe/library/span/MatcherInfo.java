package com.fanwe.library.span;

import android.text.TextUtils;

public class MatcherInfo
{
	public static final String KEY_DEFAULT_PREFIX = "KEY";

	private String key = createDefaultKey();
	private int start = -1;
	private int end = -1;
	private String pattern;

	public static String createDefaultKey()
	{
		long keyEnd = System.currentTimeMillis();
		String key = "[" + KEY_DEFAULT_PREFIX + keyEnd + "]";
		return key;
	}

	public String getKey()
	{
		return key;
	}

	public void setKey(String key)
	{
		this.key = key;
		updateIndex();
	}

	public int getStart()
	{
		return start;
	}

	public void setStart(int start)
	{
		this.start = start;
		updateIndex();
	}

	public int getEnd()
	{
		return end;
	}

	public void setEnd(int end)
	{
		this.end = end;
		updateIndex();
	}

	private void updateIndex()
	{
		if (!TextUtils.isEmpty(key))
		{
			int length = key.length();
			if (start >= 0)
			{
				end = start + length;
			} else
			{
				if (end > 0)
				{
					start = end - length;
					if (start < 0)
					{
						start = 0;
					}
				}
			}
		}
	}

	public String getPattern()
	{
		return pattern;
	}

	public void setPattern(String pattern)
	{
		this.pattern = pattern;
	}

}
