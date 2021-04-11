package com.example.tixtox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.tixtox.AccountFragment.TaiKhoanFragment;
import com.example.tixtox.HeThongRapFragment.ExpandableListRapAdapter;
import com.example.tixtox.HeThongRapFragment.HeThongRapFragment;
import com.example.tixtox.HomeFragment.PhimsFragment;
import com.example.tixtox.Model.ModelRap;
import com.facebook.login.LoginManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {



    private TabLayout tabHome;
    PhimsFragment phimsFragment;
    private BottomNavigationView bottomNavigation;
    private static int navigationPosition = R.id.navigation_home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home);

        bottomNavigation = findViewById(R.id.bottom_navigation);

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.navigation_home:
                        // Chuyển hướng về trang home

                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();;

                        PhimsFragment phimsFragment = PhimsFragment.newInstance();
                        ft.replace(R.id.fragment_home, phimsFragment);
                        ft.commit();

                        break;
                    case R.id.navigation_theater:
                        try {

                            ft = getSupportFragmentManager().beginTransaction();
                            HeThongRapFragment heThongRapFragment = HeThongRapFragment.newInstance();
                            ft.replace(R.id.fragment_home, heThongRapFragment);
                            ft.commit();

                        }catch(Exception e){
                            e.printStackTrace();
                        }

                        break;
                    case R.id.navigation_account:
                        // Chuyển hướng về trang thông tin cá nhân

                        checkAccount();

                        break;
                }
                updateNavigationBarState(item.getItemId());
                return true;
            }
        });

        bottomNavigation.setSelectedItemId(navigationPosition);

        Thread getDanhSachRap = new Thread() {
            @Override
            public void run() {
                super.run();


                try {
                    ModelRap modelRap = ModelRap.getInstance();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };


        getDanhSachRap.start();

    }
    private void updateNavigationBarState(long actionId){
        Menu menu = bottomNavigation.getMenu();

        for (int i = 0, size = menu.size(); i < size; i++) {
            MenuItem item = menu.getItem(i);
            item.setChecked(item.getItemId() == actionId);
        }
        navigationPosition = (int) actionId;
    }
    @Override
    protected void onStart() {
        super.onStart();
        bottomNavigation.setSelectedItemId(navigationPosition);

    }

    protected void checkAccount() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();;
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final FirebaseUser mUser = mAuth.getCurrentUser();
        // Kiểm tra nếu người dùng đã đăng nhập thì load fragment đăng nhập
        if (mUser != null)
        {
            TaiKhoanFragment taiKhoanFragment = TaiKhoanFragment.newInstance();

            ft.replace(R.id.fragment_home, taiKhoanFragment);
            ft.commit();
        }
        else{
            navigationPosition = R.id.navigation_home;
            startActivity(new Intent(this, LogInActivity.class));

        }

    }

}