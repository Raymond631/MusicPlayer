package com.example.musicplayer.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MusicService extends Service {
    private static final String TAG = "MusicService";
    private final IBinder binder = new MusicBinder();
    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Service Created");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String musicPath = intent.getStringExtra("MUSIC_PATH");
        if (musicPath != null) {
            playMusic(musicPath);
        }
        return START_NOT_STICKY;
    }

    private void playMusic(String path) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        mediaPlayer = MediaPlayer.create(this, Uri.parse(path));
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    public void pauseMusic() {
        if (isPlaying()) {
            mediaPlayer.pause();
        }
    }

    public void resumeMusic() {
        if (isPlaying()) {
            mediaPlayer.start();
        }
    }

    public boolean isPlaying() {
        return mediaPlayer != null && mediaPlayer.isPlaying();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        super.onDestroy();
        Log.d(TAG, "Service Destroyed");
    }

    public class MusicBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }
}