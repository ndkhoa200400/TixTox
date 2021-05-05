package com.example.tixtox.HeThongRapFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.tixtox.Model.ModelRap;
import com.example.tixtox.Model.Phim;
import com.example.tixtox.Model.Rap.RapDetail;
import com.example.tixtox.R;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class RapDetailActivity extends AppCompatActivity {
    private String maCumRap, maRapDetail;
    private ArrayList<Phim> listPhimDangChieu;
    private TextView txtTenRap;
    RapDetail rapDetail;
    private ProgressBar progressBar;
    private TabLayout tabLayout;

    public RapDetailActivity() {
    }

    public RapDetailActivity(String maCumRap, String maRapDetail) {
        this.maCumRap = maCumRap;
        this.maRapDetail = maRapDetail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_rap);

        txtTenRap = findViewById(R.id.tenRap);
        progressBar = findViewById(R.id.progressBar7);
        progressBar.setVisibility(View.VISIBLE);
        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();
        maCumRap = (String) bundle.get("maCumRap");
        maRapDetail = (String) bundle.get("maRapDetail");


        try {
            ModelRap modelRap = ModelRap.getInstance();
            rapDetail = modelRap.getMotRapDetail(maCumRap, maRapDetail);
            if (rapDetail != null) {
                txtTenRap.setText(rapDetail.getTenRap());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        tabLayout = findViewById(R.id.tabLayout2);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                progressBar.setVisibility(View.VISIBLE);
                int position = tab.getPosition();
                if (position == 0) {

                   loadLichChieuFragment();
                } else {

                    loadThongTinRapFragment();
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        loadLichChieuFragment();

    }

    private void loadLichChieuFragment() {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        LichChieuRapFragment lichChieuRapFragment = LichChieuRapFragment.newInstance();

        ft.replace(R.id.fragment_rapDetail, lichChieuRapFragment);
        ft.commit();
        progressBar.setVisibility(View.GONE);
    }

    private void loadThongTinRapFragment() {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ThongTinRapDetailFragment thongTinRapDetailFragment = ThongTinRapDetailFragment.newInstance(rapDetail, maCumRap);

        ft.replace(R.id.fragment_rapDetail, thongTinRapDetailFragment);
        ft.commit();

    }
}
