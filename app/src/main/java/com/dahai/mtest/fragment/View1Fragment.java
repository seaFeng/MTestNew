package com.dahai.mtest.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dahai.mtest.R;
import com.dahai.mtest.view.lib.WheelAdapter;
import com.dahai.mtest.view.lib.WheelView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class View1Fragment extends Fragment {
    private static final String TAG = "View1Fragment";
    Unbinder unbinder;

    @BindView(R.id.wheelView)
    WheelView wheelView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view1, container, false);
        unbinder = ButterKnife.bind(this,view);
        init();
        return view;
    }

    private void init() {
        MyWheelViewAdapter adapter = new MyWheelViewAdapter(getList());
        wheelView.setAdapter(adapter);

    }

    private List<String> getList() {
        List<String> list = new ArrayList<>();
        list.add("1111 1");
        list.add("1111 2");
        list.add("1111 3");
        list.add("1111 4");
        list.add("1111 5");
        list.add("1111 6");
        list.add("1111 7");
        list.add("1111 8");
        list.add("1111 9");
        list.add("111 10");
        list.add("111 11");
        list.add("111 12");
        list.add("111 13");
        list.add("111 14");
        return list;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private class MyWheelViewAdapter implements WheelAdapter<String> {
        private List<String> list;

        public MyWheelViewAdapter(List<String> list) {
            this.list = list;
        }

        @Override
        public int getItemsCount() {
            return list.size();
        }

        @Override
        public String getItem(int index) {
            return list.get(index);
        }

        @Override
        public int indexOf(String o) {
            return list.indexOf(o);
        }
    }
}
