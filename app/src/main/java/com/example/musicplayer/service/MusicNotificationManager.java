package com.example.musicplayer.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MusicNotificationManager extends Service {
    public MusicNotificationManager() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}