package com.example.musicplayer.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.musicplayer.R;
import com.example.musicplayer.data.model.Item;

import java.util.List;

public class ItemAdapter extends BaseAdapter {
    Context context;
    List<Item> itemList;

    public ItemAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Item getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_other, null);
            viewHolder.id = convertView.findViewById(R.id.id);
            viewHolder.name = convertView.findViewById(R.id.name);
            viewHolder.number = convertView.findViewById(R.id.number);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.id.setText(String.valueOf(position + 1));

        Item item = itemList.get(position);
        viewHolder.name.setText(item.getName());
        viewHolder.number.setText(String.valueOf(item.getMusicList().size()));

        return convertView;
    }

    public static class ViewHolder {
        TextView id, name, number;
    }
}