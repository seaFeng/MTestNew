package com.dahai.mtest.fragment;


import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import com.dahai.mtest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewAnimatorFragment extends Fragment implements View.OnClickListener{
    private static final String TAG = "ViewAnimatorFragment";
    Unbinder unbinder;

    @BindView(R.id.btn_animator1)
    Button btn_anima;
    @BindView(R.id.btn_drawableAnim)
    Button btn_drawableAnim;
    @BindView(R.id.iv_anim)
    ImageView iv_anim;
    // view动画。
    @BindView(R.id.btn_alphaAnim)
    Button btn_alphaAnim;
    @BindView(R.id.btn_translateAnim)
    Button btn_translateAnim;
    @BindView(R.id.btn_viewScale)
    Button btn_scale;
    @BindView(R.id.btn_viewRotate)
    Button btn_rotate;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_animator, container, false);
        unbinder = ButterKnife.bind(this, view);
        setListener();
        return view;

    }

    private void setListener() {
        btn_anima.setOnClickListener(this);
        btn_drawableAnim.setOnClickListener(this);
        btn_alphaAnim.setOnClickListener(this);
        btn_translateAnim.setOnClickListener(this);
        btn_scale.setOnClickListener(this);
        btn_rotate.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_animator1:
                animation1();
                break;
            case R.id.btn_drawableAnim:
                drawableAnim();
                break;
            case R.id.btn_alphaAnim:
                alphaViewAnim();
                break;
            case R.id.btn_translateAnim:
                //translateXMLAnim();
                translateCodeAnim();
                break;
            case R.id.btn_viewScale:
                //scaleCodeAnim();
                scaleXmlAnim();
                break;
            case R.id.btn_viewRotate:
                //rotateXmlAnim();
                rotateCodeAnim();

                break;
        }
    }

    // code 设置旋转动画
    private void rotateCodeAnim(){
        RotateAnimation animation = new RotateAnimation(0,90,0,0);
        animation.setFillAfter(true);
        animation.setDuration(2000);
        iv_anim.startAnimation(animation);
    }

    // xml 设置旋转动画
    private void rotateXmlAnim(){
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_anim);
        animation.setFillAfter(true);
        iv_anim.startAnimation(animation);
    }

    // xml设置缩放动画
    private void scaleXmlAnim(){
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.scale_anim);
        animation.setFillAfter(true);
        animation.setDuration(6000);
        iv_anim.startAnimation(animation);
    }

    // code设置缩放动画
    private void scaleCodeAnim(){
        //ScaleAnimation animation = new ScaleAnimation(1,(float) 0.5,1,(float) 0.5);

        //pivotX（浮点型）  pivotY（浮点型）表示控件围绕哪个点进行缩放，默认是围绕坐标原点。(0,0)
        //这两个值又有三种类型
        //ScaleAnimation animation = new ScaleAnimation(1,(float) 0.5,1,(float) 0.5,(float) 800,(float) 800);

        //ABSOLUTE:绝对值
        //RELATIVE_TO_SELF:相对于自己
        //RELATIVE_TO_PARENT:相对于父控件
        ScaleAnimation animation = new ScaleAnimation(1,(float) 0.2,1,(float) 0.2,
                Animation.RELATIVE_TO_SELF,(float) 0.5,Animation.RELATIVE_TO_SELF,1);

        animation.setDuration(1000);
        animation.setFillAfter(true);
        iv_anim.startAnimation(animation);
    }

    // 代码设置唯一动画
    private void translateCodeAnim(){
        AnimationSet set = new AnimationSet(false);
        TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0,
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF,0,
                Animation.RELATIVE_TO_SELF,1);
        set.addAnimation(animation);
        set.setDuration(1000);
        set.setFillAfter(true);
        iv_anim.startAnimation(set);
    }

    // xml 实现 translate
    private void translateXMLAnim() {
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.translate_anim);
        animation.setFillAfter(true);
        iv_anim.startAnimation(animation);
    }

    private void alphaViewAnim() {
        Animation animation; //= AnimationUtils.loadAnimation(getContext(), R.anim.alpha_anim);
        animation = new AlphaAnimation(1,0);
        animation.setDuration(1000);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        animation.setFillAfter(true);
        iv_anim.startAnimation(animation);
    }

    private void drawableAnim() {
        iv_anim.setImageResource(R.drawable.drawable_anim);
        AnimationDrawable drawable = (AnimationDrawable) iv_anim.getDrawable();
        drawable.start();
    }

    private void animation1() {
        Point startPoint = new Point(0,0);
        Point endPoint = new Point(300,300);

        ValueAnimator valueAnimator = ValueAnimator.ofObject(new PointSinEvaluator(),startPoint,endPoint);
        valueAnimator.setEvaluator(new PointSinEvaluator());

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Point point = (Point) animation.getAnimatedValue();
                Log.e(TAG,"point.x = " + point.x +"     point.y = " + point.y);
            }
        });

        valueAnimator.start();
    }

    public class PointSinEvaluator implements TypeEvaluator<Point> {

        @Override
        public Point evaluate(float fraction, Point startValue, Point endValue) {
            float x = startValue.x + fraction * (endValue.x - startValue.x);
            float y = (float) (Math.sin(x * Math.PI / 180) * 100) + endValue.y / 2;
            Point point = new Point((int) x, (int) y);
            return point;
        }
    }

}
