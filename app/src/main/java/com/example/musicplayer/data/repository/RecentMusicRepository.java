package com.example.musicplayer.data.repository;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.musicplayer.data.model.RecentMusic;

import java.util.ArrayList;
import java.util.List;

public class RecentMusicRepository extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "musicplayer.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_RECENT_MUSIC = "recent_music";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_ARTIST = "artist";
    private static final String COLUMN_ALBUM = "album";
    private static final String COLUMN_PATH = "path";

    public RecentMusicRepository(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_RECENT_MUSIC_TABLE = "CREATE TABLE " + TABLE_RECENT_MUSIC + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_ARTIST + " TEXT,"
                + COLUMN_ALBUM + " TEXT,"
                + COLUMN_PATH + " TEXT" + ")";
        db.execSQL(CREATE_RECENT_MUSIC_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECENT_MUSIC);
        onCreate(db);
    }

    public void insertRecentMusic(RecentMusic recentMusic) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, recentMusic.getTitle());
        values.put(COLUMN_ARTIST, recentMusic.getArtist());
        values.put(COLUMN_ALBUM, recentMusic.getAlbum());
        values.put(COLUMN_PATH, recentMusic.getPath());

        db.insert(TABLE_RECENT_MUSIC, null, values);
        db.close();
    }

    public List<RecentMusic> getAllRecentMusic() {
        List<RecentMusic> recentMusicList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_RECENT_MUSIC;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") RecentMusic recentMusic = new RecentMusic(
                        cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_ARTIST)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_ALBUM)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_PATH))
                );
                recentMusicList.add(recentMusic);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return recentMusicList;
    }
}
