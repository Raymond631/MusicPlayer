package com.example.musicplayer;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.example.musicplayer.data.model.Music;
import com.example.musicplayer.service.MusicService;

import java.util.ArrayList;
import java.util.List;

public class App extends Application {
    private static MusicService musicService;
    private static List<Music> musicList = new ArrayList<>();
    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            musicService = ((MusicService.MusicBinder) service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicService = null;
        }
    };

    public static List<Music> getMusicList() {
        return musicList;
    }

    public static void setMusicList(List<Music> musicList) {
        App.musicList = musicList;
        musicService.setMusicList(musicList);
    }

    public static MusicService getService() {
        return musicService;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        bindService(new Intent(this, MusicService.class), serviceConnection, Context.BIND_AUTO_CREATE);
    }
}
