package com.dahai.mtest.test;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dahai.mtest.R;
import com.dahai.mtest.view.WheelViewTest;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Test2Fragment extends Fragment {
    private View rootView;
    private WheelViewTest wheelViewTest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_test2, container, false);
        findView();
        return rootView;
    }

    private void findView() {
        wheelViewTest = rootView.findViewById(R.id.test2_wheelView);
        wheelViewTest.setStrings(getStrings(),getActivity());
    }

    private ArrayList<String> getStrings() {
        ArrayList<String> list = new ArrayList<>();
        list.add("111111");
        list.add("222222");
        list.add("333333");
        list.add("444444");
        list.add("555555");
        list.add("666666");
        list.add("777777");
        list.add("888888");
        list.add("99999");
        list.add("10101010");
        list.add("111111");
        list.add("222222");
        list.add("333333");
        list.add("444444");
        list.add("555555");
        list.add("666666");
        list.add("777777");
        list.add("888888");
        list.add("99999");
        list.add("10101010");
        return list;
    }

}
