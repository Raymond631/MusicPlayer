package com.example.musicplayer.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayer.R;
import com.example.musicplayer.data.model.Music;

import java.util.List;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ViewHolder> {
    private Context context;
    private List<Music> musicList;

    private OnItemClickListener onItemClickListener;

    public MusicAdapter(Context context, List<Music> musicList, OnItemClickListener listener) {
        this.context = context;
        this.musicList = musicList;
        this.onItemClickListener = listener;
    }

    public void setMusicList(List<Music> musicList) {
        this.musicList = musicList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_music, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Music music = musicList.get(position);
        holder.bind(music, position);
    }

    @Override
    public int getItemCount() {
        return musicList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView number, title, artist, duration;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            number = itemView.findViewById(R.id.number);
            title = itemView.findViewById(R.id.title);
            artist = itemView.findViewById(R.id.artist);
            duration = itemView.findViewById(R.id.duration);

            itemView.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(getAdapterPosition());
                }
            });
        }

        public void bind(Music music, int position) {
            number.setText(String.valueOf(position + 1));
            title.setText(music.getTitle());
            artist.setText(music.getArtist());
            duration.setText(music.getTime());
        }
    }
}

