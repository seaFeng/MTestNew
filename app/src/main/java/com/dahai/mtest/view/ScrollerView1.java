package com.dahai.mtest.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Created by 张海洋 on 2017-10-24.
 */

public class ScrollerView1 extends LinearLayout {
    private static final String TAG = "ScrollerView1";

    private Scroller mScroller;

    public ScrollerView1(Context context) {
        super(context);
        init(context);
    }

    public ScrollerView1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ScrollerView1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mScroller = new Scroller(context);
    }

    //调用此方法滚动到目标位置
    public void smoothScrollTo(int fx,int fy) {
        int dx = fx - mScroller.getFinalX();
        int dy = fy - mScroller.getFinalY();
        smoothScrollBy(dx,dy);
    }

    //调用此方法设置滚动的相对偏移
    public void smoothScrollBy(int dx,int dy) {
        // 设置mScroller的滚动偏移量
        mScroller.startScroll(mScroller.getFinalX(),mScroller.getFinalY(),dx,dy,1000);
        invalidate();   // 通过这个方法能调用起onDraw()方法。
    }

    @Override
    public void computeScroll() {
        // 先判断mScroller滚动是否完成
        if (mScroller.computeScrollOffset()) {
            //这里调用View的ScrollTo（）完成实际的滚动。
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());

            // 必须调用改方法，否则不一定看到滚动效果。
            postInvalidate();
        } else {

        }

        super.computeScroll();
    }
}
