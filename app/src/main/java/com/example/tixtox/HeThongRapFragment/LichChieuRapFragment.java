package com.example.tixtox.HeThongRapFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tixtox.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LichChieuRapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LichChieuRapFragment extends Fragment {


    public LichChieuRapFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static LichChieuRapFragment newInstance() {
        LichChieuRapFragment fragment = new LichChieuRapFragment();

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
        View v =  inflater.inflate(R.layout.activity_lich_chieu_phim, container, false);

        return v;
    }
}