package com.example.tixtox.FilmDetailsFragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tixtox.Phim;
import com.example.tixtox.R;
import com.example.tixtox.ThongTinPhimActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FilmDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FilmDetails extends Fragment {
    Phim phim;

    public FilmDetails() {
        // Required empty public constructor
    }

    public static FilmDetails newInstance() {
        FilmDetails fragment = new FilmDetails();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_thong_tin_phim, container, false);
    }
}