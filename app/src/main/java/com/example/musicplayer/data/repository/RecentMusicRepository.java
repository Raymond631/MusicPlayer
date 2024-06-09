package com.example.musicplayer.data.repository;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.musicplayer.data.model.Music;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RecentMusicRepository extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "musicplayer.db";
    private static final int DATABASE_VERSION = 1;

    private static final int MAX_LINE = 100;


    public RecentMusicRepository(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE recent (\n" +
                "  music_id INTEGER NOT NULL,\n" +
                "  add_time DATE NOT NULL,\n" +
                "  PRIMARY KEY (music_id, add_time),\n" +
                "  FOREIGN KEY (music_id) REFERENCES music (id) ON DELETE CASCADE ON UPDATE CASCADE\n" +
                ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS recent");
        onCreate(db);
    }

    public List<Music> getAllRecentMusic() {
        List<Music> musicList = new ArrayList<>();
        String selectQuery = String.format("select * from recent order by add_time desc limit %d", MAX_LINE);

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

    public void insertRecentMusic(Music music) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = String.format("insert into recent values (%d,'%s')",
                music.getId(), new Date());
        db.execSQL(sql);
        db.close();
    }


}
