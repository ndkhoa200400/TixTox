package com.example.tixtox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.Collections;

public class MuaVeActivity extends AppCompatActivity {
    GridView gridview;
    private ArrayList<ImageModel> arrayList;

    Integer[] image = {R.drawable.icon_ghe,R.drawable.icon_ghe,R.drawable.icon_ghe,
            R.drawable.icon_ghe,R.drawable.icon_ghe,
            R.drawable.icon_ghe,R.drawable.icon_ghe,
            R.drawable.icon_ghe,R.drawable.icon_ghe,
            R.drawable.icon_ghe,R.drawable.icon_ghe,
            R.drawable.icon_ghe,R.drawable.icon_ghe,
            R.drawable.icon_ghe,R.drawable.icon_ghe,
            R.drawable.icon_ghe,R.drawable.icon_ghe,
            R.drawable.icon_ghe,R.drawable.icon_ghe,
            R.drawable.icon_ghe,R.drawable.icon_ghe,
            R.drawable.icon_ghe,R.drawable.icon_ghe,
            R.drawable.icon_ghe,R.drawable.icon_ghe,
            R.drawable.icon_ghe,R.drawable.icon_ghe,
            R.drawable.icon_ghe,R.drawable.icon_ghe,
            R.drawable.icon_ghe,R.drawable.icon_ghe,
            R.drawable.icon_ghe,R.drawable.icon_ghe,
            R.drawable.icon_ghe,R.drawable.icon_ghe,
            R.drawable.icon_ghe,R.drawable.icon_ghe,
            R.drawable.icon_ghe,R.drawable.icon_ghe,
            R.drawable.icon_ghe,R.drawable.icon_ghe,
            R.drawable.icon_ghe,R.drawable.icon_ghe,
            R.drawable.icon_ghe,R.drawable.icon_ghe,
            R.drawable.icon_ghe,R.drawable.icon_ghe,
            R.drawable.icon_ghe,R.drawable.icon_ghe,
            R.drawable.icon_ghe,R.drawable.icon_ghe,
            R.drawable.icon_ghe,R.drawable.icon_ghe,
            R.drawable.icon_ghe,R.drawable.icon_ghe,
            R.drawable.icon_ghe,R.drawable.icon_ghe,
            R.drawable.icon_ghe,R.drawable.icon_ghe,
            R.drawable.icon_ghe,R.drawable.icon_ghe,
            R.drawable.icon_ghe,R.drawable.icon_ghe,
            R.drawable.icon_ghe,R.drawable.icon_ghe,
            R.drawable.icon_ghe,R.drawable.icon_ghe,
            R.drawable.icon_ghe,R.drawable.icon_ghe,
            R.drawable.icon_ghe,R.drawable.icon_ghe,
            R.drawable.icon_ghe,R.drawable.icon_ghe,
            R.drawable.icon_ghe,R.drawable.icon_ghe,
            R.drawable.icon_ghe,R.drawable.icon_ghe,
            R.drawable.icon_ghe,R.drawable.icon_ghe,
            R.drawable.icon_ghe,R.drawable.icon_ghe,
            R.drawable.icon_ghe,R.drawable.icon_ghe,
            R.drawable.icon_ghe,R.drawable.icon_ghe,
            R.drawable.icon_ghe,R.drawable.icon_ghe,
            R.drawable.icon_ghe
    };
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        gridview = (GridView) findViewById(R.id.gridviewGhe);
//
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_muave);
//        gridview = (GridView) findViewById(R.id.gridviewGhe);
//
//
//        gridview.setAdapter(new ImageAdapterGridView(this));
//    }
    private  class ImageAdapterGridView extends BaseAdapter
    {
        private Context mcontext;
        public ImageAdapterGridView(Context context)
        {
            mcontext = context;
        }

        @Override
        public int getCount() {
            return image.length;
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
            ImageView imageView;
            if(convertView == null) {
                imageView = new ImageView(mcontext);

                imageView.setLayoutParams(new GridView.LayoutParams(60,73));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

                imageView.setPadding(3,3,3,10);

            }else
            {
                imageView = (ImageView) convertView;
            }
            imageView.setImageResource(image[position]);
            return imageView;
        }

    }



}