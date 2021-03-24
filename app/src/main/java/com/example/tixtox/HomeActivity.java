package com.example.tixtox;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    //list poster image
    private GridView gridView;
    private ArrayList<ImageModel> arrayList;

    private TabLayout tabHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabHome = (TabLayout) findViewById(R.id.tabHome);
        tabHome.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tabPosition = tabHome.getSelectedTabPosition();
//                if(tabPosition == 0)
//                    loadPoster(posterPhimDangChieu);
//                else loadPoster(posterPhimSapChieu);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

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
                            ArrayList<String> danhGias = new ArrayList<>();
                            for(Phim p: phims)
                            {
                                filmNames.add(p.getTenPhim());
                                posters.add(p.getHinhAnh());
                                danhGias.add(p.getDanhGia());
                            }
                            HomeActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                        loadPoster2(posters, filmNames, danhGias);
                                    System.out.println(phims.get(0).getTenPhim());
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

//    private void loadPoster(int listImage[]){
//        gridView = (GridView) findViewById(R.id.gridView);
//        arrayList = new ArrayList<>();
//        for (int i = 0; i < listImage.length; i++) {
//            ImageModel imagemodel = new ImageModel();
//            imagemodel.setmThumbIds(listImage[i]);
//            //add in array list
//            arrayList.add(imagemodel);
//        }
//        ImageAdapter adapter= new ImageAdapter(getApplicationContext(), arrayList);
//        gridView.setAdapter(adapter);
//        //item click listener
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView parent, View view, int position, long id) {
//                System.out.println(position);
//            }
//        });
//    }
    private void loadPoster2(ArrayList<String> posters, ArrayList<String> filmNames, ArrayList<String> danhGias){
        gridView = (GridView) findViewById(R.id.gridView);

        ImageAdapter adapter= new ImageAdapter(getApplicationContext(), posters, filmNames, danhGias);
        gridView.setAdapter(adapter);
        //item click listener
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                System.out.println(position);
            }
        });
    }
}