package com.example.musicplayer.data.repository;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.musicplayer.data.model.Music;

import java.util.ArrayList;
import java.util.List;

public class MusicRepository extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "musicplayer.db";
    private static final int DATABASE_VERSION = 1;

    public MusicRepository(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE music (\n" +
                "  id integer NOT NULL,\n" +
                "  title text,\n" +
                "  artist text,\n" +
                "  album text,\n" +
                "  data text,\n" +
                "  duration integer,\n" +
                "  time text,\n" +
                "  PRIMARY KEY (id)\n" +
                ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS music");
        onCreate(db);
    }

    public List<Music> getAll() {
        List<Music> musicList = new ArrayList<>();
        String selectQuery = "select * from music";

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

    public void addAll(List<Music> musicList) {
        SQLiteDatabase db = this.getWritableDatabase();
        for (Music music : musicList) {
            // 如果主键重复，则更新
            // 注意字符串变量要加引号
            String sql = String.format("insert or replace into music values (%d,'%s','%s','%s','%s',%d,'%s')",
                    music.getId(), music.getTitle(), music.getArtist(), music.getAlbum(), music.getData(), music.getDuration(), music.getTime());
            db.execSQL(sql);
        }
        db.close();
    }
}
