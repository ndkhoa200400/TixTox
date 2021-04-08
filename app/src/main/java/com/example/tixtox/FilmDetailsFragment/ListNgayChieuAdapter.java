package com.example.tixtox.FilmDetailsFragment;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tixtox.Model.ModelPhim;
import com.example.tixtox.Model.Phim;
import com.example.tixtox.R;

import org.json.JSONException;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class ListNgayChieuAdapter extends RecyclerView.Adapter<ListNgayChieuAdapter.ViewHolder> {
    private ArrayList<ModelNgay> listNgay;
    private Context context;
    private Phim phim;
    public ListNgayChieuAdapter(Context context, ArrayList<ModelNgay> listNgay ,Phim phim){
        this.context = context;
        this.listNgay = listNgay;
        this.phim = phim;
    }

    @NonNull
    @Override
    public ListNgayChieuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_ngay, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListNgayChieuAdapter.ViewHolder holder, int position) {
        holder.thu.setText(this.listNgay.get(position).getThu());
        holder.ngay.setText(this.listNgay.get(position).getNgay());
        holder.thang.setText(this.listNgay.get(position).getThang());
        holder.cardViewItemNgay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try {
                    ModelPhim modelPhim = ModelPhim.getInstance();
                    new Thread()
                    {
                        @Override
                        public void run() {
                            try {
                                modelPhim.getThongTinPhim(phim.getMaPhim());

                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return this.listNgay.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView thu, ngay, thang;
        CardView cardViewItemNgay;
        public ViewHolder(View itemView) {
            super(itemView);
            thu = itemView.findViewById(R.id.thu);
            ngay = itemView.findViewById(R.id.ngay);
            thang = itemView.findViewById(R.id.thang);
            cardViewItemNgay = itemView.findViewById(R.id.cardViewItemNgay);
        }
    }
}
