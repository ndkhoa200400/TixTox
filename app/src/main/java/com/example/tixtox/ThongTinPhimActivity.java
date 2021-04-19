package com.example.tixtox;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.tixtox.FilmDetailsFragment.BinhLuan;
import com.example.tixtox.FilmDetailsFragment.FilmDetails;
import com.example.tixtox.FilmDetailsFragment.LichChieuPhim;
import com.example.tixtox.Model.Phim;
import com.google.android.material.tabs.TabLayout;

public class ThongTinPhimActivity extends FragmentActivity  {
    FragmentTransaction ft;
    FilmDetails filmDetails;
    LichChieuPhim lichChieuPhim;
    BinhLuan binhLuan;
    TabLayout tabThongTinPhim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_details);

        Phim phim = (Phim) getIntent().getSerializableExtra("phim");

        filmDetails = FilmDetails.newInstance();
        filmDetails.setPhim(phim);
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentFilmDetails, filmDetails);
        ft.commit();

        tabThongTinPhim = findViewById(R.id.tabThongTinPhim);
        tabThongTinPhim.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tabPos = tab.getPosition();
                if(tabPos == 0){
                    filmDetails = FilmDetails.newInstance();
                    filmDetails.setPhim(phim);
                    ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.fragmentFilmDetails, filmDetails);
                    ft.commit();
                }
                else
                    if (tabPos == 1){
                    lichChieuPhim = LichChieuPhim.newInstance();
                    lichChieuPhim.setPhim(phim);
                    ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.fragmentFilmDetails, lichChieuPhim);
                    ft.commit();
                }
                else {
                    binhLuan = BinhLuan.newInstance();
                    binhLuan.setPhim(phim);
                    ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.fragmentFilmDetails, binhLuan);
                    ft.commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}