package com.example.musicplayer.ui.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.musicplayer.R;

/**
 * 播放界面
 */
public class PlayerActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
    }
}
