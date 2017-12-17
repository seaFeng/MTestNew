package com.dahai.mtest.view;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.FrameLayout;

import com.dahai.mtest.R;

import java.util.Calendar;

/**
 * Created by 张海洋 on 2017-10-23.
 */

public class TimeView1 extends FrameLayout implements View.OnClickListener {
    private static final String TAG = "TimeManager";

    private LayoutInflater inflater;
    private View timeView;
    private Button btn_year;
    private Button btn_month;
    private Button btn_day;

    private CalendarView calendarView;

    private Calendar calendar = Calendar.getInstance();
    private int year;
    private int month;
    private int day;
    //当月的日期数。
    private int monthDays;

    public TimeView1(Context context) {
        super(context);
        init(context);
    }

    public TimeView1(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TimeView1(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        initData();
        inflater = LayoutInflater.from(context);
        timeView = inflater.inflate(R.layout.layout_time, this, false);

        btn_year = timeView.findViewById(R.id.btn_year);
        btn_month = timeView.findViewById(R.id.btn_month);
        btn_day = timeView.findViewById(R.id.btn_day);


        btn_year.setText("1992");
        btn_month.setText("10");
        btn_day.setText("6");

        btn_year.setOnClickListener(this);
        btn_month.setOnClickListener(this);
        btn_day.setOnClickListener(this);

        addView(timeView, new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    /**
     * 初始化日期。
     */
    private void initData() {
        calendar.set(1992, 9, 6);      // 月份是从0 -- 11;
        if (year == 0) {
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DATE);
        }

        Log.e(TAG, "用户的每月的几天DATE：" + calendar.get(Calendar.DATE));
        Log.e(TAG, "用户的每月的几天DAY_OF_MONTH：" + calendar.get(Calendar.DAY_OF_MONTH));

        //getMaxDayMonth();
    }

    /**
     * 获取当月的最大天数。
     */
    //返回当月天数
    int getDays(int year, int month) {
        int days;
        int FebDay = 28;
        if (isLeap(year))
            FebDay = 29;
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                days = 31;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                days = 30;
                break;
            case 2:
                days = FebDay;
                break;
            default:
                days = 0;
                break;
        }
        return days;
    }

    //判断闰年
    boolean isLeap(int year)
    {
        if (((year % 100 == 0) && year % 400 == 0) || ((year % 100 != 0) && year % 4 == 0))
            return true;
        else
            return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_year:
                setYear();
                break;
            case R.id.btn_month:
                setMonth();
                break;
            case R.id.btn_day:
                setDay();
                break;
        }
    }

    private void setDay() {
        Log.e(TAG, "当前日期：" + getCurrentDay());
        Log.e(TAG, "当月的天数：" + getDays(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH) + 1));
        if (getCurrentDay() >= getDays(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH) + 1)) {
            /*btn_month.setText((calendar.get(Calendar.MONTH) + 1) + "");
            calendar.add(Calendar.MONTH, 1);*/
            setMonth();
            calendar.set(Calendar.DATE, 1);
            btn_day.setText("1");
            return;
        }
        calendar.add(Calendar.DATE, 1);
        btn_day.setText(calendar.get(Calendar.DATE) + "");
    }

    private int getCurrentDay() {
        return calendar.get(Calendar.DATE);
    }

    private void setMonth() {
        if (getCurrentMonth() == 11) {
            btn_month.setText("1");
            calendar.set(Calendar.MONTH, 1);
            setYear();
            return;
        }
        calendar.add(Calendar.MONTH, 1);
        //get();
        btn_month.setText((calendar.get(Calendar.MONTH) + 1) + "");
    }

    private void setYear() {
        calendar.add(Calendar.YEAR, 1);
        //getMaxDayMonth();
        btn_year.setText(calendar.get(Calendar.YEAR) + "");
    }

    /**
     * 获取当前的月数。
     *
     * @return
     */
    private int getCurrentMonth() {
        return calendar.get(Calendar.MONTH);
    }

}
