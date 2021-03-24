package com.example.tixtox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.tixtox.FilmDetailsFragment.FilmDetails;

    public class ThongTinPhimActivity extends FragmentActivity  {
    FragmentTransaction ft;
    FilmDetails filmDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        filmDetails = FilmDetails.newInstance();

        setContentView(R.layout.activity_film_details);
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentFilmDetails, filmDetails);
        ft.commit();

    }
}