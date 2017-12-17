package com.dahai.mtest.sql;


import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dahai.mtest.R;

import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class SqliteFragment extends Fragment implements View.OnClickListener {

    View rootView;
    private Activity activity;
    private TextView tv_show;
    SQLiteDatabase database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = getActivity();
        rootView = inflater.inflate(R.layout.fragment_sqlite,container,false);
        initView();

        return rootView;
    }

    private void initView() {
        TextView tv_create = rootView.findViewById(R.id.iv_sql_createDataBase);
        tv_create.setOnClickListener(this);
        TextView tv_insert = rootView.findViewById(R.id.iv_sql_insert);
        tv_insert.setOnClickListener(this);
        TextView tv_select = rootView.findViewById(R.id.iv_sql_select);
        tv_select.setOnClickListener(this);

        tv_show = rootView.findViewById(R.id.tv_sql_show);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_sql_createDataBase:
                DatabaseHelper helper = new DatabaseHelper(activity);
                database = helper.getWritableDatabase();
                break;
            case R.id.iv_sql_insert:
                database.execSQL("insert into person(name,age) values('haige',25)");
                break;
            case R.id.iv_sql_select:
                Cursor cursor = database.rawQuery("select * from person",null);
                StringBuilder sb = new StringBuilder();
                while (cursor.moveToNext()) {
                    cursor.getInt(0);
                    String name = cursor.getString(1);
                    int age = cursor.getInt(2);
                    sb.append("姓名 = " + name + "   age = " + age);
                }

                tv_show.setText(sb.toString());
                break;
        }
    }
}
