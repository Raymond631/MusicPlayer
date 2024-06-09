package com.example.musicplayer.data.model;

import java.io.Serializable;

public class Music implements Serializable {
    private int id;
    private String title; // 歌名
    private String artist; // 歌手
    private String album; // 专辑
    private String data; // 文件路径
    private int duration; // 时长（毫秒）
    private String time; // 时长字符串（分：秒）

    public Music() {
    }

    public Music(int id, String title, String artist, String album, String data, int duration, String time) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.data = data;
        this.duration = duration;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Music{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", album='" + album + '\'' +
                ", data='" + data + '\'' +
                ", duration=" + duration +
                ", time='" + time + '\'' +
                '}';
    }
}
