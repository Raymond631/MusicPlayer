package com.example.musicplayer.data.model;

import java.util.ArrayList;
import java.util.List;

public class MusicList {
    private int id;
    private String name;
    private List<Music> musics;

    public MusicList() {
    }

    public MusicList(int id, String name, List<Music> musics) {
        this.id = id;
        this.name = name;
        this.musics = musics;
    }

    public MusicList(String name) {
        this.name = name;
        this.musics = new ArrayList<>();
    }

    public void add(Music music) {
        musics.add(music);
    }

    public void remove(Music music) {
        musics.remove(music);
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

    public List<Music> getMusics() {
        return musics;
    }

    public void setMusics(List<Music> musics) {
        this.musics = musics;
    }
}
