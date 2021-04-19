package com.example.tixtox.DatVe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tixtox.R;

import java.util.List;

public class AdapterVeXemPhim extends BaseAdapter {
    private Context context;
    private int layout;
    private List<VeXemPhim>  arraylist;


    public AdapterVeXemPhim(Context context, int layout, List<VeXemPhim> arraylist) {
        this.context = context;
        this.layout = layout;
        this.arraylist = arraylist;
    }



    @Override
    public int getCount() {
        return arraylist.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(layout,null);
        VeXemPhim ve =  arraylist.get(position);
        TextView txtTenPhim = (TextView) convertView.findViewById(R.id.txtTenPhim_Ve);
        TextView txtNgayThanhToan = (TextView) convertView.findViewById(R.id.txtNgayThanhToan_Ve);
        TextView txtTenRap = (TextView) convertView.findViewById(R.id.txtRap_ve);
        TextView txtTrangThai = (TextView) convertView.findViewById(R.id.txtTrangThai_Ve);

        txtTenPhim.setText(ve.getPhim());
        txtTenRap.setText(ve.getRapphim());
        txtNgayThanhToan.setText(ve.ThoiGian);
        txtTrangThai.setText(ve.getTrangThai());
        return convertView;
    }
}
