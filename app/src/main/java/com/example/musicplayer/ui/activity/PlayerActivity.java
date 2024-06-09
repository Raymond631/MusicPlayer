package com.example.musicplayer.ui.activity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.musicplayer.App;
import com.example.musicplayer.R;
import com.example.musicplayer.data.model.Music;
import com.example.musicplayer.service.MusicService;
import com.example.musicplayer.ui.view.CircleImageView;
import com.example.musicplayer.utils.TimeUtil;

/**
 * 播放界面
 */
public class PlayerActivity extends AppCompatActivity {
    private ImageView btn_play, btn_prev, btn_next, btn_status;
    private CircleImageView cover;
    private SeekBar seekBar;
    private Handler handler;
    private Runnable runnable;
    private ObjectAnimator rotationAnimator;
    private TextView start, end, title;

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
            }
        } else {
            MusicService musicService = App.getService();
            musicService.setData(musicService.getPosition());
            App.getService().playOrPause();
            rotationAnimator.start();
        }
    }

    private void init() {
        //获取控件
        title = findViewById(R.id.title);
        cover = findViewById(R.id.cover);

        start = findViewById(R.id.start);
        seekBar = findViewById(R.id.seekBar);
        end = findViewById(R.id.end);

        btn_play = findViewById(R.id.btn_play);
        btn_prev = findViewById(R.id.btn_prev);
        btn_next = findViewById(R.id.btn_next);
        btn_status = findViewById(R.id.btn_status);

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
    }


    private void setMusic(Music music) {
        //设置音乐名
        title.setText(music.getTitle());
        cover.setImageResource(R.drawable.music);
        //显示音乐进度条时长
        end.setText(music.getTime());

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
