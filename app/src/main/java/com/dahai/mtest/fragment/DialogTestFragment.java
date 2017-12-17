package com.dahai.mtest.fragment;


import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TimePicker;

import com.dahai.mtest.R;
import com.dahai.mtest.view.dialog.TransParentDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class DialogTestFragment extends Fragment implements View.OnClickListener{
    private static final String TAG = "DialogTestFragment";

    Unbinder unbinder;

    @BindView(R.id.btn_dialog1)
    Button btn_transparent;
    @BindView(R.id.btn_datePicker)
    Button btn_datePicker;
    @BindView(R.id.iv_animator)
    ImageView iv_animator;
    @BindView(R.id.btn_animator)
    Button btn_animator;
    @BindView(R.id.btn_screenWidth)
    Button btn_screenWidth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dialog_test, container, false);
                        unbinder = ButterKnife.bind(this, view);
        init();
        setOnclick();

        return view;
    }

    TransParentDialog dialog;
    private void init() {
        dialog = new TransParentDialog(getActivity(),R.style.CustomDialogTheme,R.layout.dialog_transparent);
        Window window = dialog.getWindow();
        //window.setWindowAnimations(R.style.dialog_animation);

        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.horizontalMargin = (float) 0.1;
        layoutParams.verticalMargin = (float) 0.2;
        layoutParams.gravity = Gravity.START | Gravity.TOP;
        window.setAttributes(layoutParams);
    }

    private void setOnclick() {
        btn_datePicker.setOnClickListener(this);
        btn_transparent.setOnClickListener(this);
        btn_animator.setOnClickListener(this);
        btn_screenWidth.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_dialog1:          // 透明Dialog
                if (dialog.isShowing()) {
                    dialog.dismiss();
                } else {
                    dialog.show();
                }

                break;
            case R.id.btn_datePicker:
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Log.e(TAG,hourOfDay + ":" + minute);
                    }
                }, 12, 30, true);
                timePickerDialog.show();
                break;
            case R.id.btn_animator:
                Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.alpha_anim);
                animation.setFillAfter(true);
                iv_animator.startAnimation(animation);
                break;
            case R.id.btn_screenWidth:
                //getScreenWidthFun1();
                getScreenWidthFun2();
                break;
        }
    }

    private void getScreenWidthFun2(){
        WindowManager wm = getActivity().getWindowManager();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        int widthPixels = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        Log.e(TAG,"屏幕的高度" + height);
    }

    /**
     *  获取屏幕宽高，方式1：
     */
    private void getScreenWidthFun1() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        wm = getActivity().getWindowManager();
        Display defaultDisplay = wm.getDefaultDisplay();
        int width = defaultDisplay.getWidth();
        Log.e(TAG,"屏幕宽度：" + width);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
