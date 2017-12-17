package com.dahai.mtest.view.wheelView;

import java.util.TimerTask;

/**
 * Created by 张海洋 on 2017-11-13.
 */

public class InertiaTimerTask extends TimerTask {
    float a;
    final float velocityY;
    final WheelView loopView;

    InertiaTimerTask(WheelView loopView, float velocityY) {
        this.velocityY = velocityY;
        this.loopView = loopView;
        a = Integer.MAX_VALUE;
    }

    @Override
    public void run() {
        if (a == Integer.MAX_VALUE) {
            if (Math.abs(velocityY) > 2000f) {
                if (velocityY > 0.0f) {
                    a = 2000f;
                } else {
                    a = -2000f;
                }
            } else {
                a = velocityY;
            }
        }
        if (Math.abs(a) >= 0.0f && Math.abs(a) <= 20f) {
            loopView.cancelFuture();
            loopView.handler.sendEmptyMessage(MessageHandler.WHAT_SMOOTH_SCROLL);
            return;
        }
        int i = (int) ((a * 10f) / 1000f);
        loopView.totalScrollY = loopView.totalScrollY - i;
        if (!loopView.isLoop) {
            float itemHeight = loopView.itemHeight;
            float top = (-loopView.initPosition) * itemHeight;
            float bottom = (loopView.getItemsCount() - 1 - loopView.initPosition) * itemHeight;
            if (loopView.totalScrollY - itemHeight * 0.25 < top) {
                top = loopView.totalScrollY + i;
            } else if (loopView.totalScrollY + itemHeight * 0.25 > bottom) {
                bottom = loopView.totalScrollY + i;
            }

            if (loopView.totalScrollY <= top) {
                a = 40F;
                loopView.totalScrollY = (int) top;
            } else if (loopView.totalScrollY >= bottom) {
                loopView.totalScrollY = (int) bottom;
                a = -40F;
            }
        }
        if (a < 0.0F) {
            a = a + 20F;
        } else {
            a = a - 20F;
        }
        loopView.handler.sendEmptyMessage(MessageHandler.WHAT_INVALIDATE_LOOP_VIEW);
    }
}
