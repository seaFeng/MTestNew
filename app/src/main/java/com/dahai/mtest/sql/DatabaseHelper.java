package com.dahai.mtest.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 张海洋 on 2017-12-12.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String name = "Student";   // 数据库名称
    private static final int version = 1;           // 数据库版本。

    public DatabaseHelper(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE person(personId integer primary key autoincrement,name varchar(20),age ingeger)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
