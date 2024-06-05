package com.example.musicplayer.ui.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.musicplayer.ui.fragment.AlbumFragment;
import com.example.musicplayer.ui.fragment.FolderFragment;
import com.example.musicplayer.ui.fragment.SingerFragment;
import com.example.musicplayer.ui.fragment.SingleSongFragment;

public class LocalMusicFragmentAdapter extends FragmentStateAdapter {
    private static final int NUM_PAGES = 4; // 两个选项卡

    public LocalMusicFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // 根据位置返回对应的 Fragment
        switch (position) {
            case 0:
                return new SingleSongFragment(); // 单曲 Fragment
            case 1:
                return new AlbumFragment(); // 专辑 Fragment
            case 2:
                return new SingerFragment(); // 歌手 Fragment
            case 3:
                return new FolderFragment(); // 文件夹 Fragment
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return NUM_PAGES;
    }
}
