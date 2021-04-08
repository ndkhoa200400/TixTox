package com.example.tixtox.HeThongRapFragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tixtox.Model.ModelRap;
import com.example.tixtox.Model.Phim;
import com.example.tixtox.Model.Rap.RapDetail;
import com.example.tixtox.R;

import java.io.IOException;
import java.util.ArrayList;

public class RapDetailActivity extends AppCompatActivity {
    private String maCumRap, maRapDetail, tenChiNhanhRap;
    private ArrayList<Phim> listPhimDangChieu;
    private TextView txtTenRap;
    public RapDetailActivity(){}

    public RapDetailActivity(String maCumRap, String maRapDetail){
        this.maCumRap = maCumRap;
        this.maRapDetail = maRapDetail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_rap);

        txtTenRap = findViewById(R.id.tenRap);

        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();
        maCumRap = (String) bundle.get("maCumRap");
        maRapDetail = (String) bundle.get("maRapDetail");
        System.out.println(maCumRap);
        System.out.println(maRapDetail);
        try {
            ModelRap modelRap = ModelRap.getInstance();
            RapDetail rapDetail = modelRap.getMotRapDetail(maCumRap, maRapDetail);
            if (rapDetail != null)
            {
                txtTenRap.setText(rapDetail.getTenRap());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
