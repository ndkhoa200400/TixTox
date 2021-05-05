package com.example.tixtox.HomeFragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.example.tixtox.ImageAdapter;
import com.example.tixtox.Model.ModelPhim;
import com.example.tixtox.Model.Phim;
import com.example.tixtox.R;
import com.example.tixtox.ThongTinPhimActivity;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PhimsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PhimsFragment extends Fragment {
    private GridView gridView;
    private ProgressBar progressBar;
    private TabLayout tabLayout;
    private ArrayList<Phim> phims;
    private ModelPhim modelPhim;
    private SearchView searchViewFilm;
    private CardView searchBarContainer;
    private ArrayList<String> posters, filmNames, ratings;
    private int tabPosition = 0;
    private Thread loadingPhimThread;
    public PhimsFragment() {
        // Required empty public constructor
    }


    public static PhimsFragment newInstance() {
        PhimsFragment fragment = new PhimsFragment();
        Bundle args = new Bundle();


        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        loadingPhimThread = new Thread(){
            @Override
            public void run() {
                try {
                    modelPhim = ModelPhim.getInstance();
                    getPhims(modelPhim.getPhimDangChieu());
                    modelPhim.setPhimSapChieu(modelPhim.getPhimSapChieu());
                } catch (IOException | ParseException e) {
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

        progressBar = view.findViewById(R.id.processbar_phims);
        progressBar.setVisibility(View.VISIBLE);
        gridView = (GridView) view.findViewById(R.id.gridView);
        searchViewFilm = view.findViewById(R.id.searchViewFilm);
        searchViewFilm.setSubmitButtonEnabled(true);
        searchBarContainer = view.findViewById(R.id.searchBarContainer);
        
        tabLayout = view.findViewById(R.id.tabPhims);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                progressBar.setVisibility(View.VISIBLE);
                tabPosition = tab.getPosition();
                if (tabPosition == 0){
                    try {
                        getPhims(modelPhim.getPhimDangChieu());
                    } catch (IOException | ParseException e) {
                        e.printStackTrace();
                    }
                }
                else{

                    try {
                        getPhims(modelPhim.getPhimSapChieu());
                    } catch (IOException | ParseException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        searchViewFilm.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchViewFilm.clearFocus();
                ArrayList<String> filmsSearchList = new ArrayList<>(), postersSearchList = new ArrayList<>(), ratingsSearchList = new ArrayList<>();
                for(int i = 0; i < filmNames.size(); i++){
                    if(filmNames.get(i).toLowerCase().contains(query.toLowerCase())){
                        filmsSearchList.add(filmNames.get(i));
                        postersSearchList.add(posters.get(i));
                        ratingsSearchList.add(ratings.get(i));
                    }
                }

                loadPhim(postersSearchList, filmsSearchList, ratingsSearchList);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText == null || newText.isEmpty()){
                    loadPhim(posters, filmNames, ratings);
                }
                return false;
            }
        });

        searchBarContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchViewFilm.setIconified(false);
            }
        });
        return view;
    }


    private void loadPhim(ArrayList<String> posters, ArrayList<String> filmNames, ArrayList<String> ratings){


        ImageAdapter adapter= new ImageAdapter(getContext(), posters, filmNames, ratings);
        gridView.setAdapter(adapter);
        //item click listener
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ThongTinPhimActivity.class);
                int pos = 0;
                for(int i = 0; i < phims.size(); i++){
                    if(phims.get(i).getTenPhim().equals(filmNames.get(position))){
                        pos = i;
                        break;
                    }
                }

                intent.putExtra("phim", phims.get(pos));
                startActivity(intent);
            }
        });
    }

    private void getPhims(ArrayList<Phim> phims) throws IOException {

        if (phims!= null)
        {
            posters = new ArrayList<>();
            filmNames = new ArrayList<>();
            ratings = new ArrayList<>();
            for(Phim p: phims)
            {
                filmNames.add(p.getTenPhim());
                posters.add(p.getHinhAnh());
                ratings.add(p.getDanhGia());
            }
            this.phims = phims;
            if (getActivity() != null)
            {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        try {
                            loadPhim(posters, filmNames, ratings);
                        }catch(Exception e)
                        {
                            e.printStackTrace();
                        }

                    }
                });

            }


        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (this.loadingPhimThread.isInterrupted())
            loadingPhimThread.start();
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onPause() {
        super.onPause();

    }
}