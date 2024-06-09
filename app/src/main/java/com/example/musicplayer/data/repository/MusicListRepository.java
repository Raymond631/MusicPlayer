//package com.example.musicplayer.data.repository;
//
//import android.annotation.SuppressLint;
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
//import com.example.musicplayer.data.model.Music;
//import com.example.musicplayer.data.model.MusicList;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class MusicListRepository extends SQLiteOpenHelper {
//    private static final String DATABASE_NAME = "musicplayer.db";
//    private static final int DATABASE_VERSION = 1;
//
//    private static final String TABLE_MUSIC_LIST = "music_list";  // 父表
//    private static final String COLUMN_ID = "id";
//    private static final String COLUMN_NAME = "name";
//
//    private static final String TABLE_MUSIC = "music";  // 子表
//    private static final String COLUMN_LIST_ID = "list_id";
//    private static final String COLUMN_MUSIC_ID = "music_id";
//    private static final String COLUMN_TITLE = "title";
//    private static final String COLUMN_ARTIST = "artist";
//    private static final String COLUMN_ALBUM = "album";
//    private static final String COLUMN_DATA = "data";
//    private static final String COLUMN_DURATION = "duration";
//
//
//    public MusicListRepository(Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//    }
//
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        String CREATE_MUSIC_LIST_TABLE = "CREATE TABLE " + TABLE_MUSIC_LIST + "("
//                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
//                + COLUMN_NAME + " TEXT"
//                + ")";
//
//        String CREATE_MUSIC_TABLE = "CREATE TABLE " + TABLE_MUSIC + "("
//                + COLUMN_LIST_ID + " INTEGER,"
//                + COLUMN_MUSIC_ID + " INTEGER,"
//                + COLUMN_TITLE + " TEXT,"
//                + COLUMN_ARTIST + " TEXT,"
//                + COLUMN_ALBUM + " TEXT,"
//                + COLUMN_DATA + " TEXT,"
//                + COLUMN_DURATION + " INTEGER,"
//                + " PRIMARY KEY (" + COLUMN_LIST_ID + ", " + COLUMN_MUSIC_ID + ")"
//                + ")";
//        db.execSQL(CREATE_MUSIC_LIST_TABLE);
//        db.execSQL(CREATE_MUSIC_TABLE);
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MUSIC_LIST);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MUSIC);
//        onCreate(db);
//    }
//
//    // 创建歌单
//    public void createList(MusicList musicList) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_NAME, musicList.getName());
//
//        db.insert(TABLE_MUSIC_LIST, null, values);
//        db.close();
//    }
//
//    // 删除歌单
//    public void deleteList(MusicList musicList) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        String deleteQuery = "DELETE FROM " + TABLE_MUSIC_LIST
//                + " WHERE " + COLUMN_ID + " = " + musicList.getId();
//        db.execSQL(deleteQuery);
//        db.close();
//    }
//
//    // 查询歌单
//    public List<MusicList> getMusicLists() {
//        List<MusicList> allMusicList = new ArrayList<>();
//        String selectQuery = "SELECT * FROM " + TABLE_MUSIC_LIST;
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        if (cursor.moveToFirst()) {
//            do {
//                @SuppressLint("Range") MusicList musicList = new MusicList(
//                        cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
//                        cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
//                );
//                allMusicList.add(musicList);
//            } while (cursor.moveToNext());
//        }
//
//        cursor.close();
//        db.close();
//        return allMusicList;
//    }
//
//    // 添加歌曲
//    public void addMusic(MusicList musicList, Music music) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_LIST_ID, musicList.getId());
//        values.put(COLUMN_MUSIC_ID, music.getId());
//        values.put(COLUMN_TITLE, music.getTitle());
//        values.put(COLUMN_ARTIST, music.getArtist());
//        values.put(COLUMN_ALBUM, music.getAlbum());
//        values.put(COLUMN_DATA, music.getData());
//        values.put(COLUMN_DURATION, music.getDuration());
//
//        db.insert(TABLE_MUSIC, null, values);
//        db.close();
//    }
//
//    // 删除歌曲
//    public void deleteMusic(MusicList musicList, Music music) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        String deleteQuery = "DELETE FROM " + TABLE_MUSIC
//                + " WHERE " + COLUMN_LIST_ID + " = " + musicList.getId()
//                + " AND " + COLUMN_MUSIC_ID + " = " + music.getId();
//        db.execSQL(deleteQuery);
//        db.close();
//    }
//
//
//    // 查看某个歌单
//    public List<Music> getMusicByList(MusicList musicList) {
//        List<Music> list = new ArrayList<>();
//        String selectQuery = "SELECT * FROM " + TABLE_MUSIC
//                + " WHERE " + COLUMN_LIST_ID + " = " + musicList.getId();
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        if (cursor.moveToFirst()) {
//            do {
//                @SuppressLint("Range") Music music = new Music(
//                        cursor.getLong(cursor.getColumnIndex(COLUMN_MUSIC_ID)),
//                        cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)),
//                        cursor.getString(cursor.getColumnIndex(COLUMN_ARTIST)),
//                        cursor.getString(cursor.getColumnIndex(COLUMN_ALBUM)),
//                        cursor.getLong(cursor.getColumnIndex(COLUMN_DURATION)),
//                        cursor.getString(cursor.getColumnIndex(COLUMN_DATA))
//                );
//                list.add(music);
//            } while (cursor.moveToNext());
//        }
//
//        cursor.close();
//        db.close();
//        return list;
//    }
//}
