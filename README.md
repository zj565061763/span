
## Gradle
[![](https://jitpack.io/v/zj565061763/span.svg)](https://jitpack.io/#zj565061763/span)

## 实际项目效果图
![](http://thumbsnap.com/i/QeKxWmbM.png?0718)

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
SpannableStringBuilder builder = new SpannableStringBuilder();
builder.append("f");

FImageSpan span = new FImageSpan(getApplicationContext(), R.drawable.face);
// 设置对齐字体底部
span.setVerticalAlignType(ImageSpanHelper.VerticalAlignType.ALIGN_BOTTOM);
// 设置图片宽度，内部会按比例缩放
span.setWidth(100);
// 设置左边间距
span.setMarginLeft(10);
// 设置右边间距
span.setMarginRight(10);
// 将span添加到SpannableStringBuilder
FSpanUtil.appendSpan("launcher", span, builder);
tv.setText(builder);
```
## EditText效果
![](http://thumbsnap.com/i/8T87giV5.gif?0718)<br>

1. xml布局
```xml
<EditText
    android:id="@+id/et"
    android:layout_width="match_parent"
    android:layout_height="50dp"/>
```
2. java代码
```java
final FEditTextSpanHandler editTextSpanHandler = new FEditTextSpanHandler((EditText) findViewById(R.id.et));

btn_add.setOnClickListener(new View.OnClickListener()
{
    @Override
    public void onClick(View v)
    {
        // 插入span
        editTextSpanHandler.insertSpan("face", new FImageSpan(EditTextActivity.this, R.drawable.face));
    }
});

btn_remove.setOnClickListener(new View.OnClickListener()
{
    @Override
    public void onClick(View v)
    {
        // 删除span
        editTextSpanHandler.removeSpan();
    }
});
```
## 正则表达式筛选替换
用正则表达式筛选出被中括号包围的内容，并把它替换为资源图片中名字为该内容的图片<br>
效果图：<br>
![](http://thumbsnap.com/s/a5Dgu0Cj.png?0718)<br>

1. xml布局
```xml
<TextView
    android:id="@+id/tv"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"/>
```

2. java代码
```java
public class PatternActivity extends AppCompatActivity
{
    private FTextPattern mTextPattern;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_pattern);
        tv = findViewById(R.id.tv);

        tv.setMovementMethod(LinkMovementMethod.getInstance());
        tv.setHighlightColor(Color.TRANSPARENT);

        // 开始正则表达式匹配
        final CharSequence text = getTextPattern().process("fdkfsofosi[face]fdsfsdf[face]54654655[face]654654");
        tv.setText(text);
    }

    private FTextPattern getTextPattern()
    {
        if (mTextPattern == null)
        {
            mTextPattern = new FTextPattern();
            mTextPattern.addMatchCallback(new FTextPattern.MatchCallback()
            {
                @Override
                public String getRegex()
                {
                    /**
                     * 匹配中括号的内容
                     */
                    return "\\[([^\\[\\]]+)\\]";
                }

                @Override
                public void onMatch(Matcher matcher, SpannableStringBuilder builder)
                {
                    final String key = matcher.group();
                    final int start = matcher.start();
                    final int end = matcher.end();

                    // 截取中括号中的名称
                    final String name = key.substring(1, key.length() - 1);
                    // 根据名称获得资源id
                    final int resId = getResources().getIdentifier(name, "drawable", getPackageName());
                    if (resId != 0)
                    {
                        // 添加表情span
                        builder.setSpan(new ImageSpan(PatternActivity.this, resId), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }

                    builder.setSpan(new ClickableSpan()
                    {
                        @Override
                        public void onClick(View widget)
                        {
                            Toast.makeText(PatternActivity.this, "span clicked", Toast.LENGTH_SHORT).show();
                        }
                    }, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                }
            });
        }
        return mTextPattern;
    }
}
```

## 加载网络图片并用span展示
实现TextView中展示网络图片，demo中用Glide框架加载图片演示<br>
效果图:<br>
![](http://thumbsnap.com/i/UmzIoF5v.gif?0718)<br>

1. 自定义Span
```java
public class NetImageSpan extends FDynamicDrawableSpan
{
    private String mUrl; //图片url地址
    private Bitmap mBitmap;

    /**
     * @param view span要依附的view
     */
    public NetImageSpan(View view)
    {
        super(view);
    }

    public void setUrl(String url)
    {
        mUrl = url;
    }

    @Override
    protected int getDefaultDrawableResId()
    {
        //返回图片未加载成功之前的占位图片
        return R.drawable.ic_default;
    }

    @Override
    protected Bitmap onGetBitmap()
    {
        if (mBitmap == null || mBitmap.isRecycled())
        {
            Glide.with(getContext()).load(mUrl).asBitmap().into(new SimpleTarget<Bitmap>()
            {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation)
                {
                    mBitmap = resource; //demo演示简单在span内部保存，具体项目中应该把Bitmap对象存到app的缓存管理中
                    getView().postInvalidate(); //加载成功后，刷新View
                }
            });
        }
        return mBitmap;
    }
}
```

2. java代码
```java
NetImageSpan span = new NetImageSpan(tv);
span.setUrl("https://www.baidu.com/img/bd_logo1.png");
span.setWidth(200);

SpannableStringBuilder builder = new SpannableStringBuilder();
FSpanUtil.appendSpan("span", span, builder);
tv.setText(builder);
```
