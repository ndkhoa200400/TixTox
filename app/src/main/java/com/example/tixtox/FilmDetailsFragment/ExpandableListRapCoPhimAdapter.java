package com.example.tixtox.FilmDetailsFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tixtox.Model.ModelRap;
import com.example.tixtox.Model.Phim;
import com.example.tixtox.Model.Rap.RapDetail;
import com.example.tixtox.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class ExpandableListRapCoPhimAdapter extends BaseExpandableListAdapter {
    private Context context;
    public Phim phim;
    private String NgayChieu;
    private ArrayList<String> listCumRap;
    private HashMap<String, HashMap<String, ArrayList<Date>>> thongTinPhim;
    private ArrayList<ArrayList<String>> rapDetailsCuaMotCumRap;
    private int currentClick = 0;

    public ExpandableListRapCoPhimAdapter(@NonNull Context context, ArrayList<String> cumRap, HashMap<String, HashMap<String, ArrayList<Date>>> thongTinPhim, Phim mphim, String mNgaychieu) {
        this.context = context;

        this.listCumRap = cumRap;
        this.thongTinPhim = thongTinPhim;
        this.phim = mphim;
        this.NgayChieu = mNgaychieu;
        rapDetailsCuaMotCumRap = new ArrayList<>();
        for (String heThongRap : cumRap) {
            ArrayList<String> rapDetails = new ArrayList<>();
            for (String rap : thongTinPhim.get(heThongRap).keySet()) {
                rapDetails.add(rap);
            }

            rapDetailsCuaMotCumRap.add(rapDetails);

        }
    }


    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.layout_item_cum_rap, null);
        }

        TextView tenCumRap = convertView.findViewById(R.id.txtTenCumRap);
        String cumRap = (String) this.getGroup(listPosition);
        tenCumRap.setText(cumRap);
        ImageView logo = convertView.findViewById(R.id.logo);
        try {
            String logoURL = ModelRap.getInstance().timCumRapTheoMa(cumRap).getLogo();
            Glide.with(logo.getContext()).load(logoURL).into(logo);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return convertView;
    }


    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.layout_item_rap_phim, null);
        }
        TextView txtTenRapDetail = convertView.findViewById(R.id.txtTenRapDetail);
        String tenRapDetail = (String) getChild(groupPosition, childPosition);


        txtTenRapDetail.setText(tenRapDetail);


        ArrayList<Date> listGioChieu = this.thongTinPhim.get(listCumRap.get(groupPosition)).get(tenRapDetail);

        ArrayList<ModelGioChieu> modelGioChieuArrayList = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.UK);
        for (Date gioChieu : listGioChieu) {
            ModelGioChieu modelGioChieu = new ModelGioChieu();
            modelGioChieu.setGioBD(format.format(gioChieu));
            modelGioChieuArrayList.add(modelGioChieu);
        }


        LinearLayout linear = convertView.findViewById(R.id.layoutChonGioChieu);
        LinearLayoutManager layoutManager = new LinearLayoutManager(linear.getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = convertView.findViewById(R.id.recyclerViewGioChieu);
        recyclerView.setLayoutManager(layoutManager);
        ListGioChieuAdapter adapter = new ListGioChieuAdapter(context, modelGioChieuArrayList, phim, listCumRap.get(groupPosition), NgayChieu);
        recyclerView.setAdapter(adapter);


        return convertView;
    }

    @Override
    public int getGroupCount() {
        return this.listCumRap.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (thongTinPhim != null)
            return this.thongTinPhim.get(this.listCumRap.get(groupPosition)).size();
        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listCumRap.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        // Trả về từng cụm rạp với thời gian.
        return this.rapDetailsCuaMotCumRap.get(groupPosition).get(childPosition);


    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
