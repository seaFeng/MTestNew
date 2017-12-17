package com.dahai.mtest.view.event;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by 张海洋 on 2017-11-14.
 */

public class OutGroupView extends ViewGroup {
    private Context context;
    private GestureDetector gestureDetector;

    public OutGroupView(Context context) {
        this(context,null);
        this.context = context;
    }

    public OutGroupView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
        this.context = context;
    }

    public OutGroupView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                Log.e("outView","x = onDown");
                return true;
            }

            /*@Override
            public void onShowPress(MotionEvent e) {
                Log.e("outView","onShowPress");
            }
*/
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                Log.e("outView","nSingleTapUp");
                return true;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {         // 后两个是滑动速率：单位是 px/s
                Log.e("outView","onScroll");
                return true;
            }

           /* @Override
            public void onLongPress(MotionEvent e) {
                Log.e("outView","onLongPress");
            }*/

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                Log.e("outView","onFling");
                return true;
            }
        });
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View childAt = getChildAt(0);
        if (childAt instanceof InnerView) {
            Toast.makeText(context, "InnerView", Toast.LENGTH_SHORT).show();
        }
        Log.e("outView","宽：" + childAt.getMeasuredWidth() + "   高 ：" + childAt.getMeasuredHeight());
        childAt.layout(l,t,l + 200,t + 200);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean b = gestureDetector.onTouchEvent(event);
        Log.e("onTouch",b + "");
        return b;

        //Toast.makeText(context, "outView", Toast.LENGTH_SHORT).show();
       /* Log.e("outView","111111111111111111");
        float startY = 0;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float endY = event.getRawY();
                float diff = endY - startY;
                Log.e("outView"," = " + diff);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;*/
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e("outView","2222222222222222222");
        return true;
    }
}
