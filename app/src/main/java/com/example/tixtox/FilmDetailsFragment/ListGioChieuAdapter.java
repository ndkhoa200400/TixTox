package com.example.tixtox.FilmDetailsFragment;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tixtox.Model.Phim;
import com.example.tixtox.MuaVeActivity;
import com.example.tixtox.R;

import java.util.ArrayList;

public class ListGioChieuAdapter extends RecyclerView.Adapter<ListGioChieuAdapter.ViewHolder> {
    private ArrayList<ModelGioChieu> listGioChieu;
    private Context context;
    public Phim phim;
    public String NgayChieu;
    public String CumRap;
    public ListGioChieuAdapter(Context context, ArrayList<ModelGioChieu> listGioChieu,Phim mphim, String CumRap) {
        this.context = context;
        this.listGioChieu = listGioChieu;
        this.phim = mphim;
        this.CumRap = CumRap;
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
        holder.gioBD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,MuaVeActivity.class);
                intent.putExtra("Phim_name", phim.getMaPhim());
                context.startActivity(intent);
            }
        });

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
