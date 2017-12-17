package com.dahai.mtest.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;
import java.util.Set;

/**
 * Created by 张海洋 on 2017-10-17.
 */

public class SharePreferencesUtils {

    public static void putString(Context context,String key,String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences("temp", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(key,value);
        edit.apply();
    }

    public static String getString(Context context,String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences("temp",Context.MODE_PRIVATE);
        String value = sharedPreferences.getString("key", "");
        return value;
    }

    public static void putString(Context context, Map<String,String> map) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("temp",Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();

        Set<String> strings = map.keySet();
        for (String key : strings) {
            String value = map.get(key);
            edit.putString(key,value);
        }
        edit.apply();
    }
}
