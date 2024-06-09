package com.example.musicplayer.data.repository;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.musicplayer.data.database.MyDatabaseHelper;
import com.example.musicplayer.data.model.Music;
import com.example.musicplayer.data.model.MusicList;

import java.util.ArrayList;
import java.util.List;

public class MusicListRepository extends MyDatabaseHelper {
    private static final String DATABASE_NAME = "musicplayer.db";
    private static final int DATABASE_VERSION = 1;

    public MusicListRepository(Context context) {
        super(context);
    }

    // 创建歌单
    public void createList(MusicList musicList) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = String.format("insert into list (name) values ('%s')", musicList.getName());
        db.execSQL(sql);
        db.close();
    }

    // 删除歌单
    public void deleteList(MusicList musicList) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = String.format("delete from list where id = %d", musicList.getId());
        db.execSQL(sql);
        db.close();
    }

    // 查询歌单
    public List<MusicList> getMusicLists() {
        List<MusicList> allMusicList = new ArrayList<>();
        String selectQuery = "select * from list";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                MusicList musicList = new MusicList(
                        cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("name"))
                );
                allMusicList.add(musicList);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return allMusicList;
    }

    // 添加歌曲
    public void addMusic(MusicList musicList, Music music) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = String.format("insert into list_item values (%d, %d)", musicList.getId(), music.getId());
        db.execSQL(sql);
        db.close();
    }

    // 删除歌曲
    public void deleteMusic(MusicList musicList, Music music) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = String.format("delete from list_item where list_id = %d and music_id = %d", musicList.getId(), music.getId());
        db.execSQL(sql);
        db.close();
    }


    // 查看某个歌单
    public List<Music> getMusicByList(MusicList list) {
        List<Music> musicList = list.getMusicList();
        String selectQuery = String.format("select * from list_item as l inner join music as m on l.music_id = m.id where list_id = %d order by title", list.getId());

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
}
