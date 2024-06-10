package com.example.musicplayer.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.musicplayer.service.MusicService;

public class NotificationReceiver extends BroadcastReceiver {
    public static final String ACTION_PREVIOUS = "ACTION_PREVIOUS";
    public static final String ACTION_PLAY_PAUSE = "ACTION_PLAY_PAUSE";
    public static final String ACTION_NEXT = "ACTION_NEXT";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action != null) {
            MusicService musicService = ((MusicService.MusicBinder) peekService(context, new Intent(context, MusicService.class))).getService();
            if (ACTION_PREVIOUS.equals(action)) {
                musicService.pre();
            } else if (ACTION_PLAY_PAUSE.equals(action)) {
                if (musicService.getNowPlay() != null) {
                    musicService.playOrPause();
                }
            } else if (ACTION_NEXT.equals(action)) {
                musicService.next();
            }
        }
    }
}

