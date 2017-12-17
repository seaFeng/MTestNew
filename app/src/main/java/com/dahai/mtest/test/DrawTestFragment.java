package com.dahai.mtest.test;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dahai.mtest.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DrawTestFragment extends Fragment {
    private static final String TAG = "DrawTestFragment";
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_draw_test,container,false);
        initView();
        return rootView;
    }

    private void initView() {

    }

}
