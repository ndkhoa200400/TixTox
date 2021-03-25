package com.example.tixtox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.tixtox.FilmDetailsFragment.FilmDetails;
import com.example.tixtox.HomeFragment.PhimsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private ArrayList<ImageModel> arrayList;

    private TabLayout tabHome;
    PhimsFragment phimsFragment;
    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home);




        bottomNavigation = findViewById(R.id.bottom_navigation);

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();;
                switch (item.getItemId())
                {
                    case R.id.navigation_home:
                        PhimsFragment phimsFragment = PhimsFragment.newInstance();
                        ft.replace(R.id.fragment_home, phimsFragment);
                        ft.commit();
                        break;
                    case R.id.navigation_theater:

                        break;
                    case R.id.navigation_account:
                        TaiKhoanFragment taiKhoanFragment = TaiKhoanFragment.newInstance();

                        ft.replace(R.id.fragment_home, taiKhoanFragment);
                        ft.commit();
                        break;
                }
                return true;
            }
        });


    }

}