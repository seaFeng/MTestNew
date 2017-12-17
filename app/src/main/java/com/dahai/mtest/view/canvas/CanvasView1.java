package com.dahai.mtest.view.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 张海洋 on 2017-11-22.
 */

public class CanvasView1 extends View {
    private static final String TAG = "CanvasView1";

    private Context context;
    Paint paint;

    public CanvasView1(Context context) {
        this(context,null);
    }

    public CanvasView1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context,attrs);
    }

    public CanvasView1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context,attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        this.context = context;
        setBackgroundColor(Color.GRAY);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(0xFFff0000);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.rotate(45);
        //canvas.scale(0.5f,0.5f);
        canvas.drawRect(new Rect(0,0,400,400),paint);
        //canvas.translate(100,100);

        canvas.restore();
        canvas.drawArc(new RectF(0,-100,800,200),0,360,false,paint);
    }
}
