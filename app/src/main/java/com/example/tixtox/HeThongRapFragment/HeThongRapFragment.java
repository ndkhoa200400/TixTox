package com.example.tixtox.HeThongRapFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ListView;

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
    private ArrayList<CumRap> listCumRap;
    private HashMap<CumRap, ArrayList<RapDetail>> heThongRaps;
    ExpandableListRapAdapter expandableListRapAdapter;
    Thread getDanhSachRap;

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
        View view = inflater.inflate(R.layout.activity_cum_rap, container, false);
        listView = view.findViewById(R.id.expandableListView);

        // goi API de GET thong tin
        getDanhSachRap = new Thread() {
            @Override
            public void run() {
                super.run();
                ModelRap modelRap = new ModelRap();
                try {
                    listCumRap = modelRap.getThongTinCumRap();

                    heThongRaps = new HashMap<>();
//                    listRap = new ArrayList<>();
                    for (CumRap i : listCumRap) {
                        ArrayList<RapDetail> rapDetails = modelRap.getRapDetail(i.getMaHeThongRap());
                        heThongRaps.put(i, rapDetails);

                    }
                    expandableListRapAdapter = new ExpandableListRapAdapter(getContext(), listCumRap, heThongRaps);


                    if (getActivity() != null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
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
