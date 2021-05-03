package com.example.tixtox;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.example.tixtox.WishList.WishListActivity;
import com.example.tixtox.WishList.WishListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class ImageAdapter extends ArrayAdapter<String> {
    Context context;
    ArrayList<String> posters;
    ArrayList<String> filmNames;
    ArrayList<String> danhGias;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    public ImageAdapter(Context context, ArrayList<String> posters, ArrayList<String> filmNames, ArrayList<String> danhGias) {
        super(context, R.layout.image_view, posters);
        this.context = context;
        this.posters = posters;
        this.filmNames = filmNames;
        this.danhGias = danhGias;
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getCount() {
        return posters.size();
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

        //poster of film
        ImageView imageView;
        imageView = (ImageView) convertView.findViewById(R.id.image);
        Glide.with(imageView.getContext())
                .load(posters.get(position))
                .error(R.drawable.icon_home)
                .into((ImageView) imageView);

        //name of film
        TextView filmName = convertView.findViewById(R.id.filmName);
        filmName.setText(filmNames.get(position));

        //danh gia
        TextView danhGia = convertView.findViewById(R.id.danhGia);
        danhGia.setText(danhGias.get(position));

        ConstraintLayout layoutItemFilm = convertView.findViewById(R.id.layoutItemFilm);
        if(filmNames.get(position).equals(filmNames.get(0))){
            layoutItemFilm.setPadding(0, 220,0,0);
        }
        else{
            layoutItemFilm.setPadding(0, 0, 0,0);
        }

        ImageView btn_favour = convertView.findViewById(R.id.btn_favour);
        btn_favour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //push data to database, wishlistactivity will get this data from database
                String currentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                database.child("WishList").child(currentUserID).push();
                database.child("WishList").child(currentUserID).child(filmNames.get(position)).setValue(posters.get(position));
            }
        });

        return convertView;
    }
}
