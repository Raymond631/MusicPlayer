package com.example.musicplayer.ui.activity;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.musicplayer.App;
import com.example.musicplayer.R;
import com.example.musicplayer.data.model.Music;
import com.example.musicplayer.data.model.MusicList;
import com.example.musicplayer.data.repository.FavoriteMusicRepository;
import com.example.musicplayer.data.repository.MusicListRepository;
import com.example.musicplayer.service.MusicService;
import com.example.musicplayer.ui.view.CircleImageView;
import com.example.musicplayer.utils.TimeUtil;

import java.util.List;

/**
 * 播放界面
 */
public class PlayerActivity extends AppCompatActivity {
    private Handler handler;
    private Runnable runnable;
    private TextView id, title, artist, album;
    private CircleImageView cover;
    private ObjectAnimator rotationAnimator;
    private TextView start, end;
    private SeekBar seekBar;
    private ImageView btn_play, btn_prev, btn_next, btn_status, btn_love;
    private Button addToList;


    private FavoriteMusicRepository favoriteMusicRepository = new FavoriteMusicRepository(this);
    private MusicListRepository musicListRepository = new MusicListRepository(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        btn_play.setImageResource(R.drawable.ic_play_btn_pause);
        Music music = App.getService().getMusic(App.getService().getPosition());
        if (App.getService().isPlaying()) {
            if (music != App.getService().getNowPlay()) {
                App.getService().setData(App.getService().getPosition());
                App.getService().playOrPause();
                rotationAnimator.start();
            } else {
                setMusic(music);
                rotationAnimator.start();
            }
        } else {
            MusicService musicService = App.getService();
            musicService.setData(musicService.getPosition());
            App.getService().playOrPause();
            rotationAnimator.start();
        }

        if (favoriteMusicRepository.isFavorite(App.getService().getNowPlay())) {
            btn_love.setImageResource(R.drawable.love_red);
        } else {
            btn_love.setImageResource(R.drawable.love);
        }
    }

    private void init() {
        //获取控件
        id = findViewById(R.id.id);
        title = findViewById(R.id.title);
        artist = findViewById(R.id.artist);
        album = findViewById(R.id.album);

        cover = findViewById(R.id.cover);

        start = findViewById(R.id.start);
        seekBar = findViewById(R.id.seekBar);
        end = findViewById(R.id.end);

        btn_play = findViewById(R.id.btn_play);
        btn_prev = findViewById(R.id.btn_prev);
        btn_next = findViewById(R.id.btn_next);
        btn_status = findViewById(R.id.btn_status);
        btn_love = findViewById(R.id.btn_love);

        addToList = findViewById(R.id.addToListButton);


        //动画设置
        rotationAnimator = ObjectAnimator.ofFloat(cover, "rotation", 0f, 360f);
        rotationAnimator.setDuration(5000); // 设置旋转一周的时间
        rotationAnimator.setInterpolator(new LinearInterpolator());
        rotationAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        handler = new Handler();

        //设置进度条
        updateSeekBar();
        //设置音乐
        App.getService().setOnStatusChangeListener(this::setMusic);

        //播放
        btn_play.setOnClickListener(v -> {
            if (App.getService().isPlaying()) {
                rotationAnimator.pause();
                btn_play.setImageResource(R.drawable.ic_play_btn_play);
            } else {
                if (rotationAnimator.isStarted()) {
                    rotationAnimator.resume();
                } else {
                    rotationAnimator.start();
                }

                btn_play.setImageResource(R.drawable.ic_play_btn_pause);
            }
            App.getService().playOrPause();
        });
        //上一首
        btn_prev.setOnClickListener(v -> App.getService().pre());
        //下一首
        btn_next.setOnClickListener(v -> App.getService().next());
        //改变状态
        btn_status.setOnClickListener(v -> {
            int status = (App.getService().getStatus() + 1) % 3;
            App.getService().setStatus(status);
            if (status == 0) {
                btn_status.setImageResource(R.drawable.ic_play_btn_loop);
                Toast.makeText(PlayerActivity.this, R.string.list_loops, Toast.LENGTH_SHORT).show();
            } else if (status == 1) {
                btn_status.setImageResource(R.drawable.ic_play_btn_shuffle);
                Toast.makeText(PlayerActivity.this, R.string.shuffle, Toast.LENGTH_SHORT).show();
            } else if (status == 2) {
                btn_status.setImageResource(R.drawable.ic_play_btn_one);
                Toast.makeText(PlayerActivity.this, R.string.single_loop, Toast.LENGTH_SHORT).show();
            }
        });
        // 收藏
        btn_love.setOnClickListener(v -> {
            Music music = App.getService().getNowPlay();
            if (favoriteMusicRepository.isFavorite(music)) {
                favoriteMusicRepository.cancelFavoriteMusic(music);
                btn_love.setImageResource(R.drawable.love);
            } else {
                favoriteMusicRepository.insertFavoriteMusic(music);
                btn_love.setImageResource(R.drawable.love_red);
            }
        });
        // 加入歌单
        addToList.setOnClickListener(v -> {
            Music music = App.getService().getNowPlay();
            List<MusicList> musicLists = musicListRepository.getMusicLists();
            String[] options = musicLists.stream().map(MusicList::getName).toArray(String[]::new);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("选择歌单");
            builder.setItems(options, (dialog, which) -> {
                musicListRepository.addMusic(musicLists.get(which), music);
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        });
    }


    private void setMusic(Music music) {
        //设置音乐名
        id.setText(String.valueOf(music.getId()));
        title.setText(music.getTitle());
        artist.setText(music.getArtist());
        album.setText(music.getAlbum());
        cover.setImageResource(R.drawable.music);
        //显示音乐进度条时长
        end.setText(music.getTime());

        if (favoriteMusicRepository.isFavorite(music)) {
            btn_love.setImageResource(R.drawable.love);
        } else {
            btn_love.setImageResource(R.drawable.love_red);
        }

        //设置进度条总时长
        seekBar.setMax(music.getDuration() / 1000);
        //进度条拖动监听
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser && App.getService().isPlaying()) {
                    App.getService().seekTo(progress * 1000);
                    start.setText(String.format(getString(R.string.s_s), TimeUtil.format(progress / 60), TimeUtil.format(progress % 60)));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // 不需要处理
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (!App.getService().isPlaying()) {
                    start.setText(R.string._00_00);
                    seekBar.setProgress(0);
                }
            }
        });
    }

    //更新进度条
    private void updateSeekBar() {
        runnable = new Runnable() {
            @Override
            public void run() {
                if (App.getService() != null && App.getService().isPlaying()) {
                    rotationAnimator.resume();
                    btn_play.setImageResource(R.drawable.ic_play_btn_pause);
                    int currentPosition = App.getService().getNow();
                    seekBar.setProgress(currentPosition / 1000);
                    start.setText(String.format(getString(R.string.s_s), TimeUtil.format(currentPosition / 1000 / 60), TimeUtil.format(currentPosition / 1000 % 60)));
                } else {
                    rotationAnimator.pause();
                    btn_play.setImageResource(R.drawable.ic_play_btn_play);
                }
                handler.postDelayed(this, 1000); // 每秒更新一次进度条
            }
        };
        handler.postDelayed(runnable, 0);
    }
}
