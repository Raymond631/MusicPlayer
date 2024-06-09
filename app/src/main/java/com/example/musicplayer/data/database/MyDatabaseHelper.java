package com.example.musicplayer.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "musicplayer";
    private static final int DATABASE_VERSION = 1;

    // Table creation SQL statement
    private static final String TABLE_MUSIC = "CREATE TABLE music (\n" +
            "  id integer NOT NULL,\n" +
            "  title text,\n" +
            "  artist text,\n" +
            "  album text,\n" +
            "  data text,\n" +
            "  duration integer,\n" +
            "  time text,\n" +
            "  PRIMARY KEY (id)\n" +
            ")";

    private static final String TABLE_FAVORITE = "CREATE TABLE favorite (\n" +
            "  music_id INTEGER NOT NULL,\n" +
            "  PRIMARY KEY (music_id),\n" +
            "  FOREIGN KEY (music_id) REFERENCES music (id) ON DELETE CASCADE ON UPDATE CASCADE\n" +
            ")";

    private static final String TABLE_RECENT = "CREATE TABLE recent (\n" +
            "  music_id INTEGER NOT NULL,\n" +
            "  add_time DATE NOT NULL,\n" +
            "  PRIMARY KEY (music_id, add_time),\n" +
            "  FOREIGN KEY (music_id) REFERENCES music (id) ON DELETE CASCADE ON UPDATE CASCADE\n" +
            ")";

    private static final String TABLE_LIST = "CREATE TABLE list (\n" +
            "  id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
            "  name TEXT\n" +
            ")";

    private static final String TABLE_ITEM = "CREATE TABLE list_item (\n" +
            "  list_id INTEGER NOT NULL,\n" +
            "  music_id INTEGER NOT NULL,\n" +
            "  PRIMARY KEY (list_id, music_id),\n" +
            "  FOREIGN KEY (list_id) REFERENCES list (id) ON DELETE CASCADE ON UPDATE CASCADE,\n" +
            "  FOREIGN KEY (music_id) REFERENCES music (id) ON DELETE CASCADE ON UPDATE CASCADE\n" +
            ")";


    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_MUSIC);
        db.execSQL(TABLE_FAVORITE);
        db.execSQL(TABLE_RECENT);
        db.execSQL(TABLE_LIST);
        db.execSQL(TABLE_ITEM);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS recent");
        onCreate(db);
    }
}

