package com.dahai.mtest.view.progressBar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ProgressBar;

/**
 * Created by 张海洋 on 2017-11-22.
 */

public class progressView1 extends ProgressBar {
    private static final String TAG = "progressView1";

    private Context context;
    private Paint mPaint;

    /**
     *  进度的实际宽度
     */
    private int mRealWidth;

    /**
     * 设置默认的绘制进度条颜色
     */
    protected int mBarColor = 0xffffcc00;

    /**
     * 未加载进度条的颜色
     */
    protected int mUnBarColor = 0xffe6e6e6;

    /**
     *  文字的颜色
     */
    private int mTextColor = 0xFFFF0000;

    public progressView1(Context context) {
        this(context,null);
    }

    public progressView1(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public progressView1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        initView();
    }

    private void initView(){
        mPaint = new Paint();
        mPaint.setTextSize(20);
        mPaint.setColor(0xaaff0000);
        setBackgroundColor(Color.GRAY);
        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,20,getResources().getDisplayMetrics());
        Log.e(TAG,"控件的高 == " + height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mRealWidth = w - getPaddingLeft() - getPaddingRight();
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        //super.onDraw(canvas);

        canvas.save();

        Log.e(TAG,"控件的高 == " + getHeight());

        /**
         *  定义画笔的初始位置
         */
        canvas.translate(getPaddingLeft(),getHeight());

        /**
         *  计算加载进度的比例
         */
        float radio = getProgress() * 1.0f / getMax();

        /**
         *  计算已加载的进度
         */
        float progressPosX = (int) (mRealWidth * radio);

        /**
         *  定义进度上显示的文字信息
         */
        String text = getProgress() + "%";

        /**
         *  获取绘制文字的宽与高
         */
        float textWidth = mPaint.measureText(text);
        float textHeight = (mPaint.descent() + mPaint.ascent()) / 2;

        /**
         *  判断绘制
         */
        if (progressPosX + textWidth > mRealWidth) {
            progressPosX = mRealWidth - textWidth;
        }

        /**
         *  绘制已加载的进度
         */
        mPaint.setColor(mBarColor);
        mPaint.setStrokeWidth(50);
        canvas.drawLine(0,0,progressPosX,0,mPaint);
        /**
         *  绘制加载显示的文字
         */
        mPaint.setColor(mTextColor);
        canvas.drawText(text,progressPosX,textHeight,mPaint);

        /**
         *  绘制未加载的进度
         */
        float start = progressPosX + textWidth;
        mPaint.setColor(mUnBarColor);
        mPaint.setStrokeWidth(50);
        canvas.drawLine(start,0,mRealWidth,0,mPaint);

        canvas.restore();
    }
}









