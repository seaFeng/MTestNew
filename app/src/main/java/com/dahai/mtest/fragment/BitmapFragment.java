package com.dahai.mtest.fragment;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dahai.mtest.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class BitmapFragment extends Fragment implements View.OnClickListener{
    private static final String TAG = "BitmapFragment";
    Unbinder unbinder;

    @BindView(R.id.tv_bitmap1)
    TextView tv_bitmap1;
    @BindView(R.id.iv_bitmap)
    ImageView iv_bitmap;

    Bitmap bitmap = null;
    String[] urls = new String[3];

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            iv_bitmap.setImageBitmap(bitmap);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bitmap, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        setListener();
        return view;
    }

    private void init() {
        urls[0] = "http://www.youhuaaa.com/UploadFiles/images/Painting_Pic_Big/10/4975.jpg";
        urls[1] = "http://www.youhuaaa.com/UploadFiles/images/Painting_Pic_Big/97/48191.jpg";
        urls[2] = "http://www.youhuaaa.com/UploadFiles/images/Painting_Pic_Big/96/47765.jpg";
    }

    private void setListener() {
        tv_bitmap1.setOnClickListener(this);
    }
    int i;
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_bitmap1:
                i++;
                if (i > 2) {
                    i = 0;
                }
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        URL url = null;
                        try {
                            url = new URL(urls[i]);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }

                        InputStream inputStream = null;
                        try {
                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                            // true:表示为输入，默认为true；
                            connection.setDoInput(true);
                            connection.connect();
                            inputStream = connection.getInputStream();
                            bitmap = BitmapFactory.decodeStream(inputStream);
                            handler.sendEmptyMessage(1);

                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            if (inputStream != null) {
                                try {
                                    inputStream.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                        }
                    }
                }.start();


                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
