package com.example.tixtox.WishList;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tixtox.DatVe.MuaVeActivity;
import com.example.tixtox.DatVe.VeXemPhim;
import com.example.tixtox.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class WishListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        ArrayList<ItemWishList> listFilm = new ArrayList<>();

        setContentView(R.layout.layout_wishlist);
        ListView wishList = findViewById(R.id.wishList);
        new Thread(){
            @Override
            public void run() {
                database.child("WishList").child(currentUserId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for(DataSnapshot s:snapshot.getChildren()){
                            ItemWishList temp = new ItemWishList((String) s.getValue(), s.getKey());
                            listFilm.add(temp);
                        }
                        if(listFilm.size() > 0) {
                        WishListAdapter adapter=new WishListAdapter(WishListActivity.this,R.layout.layout_item_wishlist,listFilm);
                        wishList.setAdapter(adapter);}

                        else{
                            TextView noti = findViewById(R.id.notify);
                            noti.setText("Danh sách trống.");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }.start();



    }
}
