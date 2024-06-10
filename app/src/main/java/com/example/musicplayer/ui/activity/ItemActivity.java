package com.example.musicplayer.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayer.App;
import com.example.musicplayer.R;
import com.example.musicplayer.data.model.Item;
import com.example.musicplayer.data.model.Music;
import com.example.musicplayer.ui.adapter.MusicAdapter;

import java.util.List;


/**
 * （歌单、歌手、专辑、文件夹）详情
 */
public class ItemActivity extends AppCompatActivity {
    private TextView title;
    private RecyclerView recyclerView;
    private MusicAdapter musicAdapter;
    private Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        // 接受复杂对象用getSerializableExtra
        item = (Item) getIntent().getSerializableExtra("data");
        init();
    }

    private void init() {
        recyclerView = findViewById(R.id.lv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // 设置RecyclerView的布局管理器

        title = findViewById(R.id.title);
        title.setText(item.getName());

        // 按照歌名的Unicode值排序
        List<Music> musicList = item.getMusicList();
        musicList.sort((o1, o2) -> o1.getTitle().compareToIgnoreCase(o2.getTitle()));

        musicAdapter = new MusicAdapter(this, musicList, position -> {
            if (!musicList.isEmpty()) {
                App.getService().setMusicList(musicList);
                App.getService().setPosition(position);
                startActivity(new Intent(this, PlayerActivity.class));
            }
        });
        recyclerView.setAdapter(musicAdapter);
    }
}