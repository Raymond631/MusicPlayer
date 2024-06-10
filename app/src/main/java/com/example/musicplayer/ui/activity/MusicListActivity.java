package com.example.musicplayer.ui.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayer.R;
import com.example.musicplayer.data.model.Item;
import com.example.musicplayer.data.model.MusicList;
import com.example.musicplayer.data.repository.MusicListRepository;
import com.example.musicplayer.ui.adapter.ItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class MusicListActivity extends AppCompatActivity {
    private MusicListRepository musicListRepository = new MusicListRepository(this);
    private Button addPlaylistButton;
    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    private List<MusicList> musicLists = new ArrayList<>();
    private List<Item> itemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list);
        buildData();
        init();
    }

    private void init() {
        addPlaylistButton = findViewById(R.id.addPlaylistButton);
        addPlaylistButton.setOnClickListener(v -> showAddPlaylistDialog());

        recyclerView = findViewById(R.id.lv);
        itemAdapter = new ItemAdapter(this, itemList, this::showDeleteConfirmationDialog);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(itemAdapter);
    }

    private void buildData() {
        musicLists = musicListRepository.getMusicLists();
        itemList.clear();
        for (MusicList ml : musicLists) {
            Item item = new Item();
            item.setName(ml.getName());
            item.setMusicList(musicListRepository.getMusicByList(ml));
            itemList.add(item);
        }
    }

    // 新建歌单
    private void showAddPlaylistDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("新建歌单");

        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton("确定", (dialog, which) -> {
            String playlistName = input.getText().toString();
            // 调用函数并传入歌单名称
            musicListRepository.createList(new MusicList(playlistName));
            buildData();
        });

        builder.setNegativeButton("取消", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    // 删除歌单
    private void showDeleteConfirmationDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("确认删除" + itemList.get(position).getName() + "?")
                .setPositiveButton("确定", (dialog, id) -> {
                    // Remove item from list and update ListView
                    musicListRepository.deleteList(musicLists.get(position));
                    buildData();
                    itemAdapter.notifyItemRemoved(position);
                })
                .setNegativeButton("取消", (dialog, id) -> {
                    // User cancelled the dialog, do nothing
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}