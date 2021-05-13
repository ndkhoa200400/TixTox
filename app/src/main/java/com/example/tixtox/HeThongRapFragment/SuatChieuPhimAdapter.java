package com.example.tixtox.HeThongRapFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tixtox.FilmDetailsFragment.ListGioChieuAdapter;
import com.example.tixtox.FilmDetailsFragment.ModelGioChieu;
import com.example.tixtox.Model.ModelPhim;
import com.example.tixtox.Model.Phim;
import com.example.tixtox.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class SuatChieuPhimAdapter extends ArrayAdapter<String> {
    ArrayList<String> listTenPhim;
    HashMap<String, ArrayList<Date>> thongTinSuatChieu;
    Context context;
    int resouce;
    String tenRapDetail;
    ModelPhim modelPhim;
    String ngayChieu;

    public SuatChieuPhimAdapter(@NonNull Context context, int resource, ArrayList<String> listTenPhim, HashMap<String, ArrayList<Date>> thongTinSuatChieu, String tenRapDetail, String ngayChieu) throws IOException {
        super(context, resource, listTenPhim);
        this.context = context;
        this.listTenPhim = listTenPhim;
        this.thongTinSuatChieu = thongTinSuatChieu;
        this.resouce = resource;
        this.tenRapDetail = tenRapDetail;
        this.ngayChieu = ngayChieu;
        modelPhim = ModelPhim.getInstance();

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(this.resouce, parent, false);
        }

        String tenPhim = this.listTenPhim.get(position);
        TextView txtTenPhim = convertView.findViewById(R.id.txtTenPhim);
        txtTenPhim.setText(tenPhim);
        LinearLayout linear = convertView.findViewById(R.id.layoutChonGioChieu);

        LinearLayoutManager layoutManager = new LinearLayoutManager(linear.getContext(), LinearLayoutManager.HORIZONTAL, false);

        // Set ngày giờ chiếu
        ArrayList<Date> listGioChieu = this.thongTinSuatChieu.get(listTenPhim.get(position));
        ArrayList<ModelGioChieu> modelGioChieuArrayList = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.UK);
        for (Date gioChieu : listGioChieu) {
            ModelGioChieu modelGioChieu = new ModelGioChieu();
            modelGioChieu.setGioBD(format.format(gioChieu));
            modelGioChieuArrayList.add(modelGioChieu);
        }


        // Hiện các thông tin giờ chiếu qua recyclerView
        RecyclerView recyclerView = convertView.findViewById(R.id.recyclerViewGioChieu);
        recyclerView.setLayoutManager(layoutManager);
        Phim phim = modelPhim.timPhimTheoTen(tenPhim);
        ListGioChieuAdapter adapter = new ListGioChieuAdapter(context, modelGioChieuArrayList, phim, tenRapDetail, ngayChieu);
        recyclerView.setAdapter(adapter);

        return convertView;

    }


}
