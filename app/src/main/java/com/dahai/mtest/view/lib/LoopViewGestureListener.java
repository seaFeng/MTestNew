package com.dahai.mtest.view.lib;

import android.util.Log;
import android.view.MotionEvent;

final class LoopViewGestureListener extends android.view.GestureDetector.SimpleOnGestureListener {

    final WheelView loopView;

    LoopViewGestureListener(WheelView loopview) {
        loopView = loopview;
    }

    @Override
    public final boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.e("ontouchEvent","fling/fling");
        loopView.scrollBy(velocityY);
        return true;
    }
}
