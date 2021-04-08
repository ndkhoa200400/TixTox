package com.example.tixtox;

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

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class activity_checkout extends AppCompatActivity {
    ImageView imgQR_code;
    TextView  txtTenPhim,txtTenRap,txtMaDon,txtNgayChieu,txtPhongChiue,txtSoGhe;
    Button btnHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        imgQR_code=(ImageView) findViewById(R.id.imgQR_code);
        String abc = "123456";
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
}