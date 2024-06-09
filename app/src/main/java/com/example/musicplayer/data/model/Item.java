package com.example.musicplayer.data.model;

import java.io.Serializable;
import java.util.List;

public class Item implements Serializable {
    private String name;
    private List<Music> musicList;

    public Item() {
    }


    public Item(String name, List<Music> musicList) {
        this.name = name;
        this.musicList = musicList;
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
