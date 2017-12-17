package com.dahai.mtest.view;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by 张海洋 on 2017-10-23.
 */

public class WheelViewTest extends LinearLayout {
    private static final String TAG = "WheelView";

    private ArrayList<String> list;
    private Scroller scroller;

    public WheelViewTest(@NonNull Context context) {
        super(context);
        init(context);
    }

    public WheelViewTest(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public WheelViewTest(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setOrientation(VERTICAL);
        setBackgroundColor(0xff00ffee);
        scroller = new Scroller(context);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()){
            ((View)getParent()).scrollTo(scroller.getCurrX(),scroller.getCurrY());
            invalidate();
        }
    }

    int lastX;
    int lastY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:   // 获取按下的坐标点
                lastX = x;
                lastY = y;
                Log.e(TAG,"按下的坐标：" + lastX + " || " + lastY);
                break;
            case MotionEvent.ACTION_MOVE:
                int offsetX = x - lastX;        // x轴的偏移量。
                int offsetY = y - lastY;        // y轴的偏移量。
                Log.e(TAG,"偏移量的坐标：" + offsetX +" || " + offsetY);
                // 滚动方式1：（移动View中的内容但是不可见的部分还是不可见）
                    //scrollBy(0,-offsetY);
                    //invalidate();
                // 滚动方式2：
                    //offsetTopAndBottom(offsetY);
                // 滚动方式3：
                    //layout(getLeft() + 0,getTop() + offsetY,getRight() + 0,getBottom() + offsetY);
                // 滚动方式4：(移动View中内容)
                    /*ViewGroup.MarginLayoutParams layoutParams = (MarginLayoutParams) getLayoutParams();
                    layoutParams.topMargin = getTop() + offsetY;
                    layoutParams.leftMargin = getLeft() + offsetX;
                    setLayoutParams(layoutParams);*/
                //滚动方式5：
                ((View)getParent()).scrollTo(0,-offsetY);
                break;
            case MotionEvent.ACTION_UP:
                Log.e(TAG,"起来的点：" + x + " | | " + y);
                //滚动方式5：
                View parent = (View) getParent();
                scroller.startScroll(parent.getScrollX(),parent.getScrollY(),-parent.getScrollX(),-parent.getScrollY());
                invalidate();
                break;
        }

        return true;
    }

    public void setStrings(ArrayList<String> list,Context context){
        this.list = list;
        for (String s : list) {
            TextView tv_item = new TextView(context);
            tv_item.setText(s);
            tv_item.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,20,getResources().getDisplayMetrics()));
            LayoutParams layoutParams = new LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            addView(tv_item,layoutParams);
        }
    }
}
