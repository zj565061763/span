
# AndroidSpan
实现富文本TextView和EditText

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
