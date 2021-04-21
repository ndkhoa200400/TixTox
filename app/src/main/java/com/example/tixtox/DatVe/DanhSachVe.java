package com.example.tixtox.DatVe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tixtox.Model.ModelRap;
import com.example.tixtox.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DanhSachVe extends AppCompatActivity {
    ListView listview;
    TextView txtKhongCoVe;
    DatabaseReference dtb;
    ArrayList<VeXemPhim> arrayList;
    AdapterVeXemPhim adapter;
    String Matk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_ve);


        dtb = FirebaseDatabase.getInstance().getReference();
        Intent intent = new Intent();
        Matk = intent.getStringExtra("MaTK");
        listview = (ListView) findViewById(R.id.ListViewVe);
        txtKhongCoVe = (TextView) findViewById(R.id.txtKVe_Ve);
        adapter = new AdapterVeXemPhim(DanhSachVe.this,R.layout.layout_listve,arrayList);
        arrayList = new ArrayList<>();


        new Thread(){
            @Override
            public void run() {
                dtb.child("VeXemPhim").addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        VeXemPhim ve = snapshot.getValue(VeXemPhim.class);
                        if(ve.id.equals(FirebaseAuth.getInstance()
                                .getCurrentUser().getUid())) {
                            arrayList.add(0,ve);
                            txtKhongCoVe.setText("");
                            adapter = new AdapterVeXemPhim(DanhSachVe.this, R.layout.layout_listve, arrayList);
                            listview.setAdapter(adapter);
                        }
                        else
                        {

                        }

                    //    Toast.makeText(getApplicationContext(),"af" +  ve.getGhe(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }.start();




        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DanhSachVe.this, activity_checkout.class);
                intent.putExtra("Key", arrayList.get(position).getMaVe());
                startActivity(intent);
            }
        });




    }

}