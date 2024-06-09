package com.example.musicplayer.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.musicplayer.App;
import com.example.musicplayer.R;
import com.example.musicplayer.data.model.Music;
import com.example.musicplayer.ui.activity.PlayerActivity;
import com.example.musicplayer.ui.adapter.MusicAdapter;

import java.util.List;

public class SingleSongFragment extends Fragment {
    private ListView listView;
    private MusicAdapter musicAdapter;

    private List<Music> musicList = App.getMusicList();

    public SingleSongFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_single_song, container, false);
        listView = rootView.findViewById(R.id.lv);
        musicAdapter = new MusicAdapter(getContext(), musicList);
        listView.setAdapter(musicAdapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            if (!musicList.isEmpty()) {
                App.getService().setPosition(position);
                startActivity(new Intent(getContext(), PlayerActivity.class));
            }
        });
        return rootView;
    }

}