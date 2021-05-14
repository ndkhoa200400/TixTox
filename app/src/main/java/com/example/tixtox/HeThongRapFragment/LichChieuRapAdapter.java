package com.example.tixtox.HeThongRapFragment;

import android.app.Activity;
import android.content.Context;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tixtox.DatVe.SuatChieu;
import com.example.tixtox.FilmDetailsFragment.ExpandableListRapCoPhimAdapter;
import com.example.tixtox.FilmDetailsFragment.ListGioChieuAdapter;
import com.example.tixtox.FilmDetailsFragment.ModelGioChieu;
import com.example.tixtox.FilmDetailsFragment.ModelNgay;
import com.example.tixtox.Model.ModelPhim;
import com.example.tixtox.Model.ModelRap;
import com.example.tixtox.Model.Phim;
import com.example.tixtox.Model.Rap.RapDetail;
import com.example.tixtox.R;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

// Class xử lý khi người dùng chọn vào 1 ngày để hiện thông tin chiếu của một rạp detail
public class LichChieuRapAdapter extends RecyclerView.Adapter<LichChieuRapAdapter.ViewHolder> {
    private ArrayList<ModelNgay> listNgay;
    private Context context;

    int index = -1;
    private ModelRap modelRap;
    private String maRapDetail;
    private String maCumRap;
    private HashMap<String, ArrayList<Date>> thongTinLichChieu; // lưu trữ thông tin lịch chiếu

    public LichChieuRapAdapter(Context context, ArrayList<ModelNgay> listNgay, String maRapDetail, String maCumRap) throws IOException {
        this.context = context;
        this.listNgay = listNgay;
        thongTinLichChieu = null;
        modelRap = ModelRap.getInstance();
        this.maCumRap = maCumRap;
        this.maRapDetail = maRapDetail;
        System.out.println("Line 53 LichChieuRap");

    }

    @NonNull
    @Override
    public LichChieuRapAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_ngay, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.thu.setText(this.listNgay.get(position).getThu());
        holder.ngay.setText(this.listNgay.get(position).getNgay());
        holder.thang.setText(this.listNgay.get(position).getThang());
        holder.cardViewItemNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = position;
                notifyDataSetChanged();

                ProgressBar progressBar = ((Activity) context).findViewById(R.id.progressBar6);
                progressBar.setVisibility(View.VISIBLE);
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        if (thongTinLichChieu == null) {
                            try {
                                thongTinLichChieu = modelRap.getThongTinLichChieuHeThongRap(maCumRap, maRapDetail);
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        HashMap<String, ArrayList<Date>> results = new HashMap<>();
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        String selectedDate = format.format(listNgay.get(position).getDate());
                        // Quét qua từng tên phim để lấy lịch chiếu
                        for (String tenPhim : thongTinLichChieu.keySet()) {
                            ArrayList<Date> validDates = new ArrayList<>();
                            for (Date date : thongTinLichChieu.get(tenPhim)) {
                                if (format.format(date).equals(selectedDate)) {
                                    if (!validDates.contains(date))
                                        validDates.add(date);

                                }
                            }
                            if (!validDates.isEmpty()) {
                                Collections.sort(validDates);
                                results.put(tenPhim, validDates);
                            }

                        }
                        System.out.println("Lich chieu rap adapter");
                        System.out.println(results);

                        Activity activity = (Activity) context;
                        // Lấy thông tin lịch chiếu và render lên
                        if (activity != null) {
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {

                                        TextView txtKhongCoPhim = activity.findViewById(R.id.txtKhongCoPhim);
                                        ListView listView = activity.findViewById(R.id.listviewSuatChieuPhim);

                                        RapDetail rapDetail = modelRap.getMotRapDetail(maCumRap, maRapDetail);
                                        ArrayList<String> availablePhims = new ArrayList<>();

                                        for (String result : results.keySet()) {
                                            availablePhims.add(result);
                                        }
                                        if (availablePhims.size() > 0) {
                                            txtKhongCoPhim.setText("");
                                        } else {
                                            if (txtKhongCoPhim != null) {
                                                txtKhongCoPhim.setText("Hiện không có lịch chiếu!");

                                            }

                                        }
                                        String tenRapDetail = rapDetail.getTenRap();
                                        SuatChieuPhimAdapter adapter = new SuatChieuPhimAdapter(activity, R.layout.layout_suat_chieu_rap, availablePhims, results, tenRapDetail,
                                                listNgay.get(index).getNgay() + "/" + listNgay.get(index).getThang() + "/" + listNgay.get(index).getNam());

                                        listView.setAdapter(adapter);
                                        progressBar.setVisibility(View.GONE);
                                    } catch (Exception e) {
                                        System.out.println("Error in line 145 - ListChieu rapAdapter");
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }

                    }

                }.start();
            }
        });
        if (index == position) {
            holder.cardViewItemNgay.setCardBackgroundColor(Color.parseColor("#003D98"));
            holder.thu.setTextColor(Color.WHITE);
            holder.ngay.setTextColor(Color.WHITE);
            holder.thang.setTextColor(Color.WHITE);
        } else {
            holder.cardViewItemNgay.setCardBackgroundColor(Color.WHITE);
            holder.thu.setTextColor(Color.BLACK);
            holder.ngay.setTextColor(Color.BLACK);
            holder.thang.setTextColor(Color.BLACK);
        }
    }

    @Override
    public int getItemCount() {
        return this.listNgay.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
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
