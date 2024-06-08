package com.example.musicplayer.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.musicplayer.R;

/**
 * 首页
 */
public class MainActivity extends AppCompatActivity {

    ImageButton local,recent,like;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        local = findViewById(R.id.local);
        recent = findViewById(R.id.recent);
        like = findViewById(R.id.like);

        local.setOnClickListener(v -> {
            // TODO Auto-generated method stub
            Intent intent=new Intent(MainActivity.this, LocalMusicActivity.class);
            startActivity(intent);
        });

        recent.setOnClickListener(v -> {
            // TODO Auto-generated method stub
            Intent intent=new Intent(MainActivity.this, RecentActivity.class);
            startActivity(intent);
        });

        like.setOnClickListener(v -> {
            // TODO Auto-generated method stub
            Intent intent=new Intent(MainActivity.this, FavoritesActivity.class);
            startActivity(intent);
        });
    }
}