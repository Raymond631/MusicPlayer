package com.example.musicplayer.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.musicplayer.R;
import com.example.musicplayer.data.model.Item;
import com.example.musicplayer.data.model.MusicList;
import com.example.musicplayer.data.repository.MusicListRepository;
import com.example.musicplayer.ui.adapter.ItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class MusicListActivity extends AppCompatActivity {
    private MusicListRepository musicListRepository = new MusicListRepository(this);
    private ListView listView;
    private ItemAdapter itemAdapter;
    private List<Item> itemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list);
        buildData();
        init();
    }

    private void init() {
        listView = findViewById(R.id.lv);
        itemAdapter = new ItemAdapter(this, itemList);
        listView.setAdapter(itemAdapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            if (!itemList.isEmpty()) {
                Intent intent = new Intent(this, ItemActivity.class);
                // 传递的对象要implements Serializable
                intent.putExtra("data", itemList.get(position));
                startActivity(intent);
            }
        });

        Button addPlaylistButton = findViewById(R.id.addPlaylistButton);
        addPlaylistButton.setOnClickListener(v -> showAddPlaylistDialog());
    }

    private void buildData() {
        List<MusicList> musicLists = musicListRepository.getMusicLists();
        itemList.clear();
        for (MusicList ml : musicLists) {
            Item item = new Item();
            item.setName(ml.getName());
            item.setMusicList(musicListRepository.getMusicByList(ml));
            itemList.add(item);
        }
    }

    private void showAddPlaylistDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("新建歌单");

        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String playlistName = input.getText().toString();
                // 调用函数并传入歌单名称
                musicListRepository.createList(new MusicList(playlistName));
                buildData();
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

}