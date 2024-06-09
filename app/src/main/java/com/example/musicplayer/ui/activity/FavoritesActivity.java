package com.example.musicplayer.ui.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.musicplayer.R;
import com.example.musicplayer.data.model.Music;
import com.example.musicplayer.data.repository.FavoriteMusicRepository;
import com.example.musicplayer.ui.fragment.SingleSongFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 收藏
 */
public class FavoritesActivity extends AppCompatActivity {
    private FavoriteMusicRepository favoriteMusicRepository = new FavoriteMusicRepository(this);
    private List<Music> favoriteMusic = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        favoriteMusic = favoriteMusicRepository.getAllFavoriteMusic();

        // 获取 FragmentManager
        FragmentManager fragmentManager = getSupportFragmentManager();
        // 开始 FragmentTransaction
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // 创建并添加 Fragment 实例
        SingleSongFragment myFragment = new SingleSongFragment(favoriteMusic);
        fragmentTransaction.add(R.id.main, myFragment);
        // 提交事务
        fragmentTransaction.commit();
    }
}