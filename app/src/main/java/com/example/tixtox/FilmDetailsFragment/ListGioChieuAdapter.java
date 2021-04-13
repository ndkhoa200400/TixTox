package com.example.tixtox.FilmDetailsFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tixtox.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ListGioChieuAdapter extends RecyclerView.Adapter<ListGioChieuAdapter.ViewHolder> {
    private ArrayList<ModelGioChieu> listGioChieu;
    private Context context;

    public ListGioChieuAdapter(Context context, ArrayList<ModelGioChieu> listGioChieu) {
        this.context = context;
        this.listGioChieu = listGioChieu;
    }

    @NonNull
    @Override
    public ListGioChieuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_gio_chieu, parent, false);
        return new ListGioChieuAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.gioBD.setText(this.listGioChieu.get(position).getGioBD());

    }

    @Override
    public int getItemCount() {
        return this.listGioChieu.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView gioBD, gioKT;

        public ViewHolder(View itemView) {
            super(itemView);
            gioBD = itemView.findViewById(R.id.gioBD);

        }
    }


}