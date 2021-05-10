package com.example.tixtox.HeThongRapFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;

import com.example.tixtox.Model.ModelRap;
import com.example.tixtox.Model.Rap.CumRap;
import com.example.tixtox.Model.Rap.RapDetail;
import com.example.tixtox.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class HeThongRapFragment extends Fragment {
    private ExpandableListView listView;

    private ProgressBar progressBar;
    ExpandableListRapAdapter expandableListRapAdapter;


    public HeThongRapFragment() {
    }

    public static HeThongRapFragment newInstance() {
        HeThongRapFragment fragment = new HeThongRapFragment();
        Bundle bundle = new Bundle();

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_cum_rap, container, false);
        listView = view.findViewById(R.id.expandableListView);
        progressBar = view.findViewById(R.id.progressBar3);
        progressBar.setVisibility(View.VISIBLE);
        // Lấy thông tin cụm rạp và hệ thống rạp
        Thread getDanhSachRap = new Thread() {
            @Override
            public void run() {
                super.run();


                try {
                    ModelRap modelRap = ModelRap.getInstance();

                    expandableListRapAdapter = new ExpandableListRapAdapter(getContext(), modelRap.getCumRaps(), modelRap.getHeThongRaps());
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(View.GONE);
                                listView.setAdapter(expandableListRapAdapter);
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };


        getDanhSachRap.start();

        return view;
    }
}
