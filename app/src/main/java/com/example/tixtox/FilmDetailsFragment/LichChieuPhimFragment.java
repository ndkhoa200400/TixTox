package com.example.tixtox.FilmDetailsFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tixtox.Model.Phim;
import com.example.tixtox.R;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class LichChieuPhimFragment extends Fragment {
    private Phim phim;
    TextView tenPhim;
    private ArrayList<ModelNgay> listNgay = new ArrayList<>();

    public LichChieuPhimFragment(){}

    public static LichChieuPhimFragment newInstance() {
        LichChieuPhimFragment fragment = new LichChieuPhimFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    //Tang ngay len 1
    public String increaseDate(String date, int unit){
        String dt = "";
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Calendar c = Calendar.getInstance();
            c.setTime(sdf.parse(date));
            c.add(Calendar.DATE, unit);  // number of days to add
            dt = sdf.format(c.getTime());  // dt is now the new date
        }catch (Exception e){
            e.printStackTrace();
        }
        return dt;
    }
    //get thu, ngay, thang tu dd-MM-yyy
    public ModelNgay getDateFromPattern(String date){
        ModelNgay ngay = new ModelNgay();
        String []t = date.split("-");

        ngay.setNgay(t[0]);
        ngay.setThang(t[1]);
        ngay.setNam(t[2]);
        SimpleDateFormat format1=new SimpleDateFormat("dd-MM-yyyy");
        Date dt1= null;
        try {
            dt1 = format1.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat format2=new SimpleDateFormat("EE");
        ngay.setThu(format2.format(dt1));

        return ngay;
    }
    //Khoi tao data 7 ngay ke tu ngay hien tai
    public void createDateData(){

        //ngay thang nam hien tai
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String currentDateandTime = sdf.format(new Date());

        listNgay.add(getDateFromPattern(currentDateandTime));

        for(int i = 0; i < 15; i++){
            String date = increaseDate(currentDateandTime, i + 1);
            listNgay.add(getDateFromPattern(date));
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.activity_lich_chieu_phim, container, false);
        tenPhim = view.findViewById(R.id.tenPhim);
        tenPhim.setText(phim.getTenPhim());
        ProgressBar progressBar = view.findViewById(R.id.progressBar6);

        createDateData(); //khoi tao data listNgay
        LinearLayout linear = view.findViewById(R.id.layoutChonNgay);
        LinearLayoutManager layoutManager = new LinearLayoutManager(linear.getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = view.findViewById(R.id.listViewChonNgay);
        recyclerView.setLayoutManager(layoutManager);
        ListNgayChieuAdapter adapter = null;
        try {
            adapter = new ListNgayChieuAdapter(getActivity(), listNgay, phim);
        } catch (IOException e) {
            e.printStackTrace();
        }

        recyclerView.setAdapter(adapter);

        progressBar.setVisibility(View.GONE);
        return view;
    }

    public void setPhim(Phim p)
    {
        this.phim = p;
    }
}
