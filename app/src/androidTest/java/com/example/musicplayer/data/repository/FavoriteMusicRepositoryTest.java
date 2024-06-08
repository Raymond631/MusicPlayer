package com.example.musicplayer.data.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.musicplayer.data.model.Music;

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
        Music music = new Music(1L, "Test Title", "Test Artist", "Test Album", 1L, "/test/path");
        repository.insertFavoriteMusic(music);

        List<Music> favoriteMusics = repository.getAllFavoriteMusic();
        assertNotNull(favoriteMusics);
        assertEquals(1, favoriteMusics.size());

        Music retrievedMusic = favoriteMusics.get(0);
        assertEquals(1L, retrievedMusic.getId());
        assertEquals("Test Title", retrievedMusic.getTitle());
        assertEquals("Test Artist", retrievedMusic.getArtist());
        assertEquals("Test Album", retrievedMusic.getAlbum());
        assertEquals(1L, retrievedMusic.getDuration());
        assertEquals("/test/path", retrievedMusic.getData());
    }

    @Test
    public void testGetAllFavoriteMusic() {
        Music music1 = new Music(1L, "Title 1", "Artist 1", "Album 1", 1L, "/path/1");
        Music music2 = new Music(2L, "Title 2", "Artist 2", "Album 2", 2L, "/path/2");

        repository.insertFavoriteMusic(music1);
        repository.insertFavoriteMusic(music2);

        List<Music> favoriteMusics = repository.getAllFavoriteMusic();
        assertNotNull(favoriteMusics);
        assertEquals(2, favoriteMusics.size());

        Music retrievedMusic1 = favoriteMusics.get(0);
        assertEquals(1L, retrievedMusic1.getId());
        assertEquals("Title 1", retrievedMusic1.getTitle());
        assertEquals("Artist 1", retrievedMusic1.getArtist());
        assertEquals("Album 1", retrievedMusic1.getAlbum());
        assertEquals(1L, retrievedMusic1.getDuration());
        assertEquals("/path/1", retrievedMusic1.getData());

        Music retrievedMusic2 = favoriteMusics.get(1);
        assertEquals(2L, retrievedMusic2.getId());
        assertEquals("Title 2", retrievedMusic2.getTitle());
        assertEquals("Artist 2", retrievedMusic2.getArtist());
        assertEquals("Album 2", retrievedMusic2.getAlbum());
        assertEquals(2L, retrievedMusic2.getDuration());
        assertEquals("/path/2", retrievedMusic2.getData());
    }

    @Test
    public void testCancelFavoriteMusic() {
        Music music = new Music(1L, "Test Title", "Test Artist", "Test Album", 1L, "/test/path");
        repository.insertFavoriteMusic(music);

        List<Music> favoriteMusics = repository.getAllFavoriteMusic();
        assertNotNull(favoriteMusics);
        assertEquals(1, favoriteMusics.size());

        Music retrievedMusic = favoriteMusics.get(0);
        assertEquals(1L, retrievedMusic.getId());
        assertEquals("Test Title", retrievedMusic.getTitle());
        assertEquals("Test Artist", retrievedMusic.getArtist());
        assertEquals("Test Album", retrievedMusic.getAlbum());
        assertEquals(1L, retrievedMusic.getDuration());
        assertEquals("/test/path", retrievedMusic.getData());

        repository.cancelFavoriteMusic(retrievedMusic);
        favoriteMusics = repository.getAllFavoriteMusic();
        assertEquals(0, favoriteMusics.size());
    }
}