package com.dahai.mtest.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.dahai.mtest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 张海洋 on 2017-11-22.
 */

public class ProgressBarFragment extends Fragment {


    @BindView(R.id.iv_progress1)
    ImageView iv_progress1;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    Unbinder unbinder;
    View contentView;

    public static Handler handler = new Handler();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.fragment_progressbar, container, false);
        unbinder = ButterKnife.bind(this, contentView);
        initView();
        return contentView;
    }

    private void initView() {
        iv_progress1.setImageLevel(100000);
        //iv_progress1.getDrawable().setLevel(5000);
        handler.post(runnable);

        progressBar.setMax(1000);
        handler.postDelayed(new Runnable() {
            int progress = 10;

            @Override
            public void run() {
                progress += 10;

                progressBar.setProgress(progress);

                handler.postDelayed(this,100);
            }
        },100);
    }

    Runnable runnable = new Runnable() {
        int number = 100;

        @Override
        public void run() {
            number += 100;
            iv_progress1.getDrawable().setLevel(number);
            handler.postDelayed(this,100);
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        handler.removeCallbacksAndMessages(null);
    }
}
