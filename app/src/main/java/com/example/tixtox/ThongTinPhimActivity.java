package com.example.tixtox;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.tixtox.FilmDetailsFragment.BinhLuanFragment;
import com.example.tixtox.FilmDetailsFragment.FilmDetailsFragment;
import com.example.tixtox.FilmDetailsFragment.LichChieuPhimFragment;
import com.example.tixtox.Model.Phim;
import com.google.android.material.tabs.TabLayout;

public class ThongTinPhimActivity extends FragmentActivity  {
    FragmentTransaction ft;
    FilmDetailsFragment filmDetails;
    LichChieuPhimFragment lichChieuPhimFragment;
    BinhLuanFragment binhLuanFragment;
    TabLayout tabThongTinPhim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_details);

        Phim phim = (Phim) getIntent().getSerializableExtra("phim");

        filmDetails = FilmDetailsFragment.newInstance();
        filmDetails.setPhim(phim);
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentFilmDetails, filmDetails);
        ft.commit();
        System.out.println(phim.getMaPhim());
        tabThongTinPhim = findViewById(R.id.tabThongTinPhim);
        tabThongTinPhim.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tabPos = tab.getPosition();
                if(tabPos == 0){
                    filmDetails = FilmDetailsFragment.newInstance();
                    filmDetails.setPhim(phim);
                    ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.fragmentFilmDetails, filmDetails);
                    ft.commit();
                }
                else
                    if (tabPos == 1){
                    lichChieuPhimFragment = LichChieuPhimFragment.newInstance();
                    lichChieuPhimFragment.setPhim(phim);
                    ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.fragmentFilmDetails, lichChieuPhimFragment);
                    ft.commit();
                }
                else {
                    binhLuanFragment = BinhLuanFragment.newInstance();
                    binhLuanFragment.setPhim(phim);
                    ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.fragmentFilmDetails, binhLuanFragment);
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