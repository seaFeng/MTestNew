package com.dahai.mtest.view.wheelView;

import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by 张海洋 on 2017-11-12.
 */

public class LoopViewGestureListener extends GestureDetector.SimpleOnGestureListener {
    final WheelView loopView;

    LoopViewGestureListener(WheelView loopView) {
        this.loopView = loopView;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        //loopView.scrollBy(velocityY);
        return true;
    }
}
