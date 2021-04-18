package com.example.tixtox.DatVe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tixtox.HomeActivity;
import com.example.tixtox.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class activity_bill extends AppCompatActivity {
    TextView txtTaiKhoan,txtMagd,txtGiaghedon,txtGiaghedoi,txtSoghedon,txtSogheDoi,txtTongTien;
    Button btnVeXemPhim, btnManHinhChinh;
    DatabaseReference dtb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        dtb = FirebaseDatabase.getInstance().getReference();
        txtGiaghedoi=(TextView) findViewById(R.id.txtGiagheDoi_bill);
        txtGiaghedon=(TextView) findViewById(R.id.txtGiaGheDon_bill);
        txtTaiKhoan = (TextView) findViewById(R.id.txtTaiKhoan_bill);
        txtTongTien = (TextView) findViewById(R.id.txtThanhTien_bill);
        txtMagd = (TextView) findViewById(R.id.txtMagd_bill);
        txtSogheDoi = (TextView) findViewById(R.id.txtSogheDoi_bill);
        txtSoghedon=(TextView) findViewById(R.id.txtSogheDon_bill);
        btnVeXemPhim = (Button) findViewById(R.id.btn_VeXemPhim_bill);
        btnManHinhChinh = (Button)  findViewById(R.id.btn_ManHinh_bill);
        Intent intent = getIntent();
        String Mave = intent.getStringExtra("Key");
        String MaHoaDon = intent.getStringExtra("MaHoaDon");
        Load(MaHoaDon);
        btnVeXemPhim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_bill.this, activity_checkout.class);
                intent.putExtra("Key", Mave);
                startActivity(intent);
            }
        });
        btnManHinhChinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity_bill.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    private void Load(String MaHoaDon) {


        dtb.child("HoaDon").child(MaHoaDon).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HoaDon bill = snapshot.getValue(HoaDon.class);
                Integer soghedoi = bill.getSoGheDoi();
                Integer soghedon = bill.getSoGheDon();
                txtSogheDoi.setText(soghedoi.toString());
                txtSoghedon.setText(soghedon.toString());
                txtMagd.setText(MaHoaDon);
                txtTongTien.setText(bill.getThanhTien());
                txtTaiKhoan.setText(bill.getID_Account());
                txtGiaghedoi.setText((new Integer(soghedoi*140000).toString()));
                txtGiaghedon.setText((new Integer(soghedon*70000).toString()));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}