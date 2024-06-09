package com.example.musicplayer.data.repository;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.musicplayer.data.database.MyDatabaseHelper;
import com.example.musicplayer.data.model.Music;

import java.util.ArrayList;
import java.util.List;

public class FavoriteMusicRepository extends MyDatabaseHelper {
    private static final String DATABASE_NAME = "musicplayer.db";
    private static final int DATABASE_VERSION = 1;

    public FavoriteMusicRepository(Context context) {
        super(context);
    }

    public void insertFavoriteMusic(Music music) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = String.format("insert into favorite values (%d)", music.getId());
        db.execSQL(sql);
        db.close();
    }

    public void cancelFavoriteMusic(Music music) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = String.format("delete from favorite where music_id = %d", music.getId());
        db.execSQL(sql);
        db.close();
    }

    public List<Music> getAllFavoriteMusic() {
        List<Music> musicList = new ArrayList<>();
        String selectQuery = "select * from favorite as f inner join music as m on f.music_id = m.id order by title";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                String artist = cursor.getString(cursor.getColumnIndexOrThrow("artist"));
                String album = cursor.getString(cursor.getColumnIndexOrThrow("album"));
                String data = cursor.getString(cursor.getColumnIndexOrThrow("data"));
                int duration = cursor.getInt(cursor.getColumnIndexOrThrow("duration"));
                String time = cursor.getString(cursor.getColumnIndexOrThrow("time"));
                Music music = new Music(id, title, artist, album, data, duration, time);
                musicList.add(music);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return musicList;
    }

    public boolean isFavorite(Music music) {
        boolean result = false;
        String selectQuery = String.format("select * from favorite as f inner join music as m on f.music_id = m.id where m.id = %d", music.getId());

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            result = true;
        }
        cursor.close();
        db.close();
        return result;
    }
}
