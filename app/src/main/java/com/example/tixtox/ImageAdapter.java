package com.example.tixtox;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class ImageAdapter extends ArrayAdapter<String> {
    Context context;
    ArrayList<String> arrayList;

    public ImageAdapter(Context context, ArrayList<String> arrayList) {
        super(context, R.layout.image_view, arrayList);
        this.context = context;
        this.arrayList = arrayList;
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }


    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.image_view, parent, false);
        }

        ImageView imageView;
        imageView = (ImageView) convertView.findViewById(R.id.image);
        Glide.with(imageView.getContext())
                .load(arrayList.get(position).replace("http", "https"))
                .error(R.drawable.icon_home)
                .into((ImageView) imageView);

        return convertView;
    }
}
