package com.example.tixtox.FilmDetailsFragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tixtox.Model.ModelPhim;
import com.example.tixtox.Model.ModelRap;
import com.example.tixtox.Model.Phim;
import com.example.tixtox.R;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;


// Class xử lý khi người dùng chọn vào 1 ngày để hiện thông tin chiếu của một phim
public class ListNgayChieuAdapter extends RecyclerView.Adapter<ListNgayChieuAdapter.ViewHolder> {
    private ArrayList<ModelNgay> listNgay;
    private Context context;
    private Phim phim;
    private HashMap<String, HashMap<String, ArrayList<Date>>> thongTinLichChieu;
    int index = -1;
    ModelPhim modelPhim;
    ModelRap modelRap;

    public ListNgayChieuAdapter(Context context, ArrayList<ModelNgay> listNgay, Phim phim) throws IOException {
        this.context = context;
        this.listNgay = listNgay;
        this.phim = phim;
        modelPhim = ModelPhim.getInstance();
        thongTinLichChieu = null;
        modelRap = ModelRap.getInstance();

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
                        if (thongTinLichChieu == null) {
                            try {
                                thongTinLichChieu = modelPhim.getThongTinPhim(phim.getMaPhim());
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        ArrayList<String> cumRaps = new ArrayList<>();
                        HashMap<String, HashMap<String, ArrayList<Date>>> results = new HashMap<>(); // kết quả trả về các thông tin lịch chiếu của ngày được click

                        for (String str : thongTinLichChieu.keySet()) {
                            cumRaps.add(str);
                        }
                        Date selectedDate = listNgay.get(position).getDate();

                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        for (String cumRap : cumRaps) {
                            // cumRap: là một hệ thống rạp - VD: CGV, BHD
                            // maRapPhim: là phòng phim của một chi nhánh rạp. VD: 771 => Rạp 1 của Galaxy Nguyễn Văn Quá

                            HashMap<String, ArrayList<Date>> maRapPhim = thongTinLichChieu.get(cumRap); // Lấy các mã rạp phi của 1  rạp detail từ một cụm rạp
                            HashMap<String, ArrayList<Date>> thongTinChieuCuaMotRap = new HashMap<>();

                            for (String maRapDetail : thongTinLichChieu.get(cumRap).keySet()) {
                                String tenRapDetail = modelRap.getMotRapDetailDuaTrenPhongRap(cumRap, maRapDetail).getTenRap();
                                ArrayList<Date> times = maRapPhim.get(maRapDetail);

                                ArrayList<Date> validDates = new ArrayList<>();

                                for (Date time : times) {

                                    if (format.format(time).equals(format.format(selectedDate))) {
                                        validDates.add(time);
                                    }
                                }
                                if (!validDates.isEmpty()) {
                                    if (thongTinChieuCuaMotRap.containsKey(tenRapDetail)) {
                                        ArrayList<Date> temp = thongTinChieuCuaMotRap.get(tenRapDetail);
                                        validDates.addAll(temp);
                                        Collections.sort(validDates);
                                    }
                                    Set<Date> set = new LinkedHashSet<Date>();
                                    set.addAll(validDates);
                                    validDates.clear();
                                    validDates.addAll(set);

                                    thongTinChieuCuaMotRap.put(tenRapDetail, validDates);
                                }


                            }
                            if (!thongTinChieuCuaMotRap.isEmpty())
                                results.put(cumRap, thongTinChieuCuaMotRap);

                        }
                        System.out.println(results);
                        Activity activity = (Activity) context;
                        // Lấy thông tin lịch chiếu và render lên
                        if (activity != null) {
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        ExpandableListView listRap = ((Activity) context).findViewById(R.id.listRapCoSuatChieu);
                                        TextView txtKhongCoPhim = ((Activity) context).findViewById(R.id.txtKhongCoPhim);
                                        ArrayList<String> availableCumRaps = new ArrayList<>();
                                        for (String result : results.keySet()) {
                                            availableCumRaps.add(result);
                                        }
                                        if (availableCumRaps.size() > 0) {
                                            txtKhongCoPhim.setText("");
                                        } else {
                                            if (txtKhongCoPhim != null) {
                                                txtKhongCoPhim.setText("Hiện không có lịch chiếu!");

                                            }

                                        }

                                        ExpandableListRapCoPhimAdapter expandableListRapCoPhimAdapter = new ExpandableListRapCoPhimAdapter(context, availableCumRaps, results, phim,
                                                listNgay.get(position).getNgay() + "/" + listNgay.get(position).getThang() + "/" + listNgay.get(position).getNam());

                                        listRap.setAdapter(expandableListRapCoPhimAdapter);
                                        progressBar.setVisibility(View.GONE);
                                    } catch (Exception e) {
                                        System.out.println("Error in line 154 - ListNgayCHieuAdapter");
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
