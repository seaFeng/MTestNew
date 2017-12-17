package com.dahai.mtest.test;


import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dahai.mtest.R;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class MusicPlayerFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "MusicPlayerFragment";

    private TextView tv_play;
    private SeekBar seekBar;
    //private Timer timer;
    /**
     * 播放器还未准备
     */
    private boolean isPrepared = false;

    private MediaPlayer player = new MediaPlayer();

    private MusicPlayerService.MyBinder binder;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = (MusicPlayerService.MyBinder) service;
            //seekBar.setMax( binder.getDuration());
            /*binder.getPlayer().setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.seekTo(0);
                }
            });*/
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (binder.getPlayer() != null)
                        try {
                            if (seekBar.getMax() < binder.getDuration()) {
                                seekBar.setMax(binder.getDuration());
                            }
                            seekBar.setProgress(binder.getCurrentPostion());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }finally {

                        }
                    sendEmptyMessageDelayed(1,100);
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_music_player, container, false);
        initView(rootView);
        return rootView;
    }


    private void initView(View rootView) {
        Log.e(TAG, "当前线程：" + Thread.currentThread().getName());
        tv_play = rootView.findViewById(R.id.MediaPlayer_play);
        seekBar = rootView.findViewById(R.id.MediaPlayer_progress);
        tv_play.setOnClickListener(this);

        getActivity().bindService(new Intent(getActivity(), MusicPlayerService.class), connection, Context.BIND_AUTO_CREATE);


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b)
                binder.seekTo(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.MediaPlayer_play:
                Log.e("mediaPlayer", "点击时间触发");
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    Log.e("mediaPlayer", "权限没有申请");
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                } else {
                    try {
                        if (!isPrepared) {
                            try {
                                //initPlayer();
                                binder.prepare("/sdcard/朴树 - 平凡之路.mp3");
                                binder.firstStart();
                                seekBar.setMax(binder.getDuration());
                                handler.sendEmptyMessage(1);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            isPrepared = true;
                        } else if (binder.isPlaying()) {
                            binder.pause();
                        } else {
                            binder.start();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("mediaPlayer", e.toString());
                    }
                }
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        /*if (seekBar != null && binder != null) {
            seekBar.setMax((int) binder.getDuration());
            seekBar.setProgress((int) binder.getCurrentPostion());
            //Log.e(binder.)
        }*/

    }

    private void initPlayer() {
        /*timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (player != null)
                    if (player.isPlaying()) {
                        Message msg = handler.obtainMessage();
                        msg.what = 0;
                        handler.sendMessage(msg);
                    }
            }
        };*/

        try {
            player.setDataSource("/sdcard/朴树 - 平凡之路.mp3");
            player.prepareAsync();
            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    seekBar.setMax(player.getDuration());
                    //seekBar.setProgress(0);
                    player.start();
                    handler.sendEmptyMessage(1);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 时间单位为毫秒
        //timer.schedule(task,0,100);
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                Toast.makeText(getActivity(), "播放结束", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onCompletion: " + "播放结束");
                //player.reset();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //timer.cancel();
    }

    @Override
    public void onStop() {
        super.onStop();
        //timer.cancel();
        if (binder.getPlayer() != null) {
            //binder.pause();
            /*binder.stop();
            binder.prepare();*/
            /*player.release();*/
        }
        /*if (timer != null) {
            timer.cancel();
        }*/
    }
}
