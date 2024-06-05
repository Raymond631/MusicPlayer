package com.example.musicplayer.ui.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.musicplayer.R;
import com.example.musicplayer.ui.fragment.HomeFragment;
import com.example.musicplayer.ui.fragment.RecentFragment;
import com.example.musicplayer.ui.fragment.FavoritesFragment;
import com.example.musicplayer.ui.fragment.PlaylistFragment;
import com.example.musicplayer.ui.viewmodel.MusicViewModel;
import com.example.musicplayer.ui.viewmodel.ViewModelFactory;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}