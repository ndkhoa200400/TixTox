package com.example.tixtox.AccountFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tixtox.Model.KhuyenMai;
import com.example.tixtox.R;

import java.util.ArrayList;

public class KhuyenMaiAdapter extends ArrayAdapter<KhuyenMai> {

    Context context;
    ArrayList<KhuyenMai> khuyenMaiArrayList;
    int resouce;
    public KhuyenMaiAdapter(@NonNull Context context, int resource, ArrayList<KhuyenMai> khuyenMaiArrayList) {
        super(context, resource, khuyenMaiArrayList);
        this.resouce = resource;
        this.khuyenMaiArrayList = khuyenMaiArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(this.resouce, parent, false);
        }

        TextView txtTenKM = convertView.findViewById(R.id.txtTenKM);
        TextView txtSoTienGiam = convertView.findViewById(R.id.txtSoTienGiam);
        TextView txtPhanTram = convertView.findViewById(R.id.txtPhanTram);

        KhuyenMai km = khuyenMaiArrayList.get(position);

        txtTenKM.setText(km.getName());

        txtSoTienGiam.setText( ""+km.getDiscount());

        if (km.getType() == 1)
            txtPhanTram.setText("%");
        else
            txtPhanTram.setText("VND");
        return convertView;
    }
}
