package com.example.tixtox.WishList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    ArrayList<ItemWishList> listFilm;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        listFilm = new ArrayList<>();
        setContentView(R.layout.layout_wishlist);
        ListView wishList = findViewById(R.id.wishList);

        WishListAdapter adapter = new WishListAdapter(WishListActivity.this, R.layout.layout_item_wishlist, listFilm);
        wishList.setAdapter(adapter);

        if(currentUserId != null ) {
            database.child("WishList").child(currentUserId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    listFilm = new ArrayList<>();
                    for (DataSnapshot s : snapshot.getChildren()) {
                        ItemWishList temp = new ItemWishList((String) s.getValue(), s.getKey());
                        if (!listFilm.contains(temp))
                            listFilm.add(temp);

                    }
                    if (listFilm.size() > 0) {
                        adapter.notifyDataSetChanged();
                    } else {
                        TextView noti = findViewById(R.id.notify);
                        noti.setText("Danh sách trống.");
                    }
                    WishListAdapter adapter = new WishListAdapter(WishListActivity.this, R.layout.layout_item_wishlist, listFilm);
                    wishList.setAdapter(adapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


            Button deleteAll = findViewById(R.id.delete_all);
            deleteAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!listFilm.isEmpty()) {
                        listFilm.clear();
                        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        database.child("WishList").child(currentUserId).removeValue();
                        adapter.notifyDataSetChanged();
                        TextView noti = findViewById(R.id.notify);
                        noti.setText("Danh sách trống.");
                    }
                }
            });
        }
    }

}
