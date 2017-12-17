package com.dahai.mtest.test;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dahai.mtest.R;
import com.dahai.mtest.view.ScrollerView1;

/**
 * A simple {@link Fragment} subclass.
 */
public class Test3Fragment extends Fragment implements View.OnClickListener{
    private static final String TAG = "Test3Fragment";

    private View rootView;
    private ScrollerView1 scrollerView1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_test3, container, false);
        initView();
        return rootView;
    }

    private void initView() {
        scrollerView1 = rootView.findViewById(R.id.test3_test);
        scrollerView1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        scrollerView1.smoothScrollBy(-200,-200);
    }
}
