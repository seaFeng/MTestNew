package com.dahai.mtest.fragment;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dahai.mtest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class PropertyFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "PropertyFragment";

    Unbinder unbinder;

    @BindView(R.id.tv_ainmation1)
    TextView tv_animation1;
    @BindView(R.id.tv_ainmation2)
    TextView tv_ainmation2;
    @BindView(R.id.tv_ainmation3)
    TextView tv_ainmation3;
    @BindView(R.id.iv_anim)
    ImageView iv_ainm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_property, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        tv_animation1.setOnClickListener(this);
        tv_ainmation2.setOnClickListener(this);
        tv_ainmation3.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_ainmation1:
                ObjectAnimatorTest1();
                break;
            case R.id.tv_ainmation2:
                Toast.makeText(getContext(), "1111", Toast.LENGTH_SHORT).show();
                valueAnimatorTest2();
                break;
            case R.id.tv_ainmation3:        // 自由落体
                Toast.makeText(getContext(), "22222", Toast.LENGTH_SHORT).show();
                valueAnimatorTest3();
                break;
        }
    }

    /**
     *  自由落体
     */
    private void valueAnimatorTest3() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0,500);
        valueAnimator.setDuration(2000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                iv_ainm.setTranslationX((float)animation.getAnimatedValue());
                long time = animation.getCurrentPlayTime();
                float g = 0.000125f;//500/2000/2000;
                Log.e(TAG,"g = " + g);
                int y = (int) ((g*time*time)/2);
                Log.e(TAG,"time = " + time);
                Log.e(TAG,"y = " + y);
                iv_ainm.setTranslationY(y);
            }
        });
        valueAnimator.start();
    }

    private void valueAnimatorTest2() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0,200);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) iv_ainm.getLayoutParams();
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animation.getAnimatedFraction();
                Log.e(TAG,"fraction = " + fraction);
                float value = (float) animation.getAnimatedValue();
                Log.e(TAG,"value = " + value);
                Log.e(TAG,"fraction * value = " + fraction*value);

                layoutParams.width = (int) value;
                layoutParams.topMargin = (int) value;
                iv_ainm.setPadding(0, (int) value,0,0);

                iv_ainm.setLayoutParams(layoutParams);
            }
        });

        valueAnimator.setDuration(2000).start();
    }

    private void ObjectAnimatorTest1() {

        ObjectAnimator objectAnimator = null;
        // 绕x轴旋转
        //objectAnimator = ObjectAnimator.ofFloat(iv_ainm, "rotation", 0F, 180F);

        // 绕y轴旋转
        //objectAnimator = ObjectAnimator.ofFloat(iv_ainm,"rotationY",0f,90f);

        // 沿着x轴位移
        objectAnimator = ObjectAnimator.ofFloat(iv_ainm,"translationX",200f);

        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(iv_ainm,"translationY",800f);
        objectAnimator1.setInterpolator(new DecelerateInterpolator());
        AnimatorSet set = new AnimatorSet();
        set.play(objectAnimator).with(objectAnimator1);
        set.play(objectAnimator).after(objectAnimator1);
        set.play(objectAnimator).before(objectAnimator1);
        set.setDuration(2000);
        set.start();

        //缩放
        //objectAnimator = ObjectAnimator.ofFloat(iv_ainm,"scaleX",1f,2f);

        // alpha
        /*objectAnimator = ObjectAnimator.ofFloat(iv_ainm,"alpha",1f,0.1f);

        objectAnimator.setDuration(2000);
        objectAnimator.start();*/
    }
}
