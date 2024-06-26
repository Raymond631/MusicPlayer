package com.example.musicplayer.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayer.App;
import com.example.musicplayer.R;
import com.example.musicplayer.data.model.Music;
import com.example.musicplayer.ui.activity.PlayerActivity;
import com.example.musicplayer.ui.adapter.MusicAdapter;

import java.util.List;

public class SingleSongFragment extends Fragment {
    private RecyclerView recyclerView;
    private MusicAdapter musicAdapter;

    private List<Music> musicList;
    private boolean sorted = false;

    public SingleSongFragment() {
        this.musicList = App.getMusicList();
    }

    public SingleSongFragment(List<Music> musicList) {
        this.musicList = musicList;
        this.sorted = true;
    }

    public void setMusicList(List<Music> musicList) {
        this.musicList = musicList;
        musicAdapter.setMusicList(musicList);
        musicAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_single_song, container, false);
        recyclerView = rootView.findViewById(R.id.lv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext())); // 设置RecyclerView的布局管理器
        // 按照歌名的Unicode值排序
        if (!sorted) {
            musicList.sort((o1, o2) -> o1.getTitle().compareToIgnoreCase(o2.getTitle()));
        }
        musicAdapter = new MusicAdapter(getContext(), musicList, position -> {
            if (!musicList.isEmpty()) {
                App.getService().setMusicList(musicList);
                App.getService().setPosition(position);
                startActivity(new Intent(getContext(), PlayerActivity.class));
            }
        });
        recyclerView.setAdapter(musicAdapter);
        return rootView;
    }
}