
package com.example.tixtox.AccountFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.tixtox.Model.KhuyenMai;
import com.example.tixtox.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class DanhSachKhuyenMaiActivity extends AppCompatActivity {
    ListView listView;
    ProgressBar progressBar;
    TextView txtKhongCoKM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_khuyen_mai);
        listView = findViewById(R.id.listviewKhuyenMai);
        progressBar = findViewById(R.id.progressBar5);
        progressBar.setVisibility(View.VISIBLE);
        txtKhongCoKM = findViewById(R.id.txtKhongCoKM);
        txtKhongCoKM.setText("");
        displayListView();
    }
    void displayListView()
    {
        FirebaseDatabase.getInstance()
                .getReference("KhuyenMai").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot == null)
                {
                    txtKhongCoKM.setText("Hiện tại không có khuyến mãi");
                }
                else{

                    HashMap<String, HashMap<String, String>> khuyenMais =(HashMap<String, HashMap<String, String>>) snapshot.getValue();
                    ArrayList<KhuyenMai> khuyenMaiArrayList = new ArrayList<>();
                    for (String key: khuyenMais.keySet())
                    {
                        HashMap<String, String> obj = khuyenMais.get(key);
                        String name = obj.get("name");
                        double discount = Double.parseDouble(String.valueOf(obj.get("discount")));
                        int type = Integer.parseInt(String.valueOf(obj.get("type")));

                        khuyenMaiArrayList.add(new KhuyenMai(type, name, discount));

                    }

                    KhuyenMaiAdapter khuyenMaiAdapter = new KhuyenMaiAdapter(getApplicationContext(), R.layout.layout_khuyen_mai, khuyenMaiArrayList);
                    listView.setAdapter(khuyenMaiAdapter);
                    progressBar.setVisibility(View.GONE);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}