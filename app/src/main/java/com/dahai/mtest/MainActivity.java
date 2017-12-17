package com.dahai.mtest;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.Toast;

import com.dahai.mtest.test.DrawTestFragment;
import com.dahai.mtest.test.MusicPlayerFragment;
import com.dahai.mtest.test.Test1Fragment;
import com.dahai.mtest.test.Test2Fragment;
import com.dahai.mtest.test.Test3Fragment;
import com.dahai.mtest.utils.SharePreferencesUtils;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private InputMethodManager inputMethodManager;

    private ListView listView;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //judge();
        findView();
        init();
    }

    private void findView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {   //View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        toolbar = (Toolbar) findViewById(R.id.main_tool_bar);
        drawerLayout = (DrawerLayout) findViewById(R.id.main_drawerLayout);
        nav = (NavigationView) findViewById(R.id.mian_left);
    }

    private void init() {
        //toolbar.setTitle("标题");
        toolbar.setTitleTextColor(Color.parseColor("#0000ff"));
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);               // 设置返回键可点击。
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);          // 显示返回键
        mDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_layout_open,R.string.drawer_layout_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //loge("抽屉打开");
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                //loge("抽屉关闭");
            }
        };
        drawerLayout.addDrawerListener(mDrawerToggle);
        setNavigationItem();
        /*inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);*/
        nav.getMenu().findItem(R.id.list_action1).setChecked(true);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_content,new DrawTestFragment());
        transaction.commit();
    }

    private void setNavigationItem() {
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                switch (item.getItemId()) {
                    case R.id.list_action1:
                        //SharePreferencesUtils.putString(MainActivity.this,"key","value");
                        transaction.replace(R.id.main_content,new DrawTestFragment());
                        transaction.commit();
                        break;
                    case R.id.list_action2:
                        Log.e(TAG,"1111111111111111111111111111");
                        transaction.replace(R.id.main_content,new Test3Fragment());
                        transaction.commit();
                        break;
                    case R.id.list_action3:
                        transaction.replace(R.id.main_content,new Test2Fragment());
                        transaction.commit();
                        break;
                }

                item.setChecked(true);
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void judge() {
        if (isTaskRoot()) {
            Intent intent = getIntent();
            String action = intent.getAction();
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && action.equals(Intent.ACTION_MAIN)) {
                Toast.makeText(this, "根View", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void hideSoftKeyboard(){
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null) {
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    public void loge(String msg){
        Log.e("MainActivity",msg);
    }
}
