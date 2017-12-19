package com.dahai.mtest.view.recyclerView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.dahai.mtest.R;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_SETTLING;

/**
 * Created by 张海洋 on 2017-12-17.
 */

public class RecyclerViewLoad extends FrameLayout {
    RecyclerView recyclerView;
    ProgressBar view_refresh;
    // refreshView 的高度：
    int refreshViewMarginTop = -(int)(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,50,getResources().getDisplayMetrics()));;

    boolean outScroll = false;

    public RecyclerViewLoad(Context context) {
        super(context);
        init(context);
    }

    public RecyclerViewLoad(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RecyclerViewLoad(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View content = inflater.inflate(R.layout.recycler_load, this, false);
        recyclerView = content.findViewById(R.id.recyclerView);
        view_refresh = content.findViewById(R.id.view_refresh);
        ViewGroup.LayoutParams layoutParams = view_refresh.getLayoutParams();
        layoutParams.height = refreshViewMarginTop;
        addView(content);
        refresh();

    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        recyclerView.setLayoutManager(layoutManager);
    }

    public void refresh(){
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState) {
                    case SCROLL_STATE_IDLE:                 // 静止
                        //Log.e(TAG,"第一个状态");
                        break;
                    case SCROLL_STATE_DRAGGING:             // 拖动
                        //Log.e(TAG,"第二个状态");
                        break;
                    case SCROLL_STATE_SETTLING:             // 自滑动停止
                        //Log.e(TAG,"第三个状态");
                        break;
                }
            }



            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                RecyclerView.Adapter adapter = recyclerView.getAdapter();

                int firstCompletelyVisibleItemPosition = layoutManager.findFirstCompletelyVisibleItemPosition();
                //Log.e(TAG,"第一个完全可见item的位置 == " + firstCompletelyVisibleItemPosition);
                if (firstCompletelyVisibleItemPosition == 0) {
                    outScroll = true;


                } else {
                    outScroll = false;
                }

            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return outScroll;
    }

    int value;

    float startY = 0 ;// 内部定义开始位置的坐标

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO 设置滑动事件

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = event.getRawY();
                System.out.println("按下了。。。。" + startY);
                break;
            case MotionEvent.ACTION_MOVE:
                float endY = event.getRawY();
                float diff = endY - startY;

                if (diff > 0) {
                    ViewGroup.MarginLayoutParams layoutParams = (MarginLayoutParams) view_refresh.getLayoutParams();
                    Log.e("scroll","diff = " + diff);

                    if (diff > 0) {       // 向上平移
                        refreshViewMarginTop += diff;
                    } else {            // 向下位移
                        refreshViewMarginTop += diff;
                    }

                    Log.e("scroll","滑动的高度 == " + layoutParams.height);
                    layoutParams.topMargin = refreshViewMarginTop/2;
                    view_refresh.setLayoutParams(layoutParams);
                }

                startY = endY;
                System.out.println("新获取到的值" + startY);
                break;
            case MotionEvent.ACTION_UP:
                ViewGroup.MarginLayoutParams layoutParams = (MarginLayoutParams) view_refresh.getLayoutParams();
                layoutParams.topMargin = 0;
                view_refresh.setLayoutParams(layoutParams);
                outScroll = false;
                break;

        }
        return true;
    }
}
