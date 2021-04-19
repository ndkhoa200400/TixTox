package com.example.tixtox.FilmDetailsFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tixtox.Model.Phim;
import com.example.tixtox.R;

public class BinhLuan extends Fragment {
    TextView txtRating, txtNosRating;
    private Phim phim;
    public BinhLuan() {}

    public static BinhLuan newInstance() {
        BinhLuan fragment = new BinhLuan();
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
        View view = inflater.inflate(R.layout.fragment_binh_luan, container, false);
        txtRating = view.findViewById(R.id.txtRating);
        txtNosRating = view.findViewById(R.id.txtNosRating);
        return view;
    }
    public void setPhim(Phim p)
    {
        this.phim = p;
    }
}