package com.dahai.mtest.view.event;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.EventLog;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by 张海洋 on 2017-11-14.
 */

public class InnerView extends View {
    public InnerView(Context context) {
        this(context,null);
    }

    public InnerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public InnerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {

    }
    // 初始变量的位置必须设置为成员变量。
    float startY = 0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = event.getRawY();
                Log.e("innerView","起始点：" + startY);
                break;
            case MotionEvent.ACTION_MOVE:
                float endY = event.getRawY();
                Log.e("innerView","起始点：" + startY);
                Log.e("innerView","滑动点 ：" + endY);

                float diff = endY - startY;
                Log.e("innerView","" + diff);
                layout(getLeft(), (int) (getTop() + diff),getRight(), (int) (getBottom() + diff));
                // 这个很有必要。
                startY = endY;
                //if (diff == 100) {
                return false;
                //}
                //setTranslationY(diff);
                //break;

            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }
}
