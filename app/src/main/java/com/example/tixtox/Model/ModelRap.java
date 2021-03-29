package com.example.tixtox.Model;

import com.example.tixtox.Model.Rap.CumRap;
import com.example.tixtox.Model.Rap.RapDetail;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;

public class ModelRap {
    final String url = "https://movie0706.cybersoft.edu.vn/api/QuanLyRap";
    private ArrayList result;

    public ModelRap() {
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

    public ArrayList<CumRap> getThongTinCumRap() throws IOException {
        ResponseBody responseBody = query(url + "/LayThongTinHeThongRap");

        ArrayList<CumRap> dsCumRap = null;
        if (responseBody != null) {
            Gson gson = new Gson();
            ArrayList data = gson.fromJson(responseBody.string(), ArrayList.class);
            dsCumRap = new ArrayList<>();

            for (Object i : data) {
                CumRap r = new CumRap();

                r.setMaHeThongRap((String) ((LinkedTreeMap) i).get("maHeThongRap").toString());
                r.setTenHeThongRap((String) ((LinkedTreeMap) i).get("tenHeThongRap").toString());
                r.setBiDanh((String) ((LinkedTreeMap) i).get("biDanh").toString());
                r.setLogo((String) ((LinkedTreeMap) i).get("logo").toString());

                dsCumRap.add(r);
            }
            responseBody.close();
        }

        return dsCumRap;
    }


    public ArrayList<RapDetail> getRapDetail(String cumRap) throws IOException {
        ResponseBody responseBody = query(url + "/LayThongTinCumRapTheoHeThong?maHeThongRap=" + cumRap);

        ArrayList<RapDetail> dsRapDetail = null;
        if (responseBody != null) {
            Gson gson = new Gson();
            ArrayList data = gson.fromJson(responseBody.string(), ArrayList.class);
            dsRapDetail = new ArrayList<>();
            for (Object i : data) {
                RapDetail rapDetail = new RapDetail();

                rapDetail.setMaRap((String) ((LinkedTreeMap) i).get("maCumRap").toString());
                rapDetail.setTenRap((String) ((LinkedTreeMap) i).get("tenCumRap").toString());
                rapDetail.setDiaChi((String) ((LinkedTreeMap) i).get("diaChi").toString());

                dsRapDetail.add(rapDetail);
            }
        }

        return dsRapDetail;
    }
}
