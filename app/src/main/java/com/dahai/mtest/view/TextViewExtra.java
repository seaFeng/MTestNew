package com.dahai.mtest.view;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by 张海洋 on 2017-11-17.
 */

@SuppressLint("AppCompatCustomView")
public class TextViewExtra extends TextView {

    public TextViewExtra(Context context) {
        this(context,null);
    }

    public TextViewExtra(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TextViewExtra(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


}
