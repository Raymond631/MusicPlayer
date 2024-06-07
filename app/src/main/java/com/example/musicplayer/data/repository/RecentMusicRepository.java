package com.example.musicplayer.data.repository;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.musicplayer.data.model.Music;

import java.util.ArrayList;
import java.util.List;

public class RecentMusicRepository extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "musicplayer.db";
    private static final int DATABASE_VERSION = 1;

    private static final int MAX_LINE = 100;

    private static final String TABLE_RECENT_MUSIC = "recent_music";
    private static final String COLUMN_ID = "id";  // 自增主键
    private static final String COLUMN_MUSIC_ID = "music_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_ARTIST = "artist";
    private static final String COLUMN_ALBUM = "album";
    private static final String COLUMN_DATA = "data";
    private static final String COLUMN_DURATION = "duration";

    public RecentMusicRepository(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_RECENT_MUSIC_TABLE = "CREATE TABLE " + TABLE_RECENT_MUSIC + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_MUSIC_ID + " INTEGER,"
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_ARTIST + " TEXT,"
                + COLUMN_ALBUM + " TEXT,"
                + COLUMN_DATA + " TEXT,"
                + COLUMN_DURATION + " INTEGER"
                + ")";
        db.execSQL(CREATE_RECENT_MUSIC_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECENT_MUSIC);
        onCreate(db);
    }

    public void insertRecentMusic(Music music) {
        SQLiteDatabase db = this.getWritableDatabase();

        // 检查表的行数
        String countQuery = "SELECT COUNT(*) FROM " + TABLE_RECENT_MUSIC;
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.moveToFirst();
        int rowCount = cursor.getInt(0);
        cursor.close();
        // 如果行数达到限界值，删除最早插入的记录
        if (rowCount >= MAX_LINE) {
            String deleteQuery = "DELETE FROM " + TABLE_RECENT_MUSIC +
                    " WHERE " + COLUMN_ID + " = (SELECT MIN(" + COLUMN_ID + ") FROM " + TABLE_RECENT_MUSIC + ")";
            db.execSQL(deleteQuery);
        }

        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, music.getId());
        values.put(COLUMN_TITLE, music.getTitle());
        values.put(COLUMN_ARTIST, music.getArtist());
        values.put(COLUMN_ALBUM, music.getAlbum());
        values.put(COLUMN_DATA, music.getData());
        values.put(COLUMN_DURATION, music.getDuration());

        db.insert(TABLE_RECENT_MUSIC, null, values);
        db.close();
    }

    public List<Music> getAllRecentMusic() {
        List<Music> recentMusicList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_RECENT_MUSIC;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") Music recentMusic = new Music(
                        cursor.getLong(cursor.getColumnIndex(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_ARTIST)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_ALBUM)),
                        cursor.getLong(cursor.getColumnIndex(COLUMN_DURATION)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_DATA))
                );
                recentMusicList.add(recentMusic);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return recentMusicList;
    }
}
