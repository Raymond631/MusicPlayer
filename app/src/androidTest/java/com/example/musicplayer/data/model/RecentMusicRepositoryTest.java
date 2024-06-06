package com.example.musicplayer.data.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.musicplayer.data.repository.RecentMusicRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class RecentMusicRepositoryTest {
    private RecentMusicRepository repository;
    private Context context;

    @Before
    public void setUp() {
        context = ApplicationProvider.getApplicationContext();
        repository = new RecentMusicRepository(context);

        // Clear the database before each test
        repository.onUpgrade(repository.getWritableDatabase(), 1, 1);
    }

    @After
    public void tearDown() {
        repository.close();
    }

    @Test
    public void testInsertRecentMusic() {
        RecentMusic music = new RecentMusic(0, "Test Title", "Test Artist", "Test Album", "/test/path");
        repository.insertRecentMusic(music);

        List<RecentMusic> recentMusics = repository.getAllRecentMusic();
        assertNotNull(recentMusics);
        assertEquals(1, recentMusics.size());

        RecentMusic retrievedMusic = recentMusics.get(0);
        assertEquals("Test Title", retrievedMusic.getTitle());
        assertEquals("Test Artist", retrievedMusic.getArtist());
        assertEquals("Test Album", retrievedMusic.getAlbum());
        assertEquals("/test/path", retrievedMusic.getPath());
    }

    @Test
    public void testGetAllRecentMusic() {
        RecentMusic music1 = new RecentMusic(0, "Title 1", "Artist 1", "Album 1", "/path/1");
        RecentMusic music2 = new RecentMusic(0, "Title 2", "Artist 2", "Album 2", "/path/2");

        repository.insertRecentMusic(music1);
        repository.insertRecentMusic(music2);

        List<RecentMusic> recentMusics = repository.getAllRecentMusic();
        assertNotNull(recentMusics);
        assertEquals(2, recentMusics.size());

        RecentMusic retrievedMusic1 = recentMusics.get(0);
        assertEquals("Title 1", retrievedMusic1.getTitle());
        assertEquals("Artist 1", retrievedMusic1.getArtist());
        assertEquals("Album 1", retrievedMusic1.getAlbum());
        assertEquals("/path/1", retrievedMusic1.getPath());

        RecentMusic retrievedMusic2 = recentMusics.get(1);
        assertEquals("Title 2", retrievedMusic2.getTitle());
        assertEquals("Artist 2", retrievedMusic2.getArtist());
        assertEquals("Album 2", retrievedMusic2.getAlbum());
        assertEquals("/path/2", retrievedMusic2.getPath());
    }
}