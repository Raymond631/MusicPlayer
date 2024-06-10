package com.example.musicplayer.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayer.R;
import com.example.musicplayer.data.model.Item;
import com.example.musicplayer.ui.activity.ItemActivity;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private Context context;
    private List<Item> itemList;
    private OnItemLongClickListener longClickListener;

    public ItemAdapter(Context context, List<Item> itemList, OnItemLongClickListener longClickListener) {
        this.context = context;
        this.itemList = itemList;
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_other, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView id, name, number;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id);
            name = itemView.findViewById(R.id.name);
            number = itemView.findViewById(R.id.number);
        }

        public void bind(Item item) {
            id.setText(String.valueOf(getAdapterPosition() + 1));
            name.setText(item.getName());
            number.setText(String.valueOf(item.getMusicList().size()));

            itemView.setOnClickListener(view -> {
                if (!itemList.isEmpty()) {
                    Intent intent = new Intent(context, ItemActivity.class);
                    intent.putExtra("data", item);
                    context.startActivity(intent);
                }
            });

            itemView.setOnLongClickListener(view -> {
                if (longClickListener != null) {
                    longClickListener.onItemLongClick(getAdapterPosition());
                    return true;
                }
                return false;
            });
        }
    }
}
