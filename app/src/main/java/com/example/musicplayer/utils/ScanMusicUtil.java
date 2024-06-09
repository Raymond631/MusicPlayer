package com.example.musicplayer.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.example.musicplayer.App;
import com.example.musicplayer.data.model.Music;

import java.util.ArrayList;
import java.util.List;

public class ScanMusicUtil {
    //使用ContentResolver获取音乐
    public static List<Music> getMusic(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Audio.Media.IS_MUSIC);
        List<Music> musicList = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
                String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
                String data = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                int duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
                if (duration > 5000) {
                    String time = String.format("%s:%s", TimeUtil.format(duration / 1000 / 60), TimeUtil.format(duration / 1000 % 60));
                    Music music = new Music(id, title, artist, album, data, duration, time);
                    musicList.add(music);
                }
            }
            App.setMusicList(musicList);
            cursor.close();
        }
        return musicList;
    }
}
