package com.dahai.mtest.view.wheelView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.dahai.mtest.R;

import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by 张海洋 on 2017-11-11.
 */

public class WheelView extends View {
    private static final String TAG = "WheelView";

    public enum ACTION {    // 点击，滑翔（滑倒尽头），拖拽事件
        CLICK,FLING,DAGGLE
    }
    public enum DividerType {   // 分割线类型
        FILL,WRAP
    }

    private DividerType dividerType;    // 分隔线类型

    Context context;

    Handler handler;
    private GestureDetector gestureDetector;
    OnItemSelectedListener onItemSelectedListener;

    private boolean isOptions = false;
    private boolean isCenterLabel = true;

    // Timer mTimer
    ScheduledExecutorService mExecutor = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture<?> mFuture;

    Paint paintOuterText;
    Paint paintCenterText;
    Paint paintIndicator;

    WheelAdapter adapter;

    private String label;   // 附加单位。
    int textSize;           // 选项的文字大小。
    int maxTextWidth;
    int maxTextHeight;
    float itemHeight;       // 每行高度。

    Typeface typeface = Typeface.MONOSPACE;     // 字体样式，默认是等宽字体。

    int textColorOut = 0xFFa8a8a8;
    int textColorCenter = 0x2a2a2a;
    int dividerColor = 0xFFd5d5d5;

    // 条目间距倍数
    float lineSpacingMultiplier = 1.6F;
    boolean isLoop;

    // 第一条线Y坐标值
    float firstLineY;
    // 第二条线Y坐标
    float secondLineY;
    // 中间label绘制的Y坐标
    float centerY;

    //滚动总高度y值
    float totalScrollY;
    // 初始化默认选中项
    int initPosition;
    // 选中的Item是第几个
    private int selectedItem;
    int preCurrentIndex;
    // 滚动偏移值，用于记录滚动了多少个item
    int change;

    // 绘制几个条目，实际上第一项和最后项Y轴压缩成0%了，所以可见的树木实际为9
    int itemsVisible = 11;

    int measuredHeight;     // WheelView控件高度。
    int measuredWidth;      // WheelView控件宽度。

    // 半圆周长
    int halfCircumference;
    // 半径
    int radius;

    private int mOffset = 0;
    private float previousY = 0;
    long startTime = 0;

    // 修改这个值可以改变滑行速度
    private static final int VELOCITYFLING = 5;
    int widthMeasureSpec;

    private int mGravity = Gravity.CENTER;
    private int drawCenterContentStart = 0; // 中间选中文字开始绘制位置
    private int drawOutContentStart = 0;    // 非中间文字开始绘制位置
    private static final float SCALECONTENT = 0.8F;     // 非中间位置则用此控制高度，压扁形成3d错觉
    private float CENTERCONTENTOFFSET;              // 偏移量。

    public WheelView(Context context) {
        super(context);
        init(context,null);
    }

    public WheelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public WheelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        textSize = getResources().getDimensionPixelSize(R.dimen.pickerview_textsize);   // 默认大小

        DisplayMetrics dm = getResources().getDisplayMetrics();
        float density = dm.density;
       /* Log.e(TAG,"density == " + density);
        Log.e(TAG,"宽度px == " + dm.widthPixels);
        Log.e(TAG,"宽dpi == " + dm.xdpi);
        Log.e(TAG,"" + dm.densityDpi);*/
       if (density < 1) {               // 根据密度不同进行适配
           CENTERCONTENTOFFSET = 2.4f;
       } else if (1<= density && density<2) {
           CENTERCONTENTOFFSET = 3.6f;
       } else if (1<= density && density<2) {
           CENTERCONTENTOFFSET = 4.5f;
       } else if (2 <= density && density < 3) {
           CENTERCONTENTOFFSET = 6.0f;
       } else if (density>= 3) {
           CENTERCONTENTOFFSET = density*2.5f;
       }

       if (attrs != null) {
           TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.pickerview,0,0);
           mGravity = a.getInt(R.styleable.pickerview_pickerview_gravity,Gravity.CENTER);
           textColorOut = a.getColor(R.styleable.pickerview_pickerview_textColorOut,textColorOut);
           textColorCenter = a.getColor(R.styleable.pickerview_pickerview_textColorCenter,textColorCenter);
           dividerColor = a.getColor(R.styleable.pickerview_pickerview_dividerColor,textSize);
           textSize = a.getDimensionPixelOffset(R.styleable.pickerview_pickerview_textSize,textSize);
           lineSpacingMultiplier = a.getFloat(R.styleable.pickerview_pickerview_lineSpacingMultiplier,lineSpacingMultiplier);
           a.recycle();         // 回收内存。
       }

       judgeLineSpace();

       initLoopView(context);
    }

    /**
     *  判断间距是否在1.0 - 2.0 之间
     */
    private void judgeLineSpace(){
        if (lineSpacingMultiplier < 1.2f) {
            lineSpacingMultiplier = 1.2f;
        } else if (lineSpacingMultiplier > 2.0f) {
            lineSpacingMultiplier = 2.0f;
        }
    }

    private void initLoopView(Context context) {
        this.context = context;
        handler = new MessageHandler(this);
        gestureDetector = new GestureDetector(context,new LoopViewGestureListener(this));
        gestureDetector.setIsLongpressEnabled(false);

        isLoop = true;

        totalScrollY = 0;
        initPosition = -1;

        initPaints();
    }

    private void initPaints(){
        paintOuterText = new Paint();
        paintOuterText.setColor(textColorOut);
        paintOuterText.setAntiAlias(true);
        paintOuterText.setTypeface(typeface);
        paintOuterText.setTextSize(textSize);

        paintCenterText = new Paint();
        paintCenterText.setColor(textColorCenter);
        paintCenterText.setAntiAlias(true);
        paintCenterText.setTextScaleX(1.1f);    // 沿X轴的缩放。
        paintCenterText.setTypeface(typeface);
        paintCenterText.setTextSize(textSize);

        paintIndicator = new Paint();
        paintIndicator.setColor(dividerColor);
        paintIndicator.setAntiAlias(true);

        setLayerType(LAYER_TYPE_SOFTWARE,null);         // 关闭硬件加速。
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.widthMeasureSpec = widthMeasureSpec;
        remeasure();
        setMeasuredDimension(measuredWidth,measuredHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        if (adapter == null) {
            return;
        }
        //initPosition越界会造成precurrentIndex的值不正确
        if (initPosition < 0) {
            initPosition = 0;
        }
        if (initPosition >= adapter.getItemsCount()) {
            initPosition = adapter.getItemsCount() - 1;
        }
        // 可见的item数组
        Object visibles[] = new Object[itemsVisible];
        // 滚动的Y值高度出去每行Item的高度，得到滚动了多少个item，机change数
        change = (int)(totalScrollY / itemHeight);

        try {
            preCurrentIndex = initPosition + change % adapter.getItemsCount();
        } catch (ArithmeticException e) {
            Log.e("WheelView","出错了！adapter.getItemsCount() == 0,联动数据不匹配");
        }

        if (!isLoop) {  // 不循环的情况
            if (preCurrentIndex < 0) {
                preCurrentIndex = 0;
            }
            if (preCurrentIndex > adapter.getItemsCount() - 1) {
                preCurrentIndex = adapter.getItemsCount() - 1;
            }
        } else {    // 循环
            if (preCurrentIndex < 0) {      // 举个例子：如果总数是5，preCurrentIndex = -1,那么preCurrentIndex按循环来说，其实是0的上面，也就是4的位置
                preCurrentIndex = adapter.getItemsCount() + preCurrentIndex;
            }
            if (preCurrentIndex > adapter.getItemsCount() - 1) {
                preCurrentIndex = preCurrentIndex - adapter.getItemsCount();
            }
        }
        // 跟滚动流畅度有关，总滑动距离与每个item高度取余，即并不是一格格的滚动，每个item不一定滚到对应Rect里的，这个item对应各自的偏移量。
        float itemHeightOffset = (totalScrollY % itemHeight);

        //设置数组中每个元素的值
        int counter = 0;
        while(counter < itemsVisible) {
            // 索引值，即当前在控件中间的item看作数据源的中间，计算出相对源数据源的index值
            int index = preCurrentIndex - (itemsVisible / 2 - counter);
            // 判断是否循环，如果是循环数据数据源也使用相对循环的position获取对应的item值，如果不是循环则超出
            // 数据源范围使用""空白字符串填充，在界面上形成空白无数据的item项。
            if (isLoop) {
                index = getLoopMappingIndex(index);
                visibles[counter] = adapter.getItem(index);
            } else if (index < 0) {
                visibles[counter] ="";
            } else if (index > adapter.getItemsCount() - 1) {
                visibles[counter] = "";
            } else {
                visibles[counter] = adapter.getItem(index);
            }
            counter++;
        }
        // 绘制中间两条横线
        if (dividerType == DividerType.WRAP) {      // 横线长度仅包裹内容
            float startX;
            float endX;

            if (TextUtils.isEmpty(label)) {     // 隐藏Label的情况。
                startX = (measuredWidth - maxTextWidth) / 2 -12;
            } else {
                startX = (measuredWidth - maxTextWidth)/4 - 12;
            }

            if (startX <= 0) {      // 如果超过了WheelView的边缘
                startX = 10;
            }
            endX = measuredWidth - startX;
            canvas.drawLine(startX,firstLineY,endX,firstLineY,paintIndicator);
            canvas.drawLine(startX,secondLineY,endX,secondLineY,paintIndicator);
        } else {
            canvas.drawLine(0.0f,firstLineY,measuredWidth,firstLineY,paintIndicator);
            canvas.drawLine(0.0f,secondLineY,measuredWidth,secondLineY,paintIndicator);
        }

        // 只显示选中项Label文字的模式，并且Label文字不为空，则进行绘制
        if (!TextUtils.isEmpty(label) && isCenterLabel) {
            // 绘制文字，靠右并留出空隙
            int drawRightContentStart = measuredWidth - getTextWidth(paintCenterText,label);
            canvas.drawText(label,drawRightContentStart - CENTERCONTENTOFFSET,centerY,paintCenterText);
        }

        counter = 0;
        while(counter < itemsVisible) {
            canvas.save();
            // 弧长L = itemHeight * counter - itemHeightOffset
            // 求弧度 α = L/r (弧长/半径) [0,π]
            double radian = ((itemHeight * counter) - itemHeightOffset) / radius;
            // 弧度转换成角度（把半圆以Y轴为轴心向右转90度，使其处于第一象限以及第四象限）
            // angle[-90度，90度]
            float angle = (float)(90D - (radian/Math.PI) * 180D);   // item第一项，从90度开始，逐渐递减到-90度

            // 计算取值可能有细微偏值差，保证-90度到90度以外的不绘制
            if (angle >= 90f || angle <= -90f) {
                canvas.restore();
            } else {
                // 获取内容文字
                String contentText;

                // 如果是label每项都显示的模式，并且item内容不为空、label也不为空
                if (!isCenterLabel && !TextUtils.isEmpty(label) && !TextUtils.isEmpty(getContentText(visibles[counter]))){
                    contentText = getContentText(visibles[counter]) + label;
                } else {
                    contentText = getContentText(visibles[counter]);
                }

                reMeasureTextSize(contentText);
                // 计算开始绘制的位置
                measuredCenterContentStart(contentText);
                measuredOutContentStart(contentText);
                float translateY = (float)(radius - Math.cos(radian) * radius - (Math.sin(radian) * maxTextHeight) / 2d);
                // 根据Math.sin(radian) 来更改canvas坐标系原点，然后缩放画布，使得文字高度进行缩放，形成弧形3d视觉差
                canvas.translate(0.0f,translateY);
                canvas.scale(1.0f,(float) Math.sin(radian));
                if (translateY <= firstLineY && maxTextHeight + translateY >= firstLineY){
                    // 条目经过第一条线
                    canvas.save();
                    canvas.clipRect(0,0,measuredWidth,firstLineY - translateY);
                    canvas.scale(1.0f,(float) Math.sin(radian) * SCALECONTENT);
                    canvas.drawText(contentText,drawOutContentStart,maxTextHeight,paintOuterText);
                    canvas.restore();
                    canvas.save();
                    canvas.clipRect(0,firstLineY - translateY,measuredWidth,(int)(itemHeight));
                    canvas.scale(1.0f,(float)Math.sin(radian) * 1.0f);
                    canvas.drawText(contentText,drawCenterContentStart,maxTextHeight - CENTERCONTENTOFFSET,paintCenterText);
                    canvas.restore();
                } else if (translateY <= secondLineY && maxTextHeight + translateY >= secondLineY) {
                    //条目经过第二条线
                    canvas.save();
                    canvas.clipRect(0,0,measuredWidth,secondLineY - translateY);
                    canvas.scale(1.0f,(float)Math.sin(radian)*1.0f);
                    canvas.drawText(contentText,drawCenterContentStart,maxTextHeight - CENTERCONTENTOFFSET,paintCenterText);
                    canvas.restore();
                    canvas.save();
                    canvas.clipRect(0,secondLineY - translateY,measuredWidth,(int)(itemHeight));
                    canvas.scale(1.0f,(float) Math.sin(radian) * SCALECONTENT);
                    canvas.drawText(contentText,drawOutContentStart,maxTextHeight,paintOuterText);
                    canvas.restore();
                } else if (translateY >= firstLineY && maxTextHeight + translateY <= secondLineY) {
                    // 中间条目
                    // canvas.clipRect(0,0,measuredWidth,maxTextHeight);
                    // 让文字居中
                    float Y = maxTextHeight - CENTERCONTENTOFFSET;// 因为圆弧角换算的向下取值，导致角度稍微有点偏差，加上画笔的基线会偏上，因此需要偏移量修正一下
                    canvas.drawText(contentText,drawCenterContentStart,Y,paintCenterText);
                    int preSelectedItem = adapter.indexOf(visibles[counter]);

                    selectedItem = preSelectedItem;
                } else {
                    // 其他条目
                    canvas.save();
                    canvas.clipRect(0,0,measuredWidth,(int)(itemHeight));
                    canvas.scale(1.0f,(float) Math.sin(radian) * SCALECONTENT);
                    canvas.drawText(contentText,drawOutContentStart,maxTextHeight,paintOuterText);
                    canvas.restore();
                }
                canvas.restore();
                paintCenterText.setTextSize(textSize);
            }
            counter++;
        }
    }

    private void measuredOutContentStart(String content) {
        Rect rect = new Rect();
        paintOuterText.getTextBounds(content,0,content.length(),rect);
        switch (mGravity) {
            case Gravity.CENTER:
                if (isOptions || label == null || label.equals("") || !isCenterLabel) {
                    drawOutContentStart = (int) ((measuredWidth - rect.width()) * 0.5);
                } else {
                    drawOutContentStart = (int) ((measuredWidth - rect.width()) * 0.25);
                }
                break;
            case Gravity.LEFT:
                drawOutContentStart = 0;
                break;
            case Gravity.RIGHT:
                drawOutContentStart = measuredWidth - rect.width() - (int)CENTERCONTENTOFFSET;
                break;
        }
    }

    private void measuredCenterContentStart(String content) {
        Rect rect = new Rect();
        paintCenterText.getTextBounds(content,0,content.length(),rect);
        switch (mGravity) {
            case Gravity.CENTER:        // 显示内容居中。
                if (isOptions || label == null || label.equals("") || !isCenterLabel) {
                    drawCenterContentStart = (int) ((measuredWidth - rect.width()) * 0.5);
                } else {
                    drawCenterContentStart = (int) ((measuredWidth - rect.width()) * 0.25);
                }
                break;
            case Gravity.LEFT:
                drawCenterContentStart = 0;
                break;
            case Gravity.RIGHT:         // 添加偏移量
                drawCenterContentStart = measuredWidth - rect.width() - (int)CENTERCONTENTOFFSET;
                break;
        }
    }

    /**
     *  根据文字的长度 重新设置文字的大小，让其能完全显示
     */
    private void reMeasureTextSize(String contentText) {
        Rect rect = new Rect();
        paintCenterText.getTextBounds(contentText,0,contentText.length(),rect);
        int width = rect.width();
        int size = textSize;
        while(width > measuredWidth) {
            size--;
            // 设置2条横线中间的文字大小
            paintCenterText.setTextSize(size);
            paintCenterText.getTextBounds(contentText,0,contentText.length(),rect);
            width = rect.width();
        }
        // 设置2条横线外面的文字大小
        paintOuterText.setTextSize(size);
    }

    // 计算文字宽度
    public int getTextWidth(Paint paint,String str) {
        int iRet = 0;
        if (str != null && str.length() > 0) {
            int len = str.length();
            float[] widths = new float[len];
            paint.getTextWidths(str,widths);
            for (int j = 0;j < len;j++) {
                iRet += (int) Math.ceil(widths[j]);
            }
        }
        return iRet;
    }

    // 递归算出对应的index
    private int getLoopMappingIndex(int index) {
        if (index < 0) {
            index = index + adapter.getItemsCount();
            index = getLoopMappingIndex(index);
        } else if (index > adapter.getItemsCount() - 1) {
            index = index - adapter.getItemsCount();
            index = getLoopMappingIndex(index);
        }
        return index;

    }

    private void remeasure(){       // 重新测量
        if (adapter == null) {
            return;
        }

        measureTextWidthHeight();

        // 半圆的周长 = item 高度乘以item数目-1
        halfCircumference = (int) (itemHeight * (itemsVisible - 1));
        // 整个圆的周长除以PI得到直径，这个直径用作控件的总高度
        measuredHeight = (int) ((halfCircumference * 2) / Math.PI);
        // 求出半径
        radius = (int)(halfCircumference / Math.PI);
        // 控件宽度，这里支持weight
        measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
        // 计算两条横线 和 选中项画笔的基线Y位置
        firstLineY = (measuredHeight - itemHeight) / 2.0f;
        secondLineY = (measuredHeight + itemHeight) / 2.0f;
        centerY = secondLineY - (itemHeight - maxTextHeight)/2.0f - CENTERCONTENTOFFSET;

        // 初始化显示的item的position
        if (initPosition == -1) {
            if (isLoop) {
                initPosition = (adapter.getItemsCount() + 1) / 2;
            } else {
                initPosition = 0;
            }
        }
        preCurrentIndex = initPosition;
    }

    /**
     *  计算最大length 的Text的宽高度
     */
    private void measureTextWidthHeight(){
        Rect rect = new Rect();
        for (int i = 0;i < adapter.getItemsCount();i++) {
            String s1 = getContentText(adapter.getItem(i));
            paintCenterText.getTextBounds(s1,0,s1.length(),rect);   // 获取所绘字符串的范围。

            int textWidth = rect.width();

            if (textWidth > maxTextWidth) {
                maxTextWidth = textWidth;
            }

            paintCenterText.getTextBounds("\u661F\u671F",0,2,rect); //星期

            maxTextHeight = rect.height() + 2;
        }
        itemHeight = lineSpacingMultiplier * maxTextHeight;
    }

    /**
     *  根据传进来的对象获取getPickerViewText()方法，来获取需要显示的值
     * @param item item数据源的item
     * @return 对应显示的字符串。
     */
    private String getContentText(Object item){
        if (item == null) {
            return "";
        } else if (item instanceof IPickerViewData) {
            return ((IPickerViewData) item).getPickerViewText();
        } else if (item instanceof Integer) {
            // 如果为整型则最少保留两位数.
            return String.format(Locale.getDefault(),"%02d",(int) item);
        }

        return item.toString();
    }

    public final void setAdapter(WheelAdapter adapter) {
        this.adapter = adapter;
        remeasure();
        invalidate();
    }

    public final WheelAdapter getAdapter() {
        return adapter;
    }

    public final int getCurrentItem(){
        return selectedItem;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean eventConsumed = gestureDetector.onTouchEvent(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:   // 按下
                startTime = System.currentTimeMillis();
                cancelFuture();
                previousY = event.getRawY();        // 获取屏幕上的坐标
                break;
            case MotionEvent.ACTION_MOVE:   // 滑动中
                float dy = previousY - event.getRawX();
                previousY = event.getRawY();
                totalScrollY = totalScrollY + dy;

                // 边界处理
                if(!isLoop) {
                    float top = -initPosition *itemHeight;
                    float bottom = (adapter.getItemsCount() - 1 - initPosition) * itemHeight;

                    if (totalScrollY - itemHeight * 0.25 > bottom) {
                        bottom = totalScrollY - dy;
                    } else if (totalScrollY + itemHeight * 0.25 > bottom) {
                        bottom = totalScrollY - dy;
                    }

                    if (totalScrollY < top) {
                        totalScrollY = (int) top;
                    } else if (totalScrollY > bottom) {
                        totalScrollY = (int) bottom;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            default:
                if (!eventConsumed) {   // 未消费掉事件
                    /**
                     *  <关于弧长的计算>
                     *  弧长公式：L = α*R
                     *  反余弦公式：arccos（cosα) = α
                     *  由于之前是有顺时针便宜90度，
                     *  所以实际弧度范围α2的值：α2 = π/2 - α  (α = [0,π] α2 = [-π、2，π/2])
                     *  根据正弦余弦转换公式：cosα = sin(π/2 - α)
                     *  代入，得：cosα = sin(π/2 - α) = sinα2 = (R - y) /R
                     *  所以弧长 L = arccos(cosα)*R = arccos((R -y)/R) * R
                     */
                    float y = event.getY();
                    double L = Math.acos((radius - y) / radius) * radius;
                    // item0 有一半是在不可见区域，所以需要加上 itemHeight / 2
                    int circlePosition = (int)((L + itemHeight/2) / itemHeight);
                    float extraOffset = (totalScrollY % itemHeight + itemHeight / 2) % itemHeight;
                    // 已滑动的弧长值
                    mOffset = (int) ((circlePosition - itemsVisible / 2) * itemHeight - extraOffset);

                    if ((System.currentTimeMillis() - startTime) > 120) {
                        // 处理拖拽事件
                        smoothScroll(ACTION.DAGGLE);
                    } else {
                        // 处理条目点击事件
                        smoothScroll(ACTION.CLICK);
                    }

                }
                break;
        }

        return true;
    }

    void smoothScroll(ACTION action) {     // 平滑滑动的实现
        cancelFuture();
        if (action == ACTION.FLING || action == ACTION.DAGGLE) {
            mOffset = (int) ((totalScrollY % itemHeight + itemHeight) % itemHeight);
            if ((float) mOffset > itemHeight / 2.0f) {      // 如果超过Item高度的一半，滚动到下一个Item去
                mOffset = (int) (itemHeight - (float) mOffset);
            } else {
                mOffset = -mOffset;
            }
        }
        // 停止的时候，位置有偏移，不是全部都能正确停止到中间位置，这里吧文字位置挪回中间去
        // TODO: 2017-11-12
        mFuture = mExecutor.scheduleWithFixedDelay(new SmoothScrollerTimerTask(this,mOffset),0,10, TimeUnit.MILLISECONDS);
    }

    public void cancelFuture(){
        if (mFuture != null && !mFuture.isCancelled()) {
            mFuture.cancel(true);
            mFuture = null;
        }
    }

    /**
     *  获取Item个数
     *  @return item个数
     */
    public int getItemsCount(){
        return adapter != null ? adapter.getItemsCount():0;
    }

    /**
     *  滚动惯性的实现
     */
    protected  final void scrollBy(float velocityY) {
        cancelFuture();
        //mFuture = mExecutor.scheduleWithFixedDelay()
    }
}

















