package com.dahai.mtest.test;


import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.dahai.mtest.R;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 */
public class Test1Fragment extends Fragment {
    private static final String TAG = "Test1Fragment";
    private View rootView;
    private Activity activity;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:

                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_test1,container,false);
        activity = getActivity();
        initView();
        return rootView;
    }

    private void initView() {

    }

    private void powerTest() {
        String packageName = activity.getPackageName();
        Log.e(TAG,"应用包名：" + packageName);
    }

    /**
     *  判断系统版本。
     */
    private void judgeVersion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.e(TAG,"该手机版本大于等于6.0");
        } else {
            Log.e(TAG,"小于6.0");
        }
    }

}
