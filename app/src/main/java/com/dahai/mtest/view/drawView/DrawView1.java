package com.dahai.mtest.view.drawView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by 张海洋 on 2017-10-25.
 */

public class DrawView1 extends View {
    private static final String TAG = "DrawView1";
    Paint paint = new Paint();

    private int radius = 300;             // 圆的半径。默认 300px

    public DrawView1(Context context) {
        super(context);
        init(context);
    }

    public DrawView1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(0xffff0000);
        setBackgroundColor(0xff333333);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {             //当我们设置为match_parent或者为一个具体值。
            Log.e(TAG,"测量模式为EXACTLY");                  // match_parent为父布局的大小。
            radius = widthSize/2;
        } else if (widthMode == MeasureSpec.AT_MOST) {     // 当我们设置为wrap_content
            Log.e(TAG,"测量模式为AT_MOST");
        } else if (widthMode == MeasureSpec.UNSPECIFIED) {
            Log.e(TAG,"测量模式为UNSPECIFIED");
        }

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (heightMode == MeasureSpec.EXACTLY) {             //当我们设置为match_parent或者为一个具体值。
            Log.e(TAG,"测量模式为EXACTLY");                  // match_parent为父布局的大小。
            //radius = widthSize/2;
        } else if (heightMode == MeasureSpec.AT_MOST) {     // 当我们设置为wrap_content
            Log.e(TAG,"测量模式为AT_MOST");
        }

        setMeasuredDimension(widthSize,2*radius);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 绘制一个圆：
        canvas.drawCircle(radius,radius,radius,paint);
    }
}
