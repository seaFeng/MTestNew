package com.dahai.mtest;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dahai.mtest.fragment.BitmapFragment;
import com.dahai.mtest.fragment.CanvasFragment;
import com.dahai.mtest.fragment.DialogTestFragment;
import com.dahai.mtest.fragment.ProgressBarFragment;
import com.dahai.mtest.fragment.PropertyFragment;
import com.dahai.mtest.fragment.RecyclerViewFragment;
import com.dahai.mtest.fragment.View1Fragment;
import com.dahai.mtest.fragment.ViewAnimatorFragment;
import com.dahai.mtest.sql.SqliteFragment;

public class FragmentActivity extends AppCompatActivity {
    private static final String TAG = "FragmentActivity";
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        fm = getSupportFragmentManager();
        initView();
    }

    private void initView() {
        fm.beginTransaction().add(R.id.fragment_content,new RecyclerViewFragment()).commit();
    }
}
