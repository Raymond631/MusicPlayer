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
import com.example.musicplayer.data.model.Item;
import com.example.musicplayer.data.model.Music;
import com.example.musicplayer.ui.activity.ItemActivity;
import com.example.musicplayer.ui.adapter.ItemAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class SingerFragment extends Fragment {
    private ListView listView;
    private ItemAdapter itemAdapter;

    private List<Item> itemList = new ArrayList<>();

    public SingerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_singer, container, false);
        listView = rootView.findViewById(R.id.lv);

        List<Music> musicList = App.getMusicList();
        Map<String, List<Music>> map = new HashMap<>();
        for (Music music : musicList) {
            String artist = music.getArtist();
            if (map.containsKey(artist)) {
                map.get(artist).add(music);
            } else {
                List<Music> temp = new ArrayList<>();
                temp.add(music);
                map.put(artist, temp);
            }
        }
        itemList = map.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> new Item(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        itemAdapter = new ItemAdapter(getContext(), itemList);
        listView.setAdapter(itemAdapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            if (!itemList.isEmpty()) {
                Intent intent = new Intent(getContext(), ItemActivity.class);
                // 传递的对象要implements Serializable
                intent.putExtra("data", itemList.get(position));
                startActivity(intent);
            }
        });
        return rootView;
    }
}