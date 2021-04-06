package com.example.tixtox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
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
    }
}