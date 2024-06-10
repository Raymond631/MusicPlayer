package com.example.musicplayer.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayer.App;
import com.example.musicplayer.R;
import com.example.musicplayer.data.model.Item;
import com.example.musicplayer.data.model.Music;
import com.example.musicplayer.ui.adapter.ItemAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class AlbumFragment extends Fragment {
    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;

    private List<Item> itemList = new ArrayList<>();

    public AlbumFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_album, container, false);
        recyclerView = rootView.findViewById(R.id.lv);

        List<Music> musicList = App.getMusicList();
        Map<String, List<Music>> map = new HashMap<>();
        for (Music music : musicList) {
            String album = music.getAlbum();
            if (map.containsKey(album)) {
                map.get(album).add(music);
            } else {
                List<Music> temp = new ArrayList<>();
                temp.add(music);
                map.put(album, temp);
            }
        }
        itemList = map.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> new Item(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        itemAdapter = new ItemAdapter(getContext(), itemList, null);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(itemAdapter);

        return rootView;
    }
}