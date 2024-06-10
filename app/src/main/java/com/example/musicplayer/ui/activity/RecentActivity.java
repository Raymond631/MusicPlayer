package com.example.musicplayer.ui.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;

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
    private Button clearButton;
    private SingleSongFragment fragment;
    private RecentMusicRepository recentMusicRepository = new RecentMusicRepository(this);
    private List<Music> recentMusic = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent);
        recentMusic = recentMusicRepository.getAllRecentMusic();

        // 获取 FragmentManager
        FragmentManager fragmentManager = getSupportFragmentManager();
        // 开始 FragmentTransaction
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // 创建并添加 Fragment 实例
        fragment = new SingleSongFragment(recentMusic);
        fragmentTransaction.add(R.id.container, fragment);
        // 提交事务
        fragmentTransaction.commit();

        clearButton = findViewById(R.id.clearButton);
        clearButton.setOnClickListener(v -> showDeleteConfirmationDialog());
    }

    // 删除歌单
    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("确认清空?")
                .setPositiveButton("确定", (dialog, id) -> {
                    recentMusicRepository.clearRecentMusic();
                    fragment.setMusicList(new ArrayList<>());
                })
                .setNegativeButton("取消", (dialog, id) -> {
                    // User cancelled the dialog, do nothing
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}