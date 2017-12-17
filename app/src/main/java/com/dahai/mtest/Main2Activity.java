package com.dahai.mtest;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

public class Main2Activity extends AppCompatActivity {
    private static final String TAG = "Main2Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //test1();
        //test2();
        //test3();
        //test4();
        // window操作系统View
        windowAddFlag();
    }

    private void windowAddFlag() {
        Window window = getWindow();
        int option = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION | WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        window.addFlags(option);
    }

    /**
     *  View.setSystemUiVisibility();
     */
    private void test3() {
        ViewGroup decorView = (ViewGroup) getWindow().getDecorView();
        //decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        int position = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(position);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
        }
    }

    /**
     *
     */
    private void test4(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    private void test2() {
        ViewGroup decorView = (ViewGroup) getWindow().getDecorView();
        int childCount = decorView.getChildCount();
        Log.e("TAG","改Activity的View的数量" + childCount);
        ViewGroup contentView1 = (ViewGroup) decorView.getChildAt(0);
        int childCount1 = contentView1.getChildCount();
        Log.e("TAG","改Activity的View的数量" + childCount1);
        View childAt = decorView.getChildAt(1);
        childAt.setBackgroundColor(0xff00ff00);
        View childAt1 = decorView.getChildAt(2);
        childAt1.setBackgroundColor(0xff0000ff);
    }

    private void test1() {
        ViewGroup content = (ViewGroup) findViewById(android.R.id.content);
        int childCount = content.getChildCount();
        Log.e("TAG","改Activity的View的数量" + childCount);
        ViewGroup content1 = (ViewGroup) content.getChildAt(0);
        int childCount1 = content1.getChildCount();
        Log.e(TAG,"可见View的个数" + childCount1);
    }

    public void click(View view) {
        //test2();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.e(TAG,"执行了");
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }
}
