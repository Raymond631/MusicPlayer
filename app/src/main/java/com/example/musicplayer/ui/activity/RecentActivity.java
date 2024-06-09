package com.example.musicplayer.ui.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.musicplayer.R;
import com.example.musicplayer.data.model.Music;
import com.example.musicplayer.data.repository.RecentMusicRepository;
import com.example.musicplayer.ui.fragment.SingleSongFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 最近播放
 */
public class RecentActivity extends AppCompatActivity {
    private RecentMusicRepository recentMusicRepository = new RecentMusicRepository(this);
    private List<Music> recentMusic = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent);
        recentMusic = recentMusicRepository.getAllRecentMusic();
        for (Music music : recentMusic) {
            Log.e("ABC", music.toString());
        }
        if (recentMusic.isEmpty()) {
            Log.e("ABC", "空空空空空空空空空空空");
        }

        // 获取 FragmentManager
        FragmentManager fragmentManager = getSupportFragmentManager();
        // 开始 FragmentTransaction
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // 创建并添加 Fragment 实例
        SingleSongFragment myFragment = new SingleSongFragment(recentMusic);
        fragmentTransaction.add(R.id.main, myFragment);
        // 提交事务
        fragmentTransaction.commit();
    }
}