package com.dahai.mtest.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by 张海洋 on 2017-11-03.
 */

public class TransParentDialog extends Dialog {
    private static final String TAG = "TransParentDialog";
    private int layoutId;
    private Activity activity;

    public TransParentDialog(@NonNull Context context) {
        super(context);
    }

    public TransParentDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected TransParentDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public TransParentDialog(Context context,int themeResId,int layoutId){
        super(context,themeResId);
        this.layoutId = layoutId;
        activity = (Activity) context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contentView = getLayoutInflater().inflate(layoutId, null);
        setContentView(contentView);
        setWidth();
    }

    private void setWidth() {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;

        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.width = screenWidth/4;
        getWindow().setAttributes(layoutParams);
    }
}
