package com.example.tixtox.HeThongRapFragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tixtox.Model.Phim;
import com.example.tixtox.R;

import java.util.ArrayList;

public class RapDetailActivity extends AppCompatActivity {
    private String maCumRap, maRapDetail, tenChiNhanhRap;
    private ArrayList<Phim> listPhimDangChieu;

    public RapDetailActivity(){}

    public RapDetailActivity(String maCumRap, String maRapDetail){
        this.maCumRap = maCumRap;
        this.maRapDetail = maRapDetail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_rap);
    }

}
