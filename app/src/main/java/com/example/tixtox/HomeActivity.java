package com.example.tixtox;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    //list poster image
    private int posterPhimDangChieu[] = {R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground};
    private int posterPhimSapChieu[] = {R.drawable.icon_facebook, R.drawable.icon_facebook, R.drawable.icon_facebook, R.drawable.icon_facebook, R.drawable.icon_facebook, R.drawable.icon_facebook, R.drawable.icon_facebook, R.drawable.icon_facebook, R.drawable.icon_facebook, R.drawable.icon_facebook, R.drawable.icon_facebook, R.drawable.icon_facebook, R.drawable.icon_facebook, R.drawable.icon_facebook, R.drawable.icon_facebook, R.drawable.icon_facebook};
    private GridView gridView;
    private ArrayList<ImageModel> arrayList;

    private TabLayout tabHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadPoster(posterPhimDangChieu);

        tabHome = (TabLayout) findViewById(R.id.tabHome);
        tabHome.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tabPosition = tabHome.getSelectedTabPosition();
                if(tabPosition == 0)
                    loadPoster(posterPhimDangChieu);
                else loadPoster(posterPhimSapChieu);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void loadPoster(int listImage[]){
        gridView = (GridView) findViewById(R.id.gridView);
        arrayList = new ArrayList<>();
        for (int i = 0; i < listImage.length; i++) {
            ImageModel imagemodel = new ImageModel();
            imagemodel.setmThumbIds(listImage[i]);
            //add in array list
            arrayList.add(imagemodel);
        }
        ImageAdapter adapter= new ImageAdapter(getApplicationContext(), arrayList);
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