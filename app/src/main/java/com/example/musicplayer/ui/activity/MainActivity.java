package com.example.musicplayer.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.musicplayer.App;
import com.example.musicplayer.R;
import com.example.musicplayer.data.model.Music;
import com.example.musicplayer.data.repository.MusicRepository;
import com.example.musicplayer.service.MusicService;
import com.example.musicplayer.utils.ScanMusicUtil;

import java.util.List;

/**
 * 首页
 */
public class MainActivity extends AppCompatActivity {
    private MusicRepository musicRepository = new MusicRepository(this);
    private CardView local, recent, like, list;
    private TextView name, singer;
    private ImageView imageview, pre, play, next;
    private MusicService musicService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 请求权限（坑）
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // android 13以上
            requestPermissions(new String[]{
                    Manifest.permission.READ_MEDIA_AUDIO,
                    Manifest.permission.POST_NOTIFICATIONS
            }, 0);
        } else {
            // android 13以下
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        }
        init();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean grant = true;
        if (requestCode == 0) {
            for (Integer integer : grantResults) {
                if (integer != 0) {
                    grant = false;
                    break;
                }
            }
            //权限给了，就获取音乐
            if (grant) {
                List<Music> musicList = ScanMusicUtil.getMusic(this);
                musicRepository.addAll(musicList);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void init() {
        local = findViewById(R.id.local);
        recent = findViewById(R.id.recent);
        like = findViewById(R.id.like);
        list = findViewById(R.id.list);

        imageview = findViewById(R.id.imageview);
        name = findViewById(R.id.name);
        singer = findViewById(R.id.singer);
        pre = findViewById(R.id.imageview_front);
        play = findViewById(R.id.imageview_play);
        next = findViewById(R.id.imageview_next);

        local.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LocalMusicActivity.class);
            startActivity(intent);
        });

        recent.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RecentActivity.class);
            startActivity(intent);
        });

        like.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FavoritesActivity.class);
            startActivity(intent);
        });

        list.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MusicListActivity.class);
            startActivity(intent);
        });

        imageview.setOnClickListener(v -> {
            if (App.getService().isPlaying()) {
                Intent intent = new Intent(MainActivity.this, PlayerActivity.class);
                startActivity(intent);
            }
        });

        //播放
        play.setOnClickListener(v -> {
            MusicService service = App.getService();
            if (service.getNowPlay() != null) {
                service.playOrPause();
                if (service.isPlaying()) {
                    play.setImageResource(R.mipmap.pause);
                } else {
                    play.setImageResource(R.mipmap.play);
                }
            }
        });
        //上一首
        pre.setOnClickListener(v -> App.getService().pre());
        //下一首
        next.setOnClickListener(v -> App.getService().next());
    }

    @Override
    protected void onResume() {
        super.onResume();
        musicService = App.getService();
        if (musicService != null) {
            Music music = musicService.getNowPlay();
            if (music != null) {
                name.setText(music.getTitle());
                singer.setText(music.getArtist());
            }

            if (musicService.isPlaying()) {
                play.setImageResource(R.mipmap.pause);
            } else {
                play.setImageResource(R.mipmap.play);
            }
        }
    }
}