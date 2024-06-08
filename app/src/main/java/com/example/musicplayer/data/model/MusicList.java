package com.example.musicplayer.data.model;

public class MusicList {
    private int id;
    private String name;

    public MusicList() {
    }

    public MusicList(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public MusicList(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
