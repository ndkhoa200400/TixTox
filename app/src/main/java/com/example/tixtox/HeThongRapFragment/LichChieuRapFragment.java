package com.example.tixtox.HeThongRapFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.tixtox.FilmDetailsFragment.ModelNgay;
import com.example.tixtox.R;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

// Fragment hiện các suất chiếu của một rạp detail
public class LichChieuRapFragment extends Fragment {
    private ArrayList<ModelNgay> listNgay = new ArrayList<>();
    String maRapDetail, maCumRap;

    public LichChieuRapFragment() {
        // Required empty public constructor
    }

    public static LichChieuRapFragment newInstance(String maRapDetail, String maCumRap) {
        LichChieuRapFragment fragment = new LichChieuRapFragment();
        fragment.maCumRap = maCumRap;
        fragment.maRapDetail = maRapDetail;
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lich_chieu_rap, container, false);
        ProgressBar progressBar = view.findViewById(R.id.progressBar6);

        createDateData(); //khoi tao data listNgay
        LinearLayout linear = view.findViewById(R.id.layoutChonNgayRap);
        LinearLayoutManager layoutManager = new LinearLayoutManager(linear.getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = view.findViewById(R.id.listViewChonNgayRap);
        recyclerView.setLayoutManager(layoutManager);

        LichChieuRapAdapter adapter = null;
        try {
            adapter = new LichChieuRapAdapter(getActivity(), listNgay, maRapDetail, maCumRap);
            recyclerView.setAdapter(adapter);
        } catch (IOException e) {
            e.printStackTrace();
        }


        progressBar.setVisibility(View.GONE);
        return view;
    }

    public void createDateData() {

        //ngay thang nam hien tai
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String currentDateandTime = sdf.format(new Date());

        listNgay.add(getDateFromPattern(currentDateandTime));

        for (int i = 0; i < 15; i++) {
            String date = increaseDate(currentDateandTime, i + 1);
            listNgay.add(getDateFromPattern(date));
        }
    }

    //Tang ngay len 1
    public String increaseDate(String date, int unit) {
        String dt = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Calendar c = Calendar.getInstance();
            c.setTime(sdf.parse(date));
            c.add(Calendar.DATE, unit);  // number of days to add
            dt = sdf.format(c.getTime());  // dt is now the new date
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dt;
    }

    //get thu, ngay, thang tu dd-MM-yyy
    public ModelNgay getDateFromPattern(String date) {
        ModelNgay ngay = new ModelNgay();
        String[] t = date.split("-");

        ngay.setNgay(t[0]);
        ngay.setThang(t[1]);
        ngay.setNam(t[2]);
        SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
        Date dt1 = null;
        try {
            dt1 = format1.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat format2 = new SimpleDateFormat("EE");
        ngay.setThu(format2.format(dt1));

        return ngay;
    }
}