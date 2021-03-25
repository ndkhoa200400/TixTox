package com.example.tixtox.HomeFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.example.tixtox.HomeActivity;
import com.example.tixtox.ImageAdapter;
import com.example.tixtox.ModelPhim;
import com.example.tixtox.Phim;
import com.example.tixtox.R;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PhimsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PhimsFragment extends Fragment {
    private GridView gridView;
    private ProgressBar progressBar;
    public PhimsFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static PhimsFragment newInstance() {
        PhimsFragment fragment = new PhimsFragment();
        Bundle args = new Bundle();


        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Thread loadingPhimThread = new Thread(){
            @Override
            public void run() {
                ModelPhim modelPhim = new ModelPhim();
                try {
                    ArrayList<Phim> phims = modelPhim.getPhimTheoNgay("11/11/2020", "10/03/2021");

                    if (phims!= null)
                    {
                        ArrayList<String> posters = new ArrayList<>();
                        ArrayList<String> filmNames = new ArrayList<>();
                        ArrayList<String> ratings = new ArrayList<>();
                        for(Phim p: phims)
                        {
                            filmNames.add(p.getTenPhim());
                            posters.add(p.getHinhAnh());
                            ratings.add(p.getDanhGia());
                        }
                        if (getActivity() != null)
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setVisibility(View.GONE);
                                    loadPoster2(posters, filmNames, ratings);

                                }
                            });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        loadingPhimThread.start();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_phims, container, false);

        progressBar = getView().findViewById(R.id.processbar_phims);
        progressBar.setVisibility(View.VISIBLE);
        gridView = (GridView) view.findViewById(R.id.gridView);
        return view;
    }
    private void loadPoster2(ArrayList<String> posters, ArrayList<String> filmNames, ArrayList<String> ratings){


        ImageAdapter adapter= new ImageAdapter(getContext(), posters, filmNames, ratings);
        gridView.setAdapter(adapter);
        //item click listener
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {

            }
        });
    }
}