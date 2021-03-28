package com.example.tixtox.Model;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;

public class ModelRap {
    final String url = "https://movie0706.cybersoft.edu.vn/api/QuanLyRap/LayThongTinHeThongRap";
    private ArrayList result;

    public ModelRap(){}

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

    public ArrayList<Rap> getThongTinRap() throws IOException {
        ResponseBody responseBody = query(url);

        ArrayList<Rap> dsRap = null;
        if (responseBody != null) {
            Gson gson = new Gson();
            ArrayList data = gson.fromJson(responseBody.string(), ArrayList.class);
            dsRap = new ArrayList<>();

            for (Object i : data) {
                Rap r = new Rap();

                r.setMaHeThongRap((String) ((LinkedTreeMap) i).get("maHeThongRap").toString());
                r.setTenHeThongRap((String) ((LinkedTreeMap) i).get("tenHeThongRap").toString());
                r.setBiDanh((String) ((LinkedTreeMap) i).get("biDanh").toString());
                r.setLogo((String) ((LinkedTreeMap) i).get("logo").toString());

                dsRap.add(r);
            }
            responseBody.close();
        }

        return dsRap;
    }
}
