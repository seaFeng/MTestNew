package com.dahai.mtest.test;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.util.Log;

import java.io.IOException;

public class MusicPlayerService extends Service {
    private static final String TAG = "MusicPlayerService";

    private MediaPlayer player = new MediaPlayer();
    private MyBinder binder = new MyBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        //player.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG,"执行了onBind()");
        return binder;
    }
    //Service.START_FLAG_REDELIVERY, Service.START_FLAG_RETRY
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e(TAG,"执行了onUnbind()方法");
        return super.onUnbind(intent);
    }

    class MyBinder extends Binder {
        /**
         *  准备播放
         */
        public void prepare(String path){
            try {
                player.setDataSource(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void prepare(){
            try {
                player.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         *  播放音乐
         */
        public void firstStart(){
            player.prepareAsync();
            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });
        }

        public void start(){
            if (player.isPlaying()) {
                player.pause();
            } else {
                player.start();
            }
        }

        /**
         *  暂停音乐
         */
        public void pause(){
            player.pause();
        }

        /**
         *  stop播放
         */
        public void stop(){
            player.stop();
        }

        public int getDuration(){
            return player.getDuration();
        }

        public int getCurrentPostion(){
            return player.getCurrentPosition();
        }

        public void seekTo(int i) {
            player.seekTo(i);
        }

        public MediaPlayer getPlayer(){
            return player;
        }

        public boolean isPlaying(){
            return player.isPlaying();
        }
    }
}
