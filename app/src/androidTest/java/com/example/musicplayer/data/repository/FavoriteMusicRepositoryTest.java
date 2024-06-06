package com.example.musicplayer.data.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.musicplayer.data.model.FavoriteMusic;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class FavoriteMusicRepositoryTest {
    private FavoriteMusicRepository repository;
    private Context context;

    @Before
    public void setUp() {
        context = ApplicationProvider.getApplicationContext();
        repository = new FavoriteMusicRepository(context);

        // Clear the database before each test
        repository.onUpgrade(repository.getWritableDatabase(), 1, 1);
    }

    @After
    public void tearDown() {
        repository.close();
    }

    @Test
    public void testInsertFavoriteMusic() {
        FavoriteMusic music = new FavoriteMusic(0, "Test Title", "Test Artist", "Test Album", "/test/path");
        repository.insertFavoriteMusic(music);

        List<FavoriteMusic> favoriteMusics = repository.getAllFavoriteMusic();
        assertNotNull(favoriteMusics);
        assertEquals(1, favoriteMusics.size());

        FavoriteMusic retrievedMusic = favoriteMusics.get(0);
        assertEquals("Test Title", retrievedMusic.getTitle());
        assertEquals("Test Artist", retrievedMusic.getArtist());
        assertEquals("Test Album", retrievedMusic.getAlbum());
        assertEquals("/test/path", retrievedMusic.getPath());
    }

    @Test
    public void testGetAllFavoriteMusic() {
        FavoriteMusic music1 = new FavoriteMusic(0, "Title 1", "Artist 1", "Album 1", "/path/1");
        FavoriteMusic music2 = new FavoriteMusic(0, "Title 2", "Artist 2", "Album 2", "/path/2");

        repository.insertFavoriteMusic(music1);
        repository.insertFavoriteMusic(music2);

        List<FavoriteMusic> favoriteMusics = repository.getAllFavoriteMusic();
        assertNotNull(favoriteMusics);
        assertEquals(2, favoriteMusics.size());

        FavoriteMusic retrievedMusic1 = favoriteMusics.get(0);
        assertEquals("Title 1", retrievedMusic1.getTitle());
        assertEquals("Artist 1", retrievedMusic1.getArtist());
        assertEquals("Album 1", retrievedMusic1.getAlbum());
        assertEquals("/path/1", retrievedMusic1.getPath());

        FavoriteMusic retrievedMusic2 = favoriteMusics.get(1);
        assertEquals("Title 2", retrievedMusic2.getTitle());
        assertEquals("Artist 2", retrievedMusic2.getArtist());
        assertEquals("Album 2", retrievedMusic2.getAlbum());
        assertEquals("/path/2", retrievedMusic2.getPath());
    }
}
