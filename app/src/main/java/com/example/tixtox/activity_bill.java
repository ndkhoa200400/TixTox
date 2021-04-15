package com.example.tixtox;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class activity_bill extends AppCompatActivity {
    TextView txtTaiKhoan,txtMagd,txtGiaghedon,txtGiaghedoi,txtSoghedon,txtSogheDoi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        txtGiaghedoi=(TextView) findViewById(R.id.txtGiagheDoi_bill);
        txtGiaghedon=(TextView) findViewById(R.id.txtGiaGheDon_bill);
        txtTaiKhoan = (TextView) findViewById(R.id.txtTaiKhoan_bill);
        txtMagd = (TextView) findViewById(R.id.txtMagd_bill);
        txtSogheDoi = (TextView) findViewById(R.id.txtSogheDoi_bill);
        txtSoghedon=(TextView) findViewById(R.id.txtSogheDon_bill);
    }
}