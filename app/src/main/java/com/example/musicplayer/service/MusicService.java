package com.example.musicplayer.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

import com.example.musicplayer.R;
import com.example.musicplayer.data.model.Music;
import com.example.musicplayer.data.repository.RecentMusicRepository;
import com.example.musicplayer.receiver.NotificationReceiver;
import com.example.musicplayer.ui.activity.MainActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MusicService extends Service implements MediaPlayer.OnCompletionListener {
    private static final String CHANNEL_ID = "MusicPlayerChannel";

    private final IBinder binder = new MusicBinder();
    private MediaPlayer mediaPlayer;
    private List<Music> musicList = new ArrayList<>();
    private int position;
    private Music music;
    private int status = 0;  // 顺序、随机、单曲循环

    private RecentMusicRepository recentMusicRepository = new RecentMusicRepository(this);

    private Random random = new Random();
    private OnStatusChangeListener onStatusChangeListener;

    @Override
    public void onCreate() {
        super.onCreate();
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setOnCompletionListener(this);
        }
        createNotificationChannel();
        startForeground(1, getNotification());
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (status == 0) {
            next();
        } else if (status == 1) {
            randomPlay();
        } else if (status == 2) {
            loop();
        }
    }

    //上一首
    public void pre() {
        if (status == 1) {
            randomPlay();
        } else {
            position = (position - 1) % musicList.size();
            if (position < 0) {
                position = musicList.size() - 1;
            }
            Music music = musicList.get(position);
            if (music != null) {
                setMusic(music);
                playOrPause();
            }
        }
    }

    //下一首
    public void next() {
        if (status == 1) {
            randomPlay();
        } else {
            position = (position + 1) % (musicList.size());
            Music music = musicList.get(position);
            if (music != null) {
                setMusic(music);
                playOrPause();
            }
        }
    }

    //随机播放
    public void randomPlay() {
        position = random.nextInt(musicList.size());
        setData(position);
        playOrPause();
    }

    //单曲循环
    public void loop() {
        setData(position);
        playOrPause();
    }

    //播放
    public void playOrPause() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        } else {
            recentMusicRepository.insertRecentMusic(getNowPlay());
            mediaPlayer.start();
        }
        updateNotification();
    }

    //进度改变
    public void seekTo(int i) {
        mediaPlayer.seekTo(i);
    }

    //获取当前的播放进度
    public int getNow() {
        if (mediaPlayer.isPlaying()) {
            return mediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    //设置音乐
    public void setMusic(Music music) {
        if (music != null) {
            this.music = music;
            if (onStatusChangeListener != null) {
                onStatusChangeListener.onChange(music);
            }
            mediaPlayer.reset();
            try {
                mediaPlayer.setDataSource(music.getData());
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //判断是否正在播放
    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    public Music getMusic(int position) {
        if (position >= 0 && position < musicList.size()) {
            return musicList.get(position);
        }
        return null;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setData(int position) {
        this.position = position;
        if (position >= 0 && position < musicList.size()) {
            setMusic(musicList.get(position));
        }
    }

    public void setMusicList(List<Music> musicList) {
        this.musicList = musicList;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Music getNowPlay() {
        return music;
    }

    public void setOnStatusChangeListener(OnStatusChangeListener onStatusChangeListener) {
        this.onStatusChangeListener = onStatusChangeListener;
    }

    // 通知
    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Music Player";
            String description = "Channel for Music Player";
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private Notification getNotification() {
        RemoteViews notificationLayout = new RemoteViews(getPackageName(), R.layout.notification);

        Intent previousIntent = new Intent(this, NotificationReceiver.class).setAction(NotificationReceiver.ACTION_PREVIOUS);
        PendingIntent previousPendingIntent = PendingIntent.getBroadcast(this, 0, previousIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        notificationLayout.setOnClickPendingIntent(R.id.btn_notification_previous, previousPendingIntent);

        Intent playPauseIntent = new Intent(this, NotificationReceiver.class).setAction(NotificationReceiver.ACTION_PLAY_PAUSE);
        PendingIntent playPausePendingIntent = PendingIntent.getBroadcast(this, 0, playPauseIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        notificationLayout.setOnClickPendingIntent(R.id.btn_notification_play, playPausePendingIntent);

        Intent nextIntent = new Intent(this, NotificationReceiver.class).setAction(NotificationReceiver.ACTION_NEXT);
        PendingIntent nextPendingIntent = PendingIntent.getBroadcast(this, 0, nextIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        notificationLayout.setOnClickPendingIntent(R.id.btn_notification_next, nextPendingIntent);

        // 动态更新歌曲名和歌手名
        Music nowPlaying = getNowPlay();
        if (nowPlaying != null) {
            notificationLayout.setTextViewText(R.id.tv_notification_song_name, nowPlaying.getTitle());
            notificationLayout.setTextViewText(R.id.tv_notification_singer, nowPlaying.getArtist());
        }

        if (isPlaying()) {
            notificationLayout.setImageViewResource(R.id.btn_notification_play, R.mipmap.pause);
        } else {
            notificationLayout.setImageViewResource(R.id.btn_notification_play, R.mipmap.play);
        }

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Music Player")
                .setContentText("Playing music")
                .setContentIntent(pendingIntent)
                .setCustomContentView(notificationLayout)
                .setPriority(NotificationCompat.PRIORITY_LOW);

        return builder.build();
    }


    private void updateNotification() {
        Notification notification = getNotification();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
    }

    public interface OnStatusChangeListener {
        void onChange(Music music);
    }

    public class MusicBinder extends Binder {
        //返回Service对象
        public MusicService getService() {
            return MusicService.this;
        }
    }
}