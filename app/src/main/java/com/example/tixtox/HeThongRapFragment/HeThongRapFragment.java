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
        getDanhSachRap = new Thread(){
            @Override
            public void run() {
                super.run();
                ModelRap modelRap = new ModelRap();
                try {
                    ArrayList<Rap> dsRap = modelRap.getThongTinRap();
//                    listRap = new ArrayList<>();
//                    for(Rap i: dsRap){
//                        //listRap.get(dsRap.indexOf(i)).setLogo(i.getLogo());
//                        listRap.get(dsRap.indexOf(i)).setTenRap(i.getTenHeThongRap());
//                    }

                    if(getActivity() != null)
                    {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                listRapAdapter = new ListRapAdapter(getActivity(), R.layout.layout_item_rap_phim, dsRap);
                                System.out.println(listRapAdapter);
                                System.out.println("ZO");
                                System.out.println(dsRap.get(0).getTenHeThongRap());
                                listView.setAdapter(listRapAdapter);
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                    }
                                });
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
