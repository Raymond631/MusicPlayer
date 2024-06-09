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

import java.util.ArrayList;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class MusicRepositoryTest {
    private MusicRepository repository;
    private Context context;

    @Before
    public void setUp() {
        context = ApplicationProvider.getApplicationContext();
        repository = new MusicRepository(context);

        // Clear the database before each test
        repository.onUpgrade(repository.getWritableDatabase(), 1, 1);
    }

    @After
    public void tearDown() {
        repository.close();
    }

    // 辅助方法，返回一些测试Music对象的列表
    private List<Music> getTestMusicData() {
        // 创建测试数据
        List<Music> testData = new ArrayList<>();
        testData.add(new Music(1, "Song 1", "Artist 1", "Album 1", "/path/to/song1", 300000, "5:00"));
        testData.add(new Music(2, "Song 2", "Artist 2", "Album 2", "/path/to/song2", 240000, "4:00"));
        testData.add(new Music(3, "Song 3", "Artist 3", "Album 3", "/path/to/song3", 180000, "3:00"));
        return testData;
    }

    @Test
    public void testAddAll() {
        // 添加一些测试数据到数据库
        List<Music> testData = getTestMusicData();
        repository.addAll(testData);

        // 从数据库中获取数据
        List<Music> result = repository.getAll();

        // 检查结果是否不为空
        assertNotNull(result);

        // 检查结果数量是否正确
        assertEquals(testData.size(), result.size());

        // 检查每个Music对象的字段是否正确
        for (int i = 0; i < testData.size(); i++) {
            Music expected = testData.get(i);
            Music actual = result.get(i);
            assertEquals(expected.getId(), actual.getId());
            assertEquals(expected.getTitle(), actual.getTitle());
            assertEquals(expected.getArtist(), actual.getArtist());
            assertEquals(expected.getAlbum(), actual.getAlbum());
            assertEquals(expected.getData(), actual.getData());
            assertEquals(expected.getDuration(), actual.getDuration());
            assertEquals(expected.getTime(), actual.getTime());
        }
    }

    @Test
    public void testGetAll() {
        // 添加一些测试数据到数据库
        List<Music> testData = getTestMusicData();
        repository.addAll(testData);

        // 从数据库中获取数据
        List<Music> result = repository.getAll();

        // 检查结果是否不为空
        assertNotNull(result);

        // 检查结果数量是否正确
        assertEquals(testData.size(), result.size());

        // 检查每个Music对象的字段是否正确
        for (int i = 0; i < testData.size(); i++) {
            Music expected = testData.get(i);
            Music actual = result.get(i);
            assertEquals(expected.getId(), actual.getId());
            assertEquals(expected.getTitle(), actual.getTitle());
            assertEquals(expected.getArtist(), actual.getArtist());
            assertEquals(expected.getAlbum(), actual.getAlbum());
            assertEquals(expected.getData(), actual.getData());
            assertEquals(expected.getDuration(), actual.getDuration());
            assertEquals(expected.getTime(), actual.getTime());
        }
    }
}
