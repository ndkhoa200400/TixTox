package com.example.tixtox;

import android.os.Handler;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ModelPhim extends Observable {

    final String url = "https://movie0706.cybersoft.edu.vn/api/QuanLyPhim/";
    ArrayList result;

    Handler mainHandler;

    ModelPhim(Handler h) {
        this.mainHandler = h;
    }

    public ArrayList<Phim> getPhimTheoNgay(String dateFrom, String dateTo){



        ArrayList data =  query(url + "LayDanhSachPhimTheoNgay?maNhom=GP01&soTrang=1&soTrang=10&tuNgay=" + dateFrom + "&denNgay=" + dateTo);;
        if (data != null) {
            ArrayList<Phim> phims = new ArrayList<>();
            for (Object d : data) {
                Phim p = new Phim();
                p.setMaPhim((String) ((LinkedTreeMap) d).get("maPhim").toString());
                p.setBiDanh((String) ((LinkedTreeMap) d).get("biDanh"));
                p.setDanhGia((String) ((LinkedTreeMap) d).get("danhGia").toString());
                p.setHinhAnh((String) ((LinkedTreeMap) d).get("hinhAnh"));
                p.setMaNhom((String) ((LinkedTreeMap) d).get("maNhom"));
                p.setMoTa((String) ((LinkedTreeMap) d).get("moTa"));
                p.setNgayKhoiChieu((String) ((LinkedTreeMap) d).get("ngayKhoiChieu"));
                p.setTenPhim((String) ((LinkedTreeMap) d).get("tenPhim"));
                p.setTrailer((String) ((LinkedTreeMap) d).get("trailer"));

                phims.add(p);
            }


            return phims;
        }
        return null;

    }


    private ArrayList query(String passURL) {
        try {
            result = null;
            Gson gson = new Gson();
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(passURL).build();

            ResponseBody responseBody = client.newCall(request).execute().body();

            return gson.fromJson(responseBody.string(), ArrayList.class);

        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

}
