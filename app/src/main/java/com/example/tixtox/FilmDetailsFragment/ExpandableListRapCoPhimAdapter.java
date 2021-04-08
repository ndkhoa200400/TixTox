package com.example.tixtox.FilmDetailsFragment;

import android.content.Context;
import android.content.Intent;
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
import com.example.tixtox.HeThongRapFragment.RapDetailActivity;
import com.example.tixtox.Model.ModelRap;
import com.example.tixtox.Model.Rap.CumRap;
import com.example.tixtox.Model.Rap.RapDetail;
import com.example.tixtox.R;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ExpandableListRapCoPhimAdapter extends BaseExpandableListAdapter {
    private Context context;

    private ArrayList<String> listCumRap;
    private HashMap<String, HashMap<String, ArrayList<Date>>> thongTinPhim;
    public ExpandableListRapCoPhimAdapter(@NonNull Context context, ArrayList<String> cumRap, HashMap<String, HashMap<String, ArrayList<Date>>> thongTinPhim) {
        this.context = context;

        this.listCumRap = cumRap;
        this.thongTinPhim = thongTinPhim;
    }


    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent){
        if (convertView == null)
        {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.layout_item_cum_rap, null);
        }

        TextView tenCumRap = convertView.findViewById(R.id.txtTenCumRap);
        CumRap cumRap = (CumRap) this.getGroup(listPosition);
        tenCumRap.setText(cumRap.getTenHeThongRap());
        ImageView logo = convertView.findViewById(R.id.logo);
        Glide.with(logo.getContext()).load(cumRap.getLogo()).into(logo);

        return convertView;
    }


    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null)
        {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.layout_item_rap_phim, null);
        }
        TextView txtTenRapDetail = convertView.findViewById(R.id.txtTenRapDetail);

        RapDetail rapDetail = (RapDetail) getChild(groupPosition, childPosition);

        txtTenRapDetail.setText(rapDetail.getTenRap());

        ArrayList<ModelGioChieu> listGioChieu = new ArrayList<>();

        LinearLayout linear = convertView.findViewById(R.id.layoutChonGioChieu);
        LinearLayoutManager layoutManager = new LinearLayoutManager(linear.getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = convertView.findViewById(R.id.recyclerViewGioChieu);
        recyclerView.setLayoutManager(layoutManager);
        ListGioChieuAdapter adapter = new ListGioChieuAdapter(context, listGioChieu);
        recyclerView.setAdapter(adapter);



        return convertView;
    }

    @Override
    public int getGroupCount() {
        return this.listCumRap.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if(thongTinPhim != null)
            return this.thongTinPhim.get(this.listCumRap.get(groupPosition)).size();
        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listCumRap.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
       // return this.listRapDetail.get(this.listCumRap.get(groupPosition)).get(childPosition);
        return null;
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
