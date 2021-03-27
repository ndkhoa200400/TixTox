package com.example.tixtox.HeThongRapFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.tixtox.R;

import java.util.ArrayList;
import java.util.List;

public class ListRapAdapter  extends ArrayAdapter<RapDetail> {
    private List<RapDetail> listRap;
    private Context context;
    private int layoutResource;

    public ListRapAdapter(@NonNull Context context, int resource, ArrayList<RapDetail> objects) {
        super(context, resource);
        this.context = context;
        this.layoutResource = resource;
        this.listRap = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(layoutResource, null);

        ImageView logo = convertView.findViewById(R.id.logo);
        logo.setImageResource(listRap.get(position).getLogo());

        TextView tenRap = convertView.findViewById(R.id.tenRap);
        tenRap.setText(listRap.get(position).getTenRap());

        return convertView;
    }
}
