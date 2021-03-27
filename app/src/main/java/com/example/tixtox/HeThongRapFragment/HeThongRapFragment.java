package com.example.tixtox.HeThongRapFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.tixtox.Model.ModelRap;
import com.example.tixtox.Model.Rap;
import com.example.tixtox.R;

import java.io.IOException;
import java.util.ArrayList;

public class HeThongRapFragment extends Fragment {
    private ListView listView;
    private ArrayList<RapDetail> listRap;
    ListRapAdapter listRapAdapter;
    Thread getDanhSachRap;

    public HeThongRapFragment(){}
    public static HeThongRapFragment newInstance(){
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
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.activity_cum_rap, container, false);
        listView = view.findViewById(R.id.listView);

        // goi API de GET thong tin
        ModelRap modelRap = new ModelRap();
        try {
            ArrayList<Rap> dsRap = modelRap.getThongTinRap();
            for(Rap i: dsRap){
                //listRap.get(dsRap.indexOf(i)).setLogo(i.getLogo());
                listRap.get(dsRap.indexOf(i)).setTenRap(i.getTenHeThongRap());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        listRapAdapter = new ListRapAdapter(getActivity().getApplicationContext(), R.layout.layout_item_rap_phim, listRap);

        listView.setAdapter(listRapAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
        return view;
    }
}
