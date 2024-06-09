package com.example.musicplayer.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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
    private ListView listView;
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
        listView = findViewById(R.id.lv);
        title = findViewById(R.id.title);
        title.setText(item.getName());

        // 按照歌名的Unicode值排序
        List<Music> musicList = item.getMusicList();
        musicList.sort((o1, o2) -> o1.getTitle().compareToIgnoreCase(o2.getTitle()));

        musicAdapter = new MusicAdapter(this, musicList);
        listView.setAdapter(musicAdapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            if (!musicList.isEmpty()) {
                // TODO: 播放列表也要能切换，待实现
                App.getService().setMusicList(musicList);
                App.getService().setPosition(position);
                startActivity(new Intent(this, PlayerActivity.class));
            }
        });
    }
}