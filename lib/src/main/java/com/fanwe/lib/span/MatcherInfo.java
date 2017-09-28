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
