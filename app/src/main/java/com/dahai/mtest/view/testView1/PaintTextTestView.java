package com.dahai.mtest.view.testView1;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.dahai.mtest.R;

/**
 * Created by 张海洋 on 2017-11-14.
 */

public class PaintTextTestView extends View {
    private Context context;
    private TextPaint paint;
    private String str;
    private int textHeight;
    private int lineSpacing;

    public PaintTextTestView(Context context) {
        super(context);
        init(context,null);
    }

    public PaintTextTestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    private void init(Context context,AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.PaintTextTestView,0,0);
        str = typedArray.getString(R.styleable.PaintTextTestView_paintText);
        Log.e("text",str);
        typedArray.recycle();

        this.context = context;
        paint = new TextPaint();
        paint.setAntiAlias(true);
        paint.setTextSize(50);
        paint.setColor(0xff000000);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Rect rect = new Rect();
        paint.getTextBounds(str,0,str.length(),rect);
        textHeight = rect.height();
        setMeasuredDimension(rect.width(),rect.height()+10);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawText(str,0,textHeight - paint.descent() + 5,paint);
    }
}
