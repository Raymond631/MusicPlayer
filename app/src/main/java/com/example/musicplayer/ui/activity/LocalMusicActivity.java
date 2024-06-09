package com.example.musicplayer.ui.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.musicplayer.R;
import com.example.musicplayer.ui.adapter.LocalMusicFragmentAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

/**
 * 本地
 */
public class LocalMusicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_music);
        init();
    }

    private void init() {
        ViewPager2 viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new LocalMusicFragmentAdapter(this));

        // 将TabLayout和ViewPager关联起来，并设置选项卡的文本
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("单曲");
                    break;
                case 1:
                    tab.setText("专辑");
                    break;
                case 2:
                    tab.setText("歌手");
                    break;
                case 3:
                    tab.setText("文件夹");
                    break;
                default:
                    break;
            }
        }).attach();
    }
}