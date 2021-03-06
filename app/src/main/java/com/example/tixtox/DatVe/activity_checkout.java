package com.example.tixtox.DatVe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tixtox.HomeActivity;
import com.example.tixtox.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class activity_checkout extends AppCompatActivity {
    ImageView imgQR_code,imgTenphim;
    TextView  txtTenPhim,txtTenRap,txtMaDon,txtNgayChieu,txtPhongChieu,txtSoGhe,txtSuatChieu,txtXemBill;
    Button btnHome,btnHuyVe;
    VeXemPhim vexemphim = new VeXemPhim();
    String trangthai;
    DatabaseReference dtb;
    String MaHoaDon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        imgTenphim = (ImageView) findViewById(R.id.imgTenphim_checkout);
        txtSoGhe = (TextView) findViewById(R.id.txtSoghe_checkout);
        txtNgayChieu = (TextView) findViewById(R.id.txtNgaychieu_checkout);
        txtPhongChieu = (TextView)  findViewById(R.id.txtPhongChieu_checkout);
        txtMaDon = (TextView) findViewById(R.id.txtMadon_checkout);
        txtTenPhim = (TextView) findViewById(R.id.txtTenPhim_checkout) ;
        txtTenRap = (TextView) findViewById(R.id.txtTenRap_checkout) ;
        txtSuatChieu = (TextView) findViewById(R.id.txtSuatChieu_checkout) ;
        btnHuyVe = (Button) findViewById(R.id.btnHuy);
        txtXemBill = (TextView) findViewById(R.id.txtXemHoaDon_checkout);
        dtb = FirebaseDatabase.getInstance().getReference();
        imgQR_code=(ImageView) findViewById(R.id.imgQR_code);

        Intent intent = getIntent();
        String Mave = intent.getStringExtra("Key");
        //String hinhanh = intent.getStringExtra("Phim_Hinh_Anh");
        Load(Mave);
        Mave.trim();

        MultiFormatWriter write =new MultiFormatWriter();
        try {
            BitMatrix matrix = write.encode(Mave, BarcodeFormat.QR_CODE,
                    400,400);
            BarcodeEncoder encoder  = new BarcodeEncoder();
            Bitmap bitmap = encoder.createBitmap(matrix);
            imgQR_code .setImageBitmap(bitmap);

        } catch (WriterException e) {
            e.printStackTrace();
        }
        txtXemBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_checkout.this, activity_bill.class);
                intent.putExtra("Key", txtMaDon.getText().toString());
                intent.putExtra("MaHoaDon", MaHoaDon);
                startActivity(intent);
            }
        });
        btnHome = (Button) findViewById(R.id.btn_ManHinhHome);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_checkout.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        btnHuyVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dtb.child("VeXemPhim").child(Mave).child("trangThai").setValue("???? H???y");
                
            }
        });
    }

    private void Load(String MaVe) {


        dtb.child("VeXemPhim").child(MaVe).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                VeXemPhim ve = snapshot.getValue(VeXemPhim.class);
                txtMaDon.setText(MaVe);
                txtSuatChieu.setText(snapshot.child("suatChieu").getValue().toString());
                txtNgayChieu.setText(ve.getThoiGian());
                txtTenPhim.setText(ve.getPhim());
                txtTenRap.setText(ve.getRapphim());
                txtPhongChieu.setText(ve.getPhongChieu());
                MaHoaDon = ve.getHoaDon();
                txtSoGhe.setText(ve.getGhe());
                Glide.with(activity_checkout.this).load(ve.HinhAnh)
                        .into(imgTenphim);
                if (ve.getTrangThai().equals("???? H???y")) {
                    btnHuyVe.setText("???? H???y");
                    btnHuyVe.setEnabled(false);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}