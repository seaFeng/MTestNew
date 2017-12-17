package com.dahai.mtest.view.drawView;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.dahai.mtest.R;

/**
 * Created by 张海洋 on 2017-10-25.
 */

public class DrawView2 extends View {
    private static final String TAG = "DrawView2";

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public DrawView2(Context context) {
        super(context);
        initView();
    }

    public DrawView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(0xffff0000);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //这个颜色图层是：src ；下一个图层是：des ;
        canvas.drawColor(0xff009900, PorterDuff.Mode.DST_OVER);
        //canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round),120,120,paint);
        // 画矩形
        paint.setStrokeWidth(10);
        canvas.drawRect(10,10,100,100,paint);

        // 画点
        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawPoint(55,55,paint);

        // 批量画点：
        float[] points = new float[] {120,55,240,55,360,55,480,55,520,55};
        //canvas.drawPoints(points,2,7,paint);
        canvas.drawPoints(points,paint);

        // 椭圆：
        RectF rectF = new RectF(10,130,100,230);
        canvas.drawOval(rectF,paint);

        // 划线：
        canvas.drawLine(10,10,100,100,paint);

        //批量划线：(田)
        float[] points1 = new float[] {10,250,110,250,0,350,120,350};
        canvas.drawLines(points1,paint);

        //画圆角矩形：
        RectF rectF2 = new RectF(150,130,250,230);
        canvas.drawRoundRect(rectF2,50,20,paint);

        // 画圆弧：
        /*paint.setStyle(Paint.Style.FILL);
        RectF rectF3 = new RectF(300,140,400,230);
        canvas.drawArc(rectF3,20,140,true,paint);*/

        // 路径：
        Path path = new Path();
        path.addArc(new RectF(200, 200, 400, 400), -225, 225);
        path.arcTo(new RectF(400, 200, 600, 400), -180, 225, false);
        path.lineTo(400, 542);
        path.close();
        canvas.drawPath(path,paint);
    }
}
