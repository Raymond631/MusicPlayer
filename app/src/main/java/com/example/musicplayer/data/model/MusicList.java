package com.example.musicplayer.data.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MusicList implements Serializable {
    private int id;
    private String name;
    private List<Music> musicList;

    public MusicList() {
    }

    public MusicList(String name) {
        this.name = name;
        this.musicList = new ArrayList<>();
    }

    public MusicList(int id, String name) {
        this.id = id;
        this.name = name;
        this.musicList = new ArrayList<>();
    }

    public MusicList(int id, String name, List<Music> musicList) {
        this.id = id;
        this.name = name;
        this.musicList = musicList;
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

    public List<Music> getMusicList() {
        return musicList;
    }

    public void setMusicList(List<Music> musicList) {
        this.musicList = musicList;
    }
}
