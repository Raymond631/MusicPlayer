package com.example.musicplayer.data.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.musicplayer.data.model.Music;
import com.example.musicplayer.data.model.MusicList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class MusicListRepositoryTest {
    private MusicListRepository repository;
    private Context context;

    @Before
    public void setUp() {
        context = ApplicationProvider.getApplicationContext();
        repository = new MusicListRepository(context);

        // Clear the database before each test
        repository.onUpgrade(repository.getWritableDatabase(), 1, 1);
    }

    @After
    public void tearDown() {
        repository.close();
    }

    @Test
    public void testCreateList() {
        MusicList musicList = new MusicList("Test Name");
        repository.createList(musicList);

        List<MusicList> musicLists = repository.getMusicLists();
        assertNotNull(musicLists);
        assertEquals(1, musicLists.size());

        MusicList retrievedMusicList = musicLists.get(0);
        assertEquals("Test Name", retrievedMusicList.getName());
    }

    @Test
    public void testDeleteList() {
        // 添加歌单
        MusicList musicList = new MusicList("Test Name");
        repository.createList(musicList);
        List<MusicList> musicLists = repository.getMusicLists();
        assertNotNull(musicLists);
        assertEquals(1, musicLists.size());
        MusicList retrievedMusicList = musicLists.get(0);
        assertEquals("Test Name", retrievedMusicList.getName());

        // 删除歌单
        repository.deleteList(retrievedMusicList);
        musicLists = repository.getMusicLists();
        assertEquals(0, musicLists.size());
    }

    @Test
    public void testAddMusic() {
        // 添加歌单
        MusicList musicList = new MusicList("Test Name");
        repository.createList(musicList);
        List<MusicList> musicLists = repository.getMusicLists();
        assertNotNull(musicLists);
        assertEquals(1, musicLists.size());
        MusicList retrievedMusicList = musicLists.get(0);
        assertEquals("Test Name", retrievedMusicList.getName());

        // 添加歌曲
        Music music = new Music("Test Title", "Test Artist", "Test Album", "/test/path");
        repository.addMusic(retrievedMusicList, music);

        List<Music> list = repository.getMusicByList(retrievedMusicList);
        assertNotNull(list);
        assertEquals(1, list.size());

        Music retrievedMusic = list.get(0);
        assertEquals("Test Title", retrievedMusic.getTitle());
        assertEquals("Test Artist", retrievedMusic.getArtist());
        assertEquals("Test Album", retrievedMusic.getAlbum());
        assertEquals("/test/path", retrievedMusic.getPath());
    }

    @Test
    public void testDeleteMusic() {
        // 添加歌单
        MusicList musicList = new MusicList("Test Name");
        repository.createList(musicList);
        List<MusicList> musicLists = repository.getMusicLists();
        assertNotNull(musicLists);
        assertEquals(1, musicLists.size());
        MusicList retrievedMusicList = musicLists.get(0);
        assertEquals("Test Name", retrievedMusicList.getName());

        // 添加歌曲
        Music music = new Music("Test Title", "Test Artist", "Test Album", "/test/path");
        repository.addMusic(retrievedMusicList, music);
        List<Music> list = repository.getMusicByList(retrievedMusicList);
        assertNotNull(list);
        assertEquals(1, list.size());
        Music retrievedMusic = list.get(0);
        assertEquals("Test Title", retrievedMusic.getTitle());
        assertEquals("Test Artist", retrievedMusic.getArtist());
        assertEquals("Test Album", retrievedMusic.getAlbum());
        assertEquals("/test/path", retrievedMusic.getPath());

        // 删除歌曲
        repository.deleteMusic(retrievedMusicList, retrievedMusic);
        list = repository.getMusicByList(retrievedMusicList);
        assertEquals(0, list.size());
    }

    @Test
    public void testGetMusicLists() {
        MusicList musicList1 = new MusicList("Test Name 1");
        MusicList musicList2 = new MusicList("Test Name 2");

        repository.createList(musicList1);
        repository.createList(musicList2);

        List<MusicList> musicLists = repository.getMusicLists();
        assertNotNull(musicLists);
        assertEquals(2, musicLists.size());

        MusicList retrievedMusicList1 = musicLists.get(0);
        assertEquals("Test Name 1", retrievedMusicList1.getName());
        MusicList retrievedMusicList2 = musicLists.get(1);
        assertEquals("Test Name 2", retrievedMusicList2.getName());
    }

    @Test
    public void testGetMusicByList() {
        // 添加歌单
        MusicList musicList = new MusicList("Test Name");
        repository.createList(musicList);
        List<MusicList> musicLists = repository.getMusicLists();
        assertNotNull(musicLists);
        assertEquals(1, musicLists.size());
        MusicList retrievedMusicList = musicLists.get(0);
        assertEquals("Test Name", retrievedMusicList.getName());

        // 添加歌曲
        Music music1 = new Music("Title 1", "Artist 1", "Album 1", "/path/1");
        Music music2 = new Music("Title 2", "Artist 2", "Album 2", "/path/2");
        repository.addMusic(retrievedMusicList, music1);
        repository.addMusic(retrievedMusicList, music2);

        List<Music> list = repository.getMusicByList(retrievedMusicList);
        assertNotNull(list);
        assertEquals(2, list.size());

        Music retrievedMusic1 = list.get(0);
        assertEquals("Title 1", retrievedMusic1.getTitle());
        assertEquals("Artist 1", retrievedMusic1.getArtist());
        assertEquals("Album 1", retrievedMusic1.getAlbum());
        assertEquals("/path/1", retrievedMusic1.getPath());

        Music retrievedMusic2 = list.get(1);
        assertEquals("Title 2", retrievedMusic2.getTitle());
        assertEquals("Artist 2", retrievedMusic2.getArtist());
        assertEquals("Album 2", retrievedMusic2.getAlbum());
        assertEquals("/path/2", retrievedMusic2.getPath());
    }
}
