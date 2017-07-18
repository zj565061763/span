
# AndroidSpan
实现富文本TextView和EditText，可以调节span的宽度以及间距

## TextView效果
![](http://thumbsnap.com/i/uKnMNBqU.gif?0718)<br>

1. xml布局
```xml
<TextView
    android:id="@+id/tv"
    android:layout_width="wrap_content"
    android:layout_height="300dp"
    android:background="@color/red"
    android:gravity="bottom"/>
```
2. java代码
```java
SDSpannableStringBuilder sb = new SDSpannableStringBuilder();
sb.append("f");

SDImageSpan span = new SDImageSpan(getApplicationContext(), R.drawable.face);
span.setVerticalAlignType(IImageSpanHelper.VerticalAlignType.ALIGN_BOTTOM); //设置对齐字体底部（默认对齐方式）
span.setWidth(100); //设置图片宽度，内部会按比例缩放
span.setMarginLeft(10); //设置左边间距
span.setMarginRight(10); //设置右边间距

sb.appendSpan(span, "launcher");
tv.setText(sb);
```
## EditText效果
![](http://thumbsnap.com/i/8T87giV5.gif?0718)<br>

1. xml布局
```xml
<com.fanwe.library.span.view.SDSpannableEditText
    android:id="@+id/et"
    android:layout_width="match_parent"
    android:layout_height="50dp"/>
```
2. java代码
```java
btn_add.setOnClickListener(new View.OnClickListener()
{
    @Override
    public void onClick(View v)
    {
        SDImageSpan span = new SDImageSpan(getApplicationContext(), R.drawable.face);
        et.insertSpan(span, "face"); //插入span
    }
});

btn_remove.setOnClickListener(new View.OnClickListener()
{
    @Override
    public void onClick(View v)
    {
        et.removeSpan(); //删除span
    }
});
```
## 正则表达式筛选替换
用正则表达式筛选出被中括号包围的内容，并把它替换为资源图片中名字为该内容的图片<br>
效果图：<br>
![](http://thumbsnap.com/s/a5Dgu0Cj.png?0718)<br>

1. 自定义TextView
```java
public class CustomTextView extends SDSpannableTextView
{
    public CustomTextView(Context context)
    {
        super(context);
    }

    public CustomTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    protected void onProcessSpannableStringBuilder(SDSpannableStringBuilder builder)
    {
        //正则表达式匹配[***]中括号这种规则的字符串
        List<MatcherInfo> list = SDPatternUtil.findMatcherInfo("\\[([^\\[\\]]+)\\]", builder.toString());
        for (final MatcherInfo info : list)
        {
            String key = info.getKey(); //获得匹配的字符串
            key = key.substring(1, key.length() - 1); //移除中括号，得到文件名
            int resId = getIdentifierDrawable(key); //根据文件名获得图片资源id
            if (resId != 0)
            {
                SDImageSpan span = new SDImageSpan(getContext(), resId);
                builder.setSpan(span, info); //用span，替换匹配到的字符串
            }
        }
    }

    public int getIdentifierDrawable(String name)
    {
        return getResources().getIdentifier(name, "drawable", SDPackageUtil.getPackageName());
    }
}
```

2. xml布局
```xml
<com.fanwe.www.androidspan.CustomTextView
    android:id="@+id/tv"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"/>
```

3. java代码
```java
tv.setText("fdkfsofosi[face]fdsfsdf[face]");
```
