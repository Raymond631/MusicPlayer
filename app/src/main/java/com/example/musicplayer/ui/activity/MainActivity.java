package com.example.musicplayer.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.musicplayer.R;
import com.example.musicplayer.utils.ScanMusicUtil;

/**
 * 首页
 */
public class MainActivity extends AppCompatActivity {

    ImageButton local, recent, like;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 请求权限（坑）
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // android 13以上
            requestPermissions(new String[]{Manifest.permission.READ_MEDIA_AUDIO}, 0);
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
                ScanMusicUtil.getMusic(this);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void init() {
        local = findViewById(R.id.local);
        recent = findViewById(R.id.recent);
        like = findViewById(R.id.like);

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
    }

}