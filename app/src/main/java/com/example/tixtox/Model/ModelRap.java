package com.example.tixtox.Model;

import com.example.tixtox.Model.Rap.CumRap;
import com.example.tixtox.Model.Rap.RapDetail;
import com.example.tixtox.Model.Rap.RapPhim;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;

public class ModelRap {
    final String url = "https://movie0706.cybersoft.edu.vn/api/QuanLyRap";
    private static ModelRap modelRap;
    private ArrayList<CumRap> cumRaps;

    private HashMap<CumRap, ArrayList<RapDetail>> heThongRaps;
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

    public static ModelRap getInstance() throws IOException {
        if (modelRap == null) {
            modelRap = new ModelRap();
            modelRap.setCumRaps(modelRap.getThongTinCumRap());
            HashMap<CumRap, ArrayList<RapDetail>> heThongRaps = new HashMap<>();
            // Lấy các chi nhánh của một cụm rạp ra
            for (CumRap cumRap : modelRap.getCumRaps()) {
                ArrayList<RapDetail> rapDetails = modelRap.getRapDetail(cumRap.getMaHeThongRap());
                heThongRaps.put(cumRap, rapDetails);
            }

            modelRap.setHeThongRaps(heThongRaps);

        }

        return modelRap;

    }

    private ArrayList<CumRap> getThongTinCumRap() throws IOException {
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


    private ArrayList<RapDetail> getRapDetail(String cumRap) throws IOException {
        ResponseBody responseBody = query(url + "/LayThongTinCumRapTheoHeThong?maHeThongRap=" + cumRap);

        ArrayList<RapDetail> dsRapDetail = null;
        if (responseBody != null) {
            Gson gson = new Gson();
            ArrayList data = gson.fromJson(responseBody.string(), ArrayList.class);
            dsRapDetail = new ArrayList<>();
            for (Object i : data) {

                RapDetail rapDetail = new RapDetail();

                rapDetail.setMaRap(((LinkedTreeMap) i).get("maCumRap").toString());
                rapDetail.setTenRap(((LinkedTreeMap) i).get("tenCumRap").toString());
                rapDetail.setDiaChi(((LinkedTreeMap) i).get("diaChi").toString());

                // Lấy các rạp (phòng) có bên trong một cinema
                ArrayList<RapPhim> rapPhims = new ArrayList<>();
                for (Object rapPhim : (ArrayList) ((LinkedTreeMap) i).get("danhSachRap")) {
                    RapPhim p = new RapPhim();
                    // Loại bỏ .0 sau mã rạp
                    String maRap = ((LinkedTreeMap) rapPhim).get("maRap").toString();
                    maRap = maRap.substring(maRap.indexOf("."));
                    p.setMaRap(maRap);
                    p.setTenRap(((LinkedTreeMap) rapPhim).get("tenRap").toString());
                }
                rapDetail.setListRapPhim(rapPhims);


                dsRapDetail.add(rapDetail);
            }
        }

        return dsRapDetail;
    }

    public void setCumRaps(ArrayList<CumRap> cumRaps) {
        this.cumRaps = cumRaps;
    }

    public ArrayList<CumRap> getCumRaps() {
        return cumRaps;
    }

    public void setHeThongRaps(HashMap<CumRap, ArrayList<RapDetail>> heThongRaps) {
        this.heThongRaps = heThongRaps;
    }

    public HashMap<CumRap, ArrayList<RapDetail>> getHeThongRaps() {
        return heThongRaps;
    }

    public ArrayList<RapDetail> getRapDetails(String CumRap) {
        return this.heThongRaps.get(CumRap);
    }

    public void getThongTinLichChieuHeThongRap(String maCumRap, String maRapDetail) throws IOException {
        System.out.println(maCumRap);
        ResponseBody responseBody = query(url + "/LayThongTinLichChieuHeThongRap?maHeThongRap="+maCumRap);
       if (responseBody != null)
       {
           Gson gson = new Gson();
           ArrayList data = gson.fromJson(responseBody.string(), ArrayList.class);
           for(Object i: data)
           {
               LinkedTreeMap d = (LinkedTreeMap) i;
               ArrayList listRaps = (ArrayList) d.get("lstCumRap");

               for (Object r: listRaps)
               {
                   LinkedTreeMap rap = (LinkedTreeMap) r;
                   if (rap.get("maCumRap").equals(maRapDetail))
                   {
                       System.out.println(rap.get("danhSachPhim"));
                       ArrayList danhSachPhim = (ArrayList) rap.get("danhSachPhim");


                       ModelPhim modelPhim = ModelPhim.getInstance();
                       // Kiểm tra các phim nào đang chiếu.
                       System.out.println("Phim đang chiếu nek");
                       for(Object p: danhSachPhim)
                       {
                           LinkedTreeMap phim = (LinkedTreeMap) p;
                           for (Phim phimDangChieu: modelPhim.getPhimDangChieu()){
                                if (phim.get("tenPhim").equals(phimDangChieu.getTenPhim()))
                                {
                                    System.out.println(phim.get("tenPhim"));
                                }
                           }

                       }
                       return;
                   }

               }
           }
       }
    }

    public void getThongTinLichChieuPhim(String maPhim, String ngayXem) throws IOException {
        ResponseBody responseBody = query(url + "/LayThongTinLichChieuPhim?MaPhim="+maPhim);
        if(responseBody != null){
            Gson gson = new Gson();
            ArrayList data = gson.fromJson(responseBody.string(), ArrayList.class);
            for(Object obj1: data){
                LinkedTreeMap linkedTreeMap1 = (LinkedTreeMap) obj1;
                ArrayList heThongRapChieu = (ArrayList) linkedTreeMap1.get("heThongRapChieu");
                for(Object obj2: heThongRapChieu){
                    LinkedTreeMap linkedTreeMap2 = (LinkedTreeMap) obj2;
                    ArrayList cumRapChieu = (ArrayList) linkedTreeMap2.get("cumRapChieu");
                }
            }
        }
    }
}
