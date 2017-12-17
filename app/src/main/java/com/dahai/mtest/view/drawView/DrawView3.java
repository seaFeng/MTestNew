package com.dahai.mtest.view.drawView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 张海洋 on 2017-10-26.
 */

public class DrawView3 extends View {
    private static final String TAG = "DrawView3";
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Path path = new Path();

    public DrawView3(Context context) {
        super(context);
        initView();
    }

    public DrawView3(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        path.addArc(new RectF(10,10,110,110),0,90);
        path.arcTo(new RectF(10,10,110,110),90,180,true);       // 是否开始一个新的弧形，
        path.lineTo(500,10);
        //path.rLineTo(200,100);          // x轴和y轴的偏移量。
        //path.quadTo(600,200,500,300);
        path.rQuadTo(100,190,0,290);                // 这些点的偏移量都是相对上一次路径末点的位置的改变量。
        path.cubicTo(600,400,500,500,600,600);


        canvas.drawPath(path,paint);
    }
}
