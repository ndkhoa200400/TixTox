package com.example.tixtox;

import android.os.Handler;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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



    ModelPhim() {

    }
    public void getThongTinPhim(String MaPhim) throws IOException, JSONException {

        // Lay thong tin cua mot phim
        // Tra ve rap, ngay chieu va thong tin phim chi tiet
        String link = "LayThongTinPhim?MaPhim=" +MaPhim;

        ResponseBody  responseBody= query("https://movie0706.cybersoft.edu.vn/api/QuanLyPhim/LayThongTinPhim?MaPhim=5126");



        if (responseBody != null) {
            Gson gson = new Gson();
            String str = responseBody.string();
            JSONObject data = new JSONObject(str);
            responseBody.close();

            Phim p = new Phim();
            p.setTenPhim((String)data.get("tenPhim").toString());
            p.setTrailer((String)data.get("trailer").toString());
            p.setMoTa((String)data.get("moTa").toString());
            p.setMaNhom((String)data.get("maNhom").toString());
            p.setNgayKhoiChieu((String)data.get("ngayKhoiChieu").toString());
            p.setHinhAnh((String)data.get("hinhAnh").toString());
            p.setBiDanh((String)data.get("biDanh").toString());
            p.setMaPhim((String)data.get("maPhim").toString());
            p.setDanhGia((String)data.get("danhGia").toString());
            System.out.println(p.getTenPhim());
        }
    }
    public ArrayList<Phim> getPhimTheoNgay(String dateFrom, String dateTo) throws IOException {


        ResponseBody  responseBody= query(url + "LayDanhSachPhimTheoNgay?maNhom=GP01&soTrang=1&soTrang=10&tuNgay=" + dateFrom + "&denNgay=" + dateTo);

        if (responseBody != null) {
            Gson gson = new Gson();

            ArrayList data =  gson.fromJson(responseBody.string(), ArrayList.class);;
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

            responseBody.close();
            return phims;
        }
        return null;

    }


    private ResponseBody query(String passURL) {
        try {
            result = null;

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(passURL).build();

            ResponseBody responseBody = client.newCall(request).execute().body();

            return responseBody;

        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

}
