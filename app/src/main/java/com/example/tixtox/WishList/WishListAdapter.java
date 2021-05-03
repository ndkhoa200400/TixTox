package com.example.tixtox.WishList;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tixtox.ImageAdapter;
import com.example.tixtox.R;

import java.util.ArrayList;

import static android.content.Intent.getIntent;

public class WishListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<ItemWishList> listFilm ;
    private int idLayout;

    WishListAdapter(Context context, int idLayout, ArrayList<ItemWishList> listFilm){
        this.context = context;
        this.idLayout = idLayout;
        this.listFilm = listFilm;
    }


    @Override
    public int getCount() {

        return listFilm.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(idLayout, parent, false);
        }
        TextView filmName = convertView.findViewById(R.id.itemName);
        ImageView imageFilm = convertView.findViewById(R.id.imageFilm);
        filmName.setText(listFilm.get(position).getFilmName());
        Glide.with(imageFilm.getContext())
                .load(listFilm.get(position).getImageFilm())
                .error(R.drawable.icon_home)
                .into((ImageView) imageFilm);
        return convertView;
    }
}
class ItemWishList {
    private String imageFilm;
    private String filmName;

    ItemWishList(String img, String name){
        this.imageFilm = img;
        this.filmName = name;
    }

    public String getFilmName() {
        return filmName;
    }

    public String getImageFilm() {
        return imageFilm;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public void setImageFilm(String imageFilm) {
        this.imageFilm = imageFilm;
    }
}
