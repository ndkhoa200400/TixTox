package com.example.tixtox.WishList;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tixtox.R;

import java.util.ArrayList;

public class WishListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_wishlist);
        ListView wishList = findViewById(R.id.wishList);
        ArrayList<ItemWishList> listFilm = new ArrayList<>();

        String rcvMsg = (String) getIntent().getSerializableExtra("phimyeuthich");
        System.out.println(rcvMsg);
        if(rcvMsg == null || rcvMsg.isEmpty()){
            TextView notify = findViewById(R.id.notify);
            notify.setText("Nothing here");
        }
        else {
            String[] data = rcvMsg.split(" ");
            ItemWishList item = new ItemWishList(data[0], data[1]);
            listFilm.add(item);
        }
        WishListAdapter adapter=new WishListAdapter(this,R.layout.layout_item_wishlist,listFilm);
        wishList.setAdapter(adapter);
    }
}
