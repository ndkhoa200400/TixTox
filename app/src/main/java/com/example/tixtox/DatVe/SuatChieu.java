package com.example.tixtox.DatVe;


import android.text.format.DateFormat;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SuatChieu {
    String id;
    String Ghe;
    DatabaseReference dtb = FirebaseDatabase.getInstance().getReference();
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGhe() {
        return Ghe;
    }

    public void setGhe(String ghe) {
        Ghe = ghe;
    }


    public SuatChieu(String maphim, String ngay, String gio) {
        id = maphim+ngay+gio;
     //   Load();
    }
    private void UpdateGhe(String v)
    {
        Ghe = Ghe + " " + v;
    }

    private void DeleteGhe(String v)
    {

    }

    void Load()
    {
        dtb.child("SuatChieu").child(this.id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Ghe = snapshot.child("Ghe").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
