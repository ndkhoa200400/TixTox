package com.example.tixtox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tixtox.Model.VeXemPhim;
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

import java.util.ArrayList;
import java.util.List;

public class activity_checkout extends AppCompatActivity {
    ImageView imgQR_code;
    TextView  txtTenPhim,txtTenRap,txtMaDon,txtNgayChieu,txtPhongChieu,txtSoGhe,txtSuatChieu;
    Button btnHome;
    VeXemPhim vexemphim = new VeXemPhim();
    DatabaseReference dtb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        txtSoGhe = (TextView) findViewById(R.id.txtSoghe_checkout);
        txtNgayChieu = (TextView) findViewById(R.id.txtNgaychieu_checkout);
        txtPhongChieu = (TextView)  findViewById(R.id.txtPhongChieu_checkout);
        txtMaDon = (TextView) findViewById(R.id.txtMadon_checkout);
        txtTenPhim = (TextView) findViewById(R.id.txtTenPhim_checkout) ;
        txtTenRap = (TextView) findViewById(R.id.txtTenRap_checkout) ;
        txtSuatChieu = (TextView) findViewById(R.id.txtSuatChieu) ;
        dtb = FirebaseDatabase.getInstance().getReference();
        imgQR_code=(ImageView) findViewById(R.id.imgQR_code);
        String abc = "-MYJ8E57RrmrALM7Jr3M";
        Load(abc);
        abc.trim();
        MultiFormatWriter write =new MultiFormatWriter();
        try {
            BitMatrix matrix = write.encode(abc, BarcodeFormat.QR_CODE,
                    400,400);
            BarcodeEncoder encoder  = new BarcodeEncoder();
            Bitmap bitmap = encoder.createBitmap(matrix);
            imgQR_code .setImageBitmap(bitmap);

        } catch (WriterException e) {
            e.printStackTrace();
        }
        btnHome = (Button) findViewById(R.id.btn_ManHinhHome);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_checkout.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    private void Load(String MaVe) {


      /*  dtb.child("VeXemPhim").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                VeXemPhim temp = snapshot.getValue(VeXemPhim.class);
                vexm.add(temp);
                Toast.makeText(getApplicationContext(), temp.getGhe(), Toast.LENGTH_SHORT).show();
                ChangeGhe = ConverStringtoGhe(temp.getGhe());
                for(int j =0;j<ChangeGhe.size();j++){
                    setImageToX(ChangeGhe.get(j));

                }


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
        });*/

        dtb.child("VeXemPhim").child(MaVe).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                VeXemPhim a = snapshot.getValue(VeXemPhim.class);
                txtMaDon.setText(MaVe);
                txtNgayChieu.setText(a.getThoiGian());
                txtTenPhim.setText(a.getPhim());
                txtTenRap.setText(a.getRapphim());
                txtPhongChieu.setText(a.getPhongChieu());
                txtSoGhe.setText(a.getGhe());
//                txtSuatChieu.setText(a.getSuatChieu());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}