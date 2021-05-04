package com.example.tixtox.Model;

import com.example.tixtox.Model.Rap.CumRap;
import com.example.tixtox.Model.Rap.RapDetail;
import com.example.tixtox.Model.Rap.RapPhim;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.io.IOException;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;

public class ModelRap {
    final String url = "https://movie0706.cybersoft.edu.vn/api/QuanLyRap";
    private static ModelRap modelRap;
    private ArrayList<CumRap> cumRaps;

    private HashMap<String, ArrayList<RapDetail>> heThongRaps;
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

    private Date parseGioChieu(String h) throws ParseException {
        // "2019-01-01T10:10:00" => 2019-01-01 và 10:10:10
        h = h.replace("T", " ");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return simpleDateFormat.parse(h);

    }

    public static ModelRap getInstance() throws IOException {
        if (modelRap == null) {
            modelRap = new ModelRap();
            modelRap.setCumRaps(modelRap.getThongTinCumRap());
            HashMap<String, ArrayList<RapDetail>> heThongRaps = new HashMap<>();
            // Lấy các chi nhánh của một cụm rạp ra
            for (CumRap cumRap : modelRap.getCumRaps()) {
                ArrayList<RapDetail> rapDetails = modelRap.getRapDetail(cumRap.getMaHeThongRap());
                heThongRaps.put(cumRap.getMaHeThongRap(), rapDetails);
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

                    p.setMaRap(maRap);
                    p.setTenRap(((LinkedTreeMap) rapPhim).get("tenRap").toString());

                    rapPhims.add(p);
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

    public void setHeThongRaps(HashMap<String, ArrayList<RapDetail>> heThongRaps) {
        this.heThongRaps = heThongRaps;
    }

    public HashMap<String, ArrayList<RapDetail>> getHeThongRaps() {
        return heThongRaps;
    }

    public ArrayList<RapDetail> getRapDetails(String CumRap) {
        return this.heThongRaps.get(CumRap);
    }

    public HashMap<String, ArrayList<Date>> getThongTinLichChieuHeThongRap(String maCumRap, String maRapDetail) throws IOException, ParseException {
        ResponseBody responseBody = query(url + "/LayThongTinLichChieuHeThongRap?maHeThongRap=" + maCumRap);
        if (responseBody != null) {
            Gson gson = new Gson();
            ArrayList data = gson.fromJson(responseBody.string(), ArrayList.class);
            ModelPhim modelPhim = ModelPhim.getInstance();

            // Lưu lại kết quả: {Tên Phim: Suất Chiếu}
            HashMap<String, ArrayList<Date>> lichChieuCuaRap = new HashMap<>();
            for (Object i : data) {
                LinkedTreeMap d = (LinkedTreeMap) i;
                ArrayList<LinkedTreeMap> listRaps = (ArrayList<LinkedTreeMap>) d.get("lstCumRap");

                for (LinkedTreeMap rap : listRaps) {
                    // Kiểm tra xem mã cụm rạp có phải rạp đang query không
                    if (rap.get("maCumRap").equals(maRapDetail)) {
                        System.out.println(rap.get("danhSachPhim"));
                        ArrayList<LinkedTreeMap> danhSachPhim = (ArrayList<LinkedTreeMap>) rap.get("danhSachPhim");

                        // thongTinMotPhim bao gồm mã phim, tên phim, hình ảnh và lstChieuTheoPhim
                        // lstChieuTheoPhim là một array chứa thông tin phim của một phim
                        for (LinkedTreeMap thongTinMotPhim : danhSachPhim) {
                            String maPhim = ((Double) thongTinMotPhim.get("maPhim")).toString();
                            maPhim = maPhim.substring(0, maPhim.indexOf("."));
                            // Kiểm tra xem mã phim có đang chiếu trên hệ thống hay không?
                            for (Phim p : modelPhim.getPhimDangChieu()) {

                                if (p.getMaPhim().equals(maPhim)) {
                                    // Lấy ra các suất chiếu
                                    ArrayList<LinkedTreeMap> lstLichChieuTheoPhim = (ArrayList<LinkedTreeMap>) thongTinMotPhim.get("lstLichChieuTheoPhim");
                                    ArrayList<Date> ngayChieuCuaMotPhim = new ArrayList<>();
                                    for (LinkedTreeMap lichChieuMotPhim : lstLichChieuTheoPhim) {
                                        Date ngay = parseGioChieu(lichChieuMotPhim.get("ngayChieuGioChieu").toString());
                                        ngayChieuCuaMotPhim.add(ngay);
                                    }

                                    lichChieuCuaRap.put(thongTinMotPhim.get("tenPhim").toString(), ngayChieuCuaMotPhim);

                                    break;
                                }
                            }

                        }

                    }

                }

            }
            return lichChieuCuaRap;
        }
        return null;
    }


    public RapDetail getMotRapDetail(String maCumRap, String maRapDetail) {
        ArrayList<RapDetail> rapDetails = this.heThongRaps.get(maCumRap);
        ;
        if (rapDetails != null) {
            for (RapDetail rap : rapDetails) {

                if (rap.getMaRap().equals(maRapDetail)) {
                    return rap;
                }
            }
        }
        return null;
    }

    public RapDetail getMotRapDetail(String maCumRap, String maRapDetail, String maRap) {
        ArrayList<RapDetail> rapDetails = this.heThongRaps.get(maCumRap);
        if (rapDetails != null) {
            for (RapDetail rap : rapDetails) {
                if (rap.getMaRap().equals(maRapDetail)) {
                    for (RapPhim rapPhim : rap.getListRapPhim()) {
                        if (rapPhim.getMaRap().equals(maRap))
                            return rap;
                    }
                }
            }
        }
        return null;
    }

    public RapDetail getMotRapDetailDuaTrenPhongRap(String maCumRap, String maRap) {
        ArrayList<RapDetail> rapDetails = this.heThongRaps.get(maCumRap);

        if (rapDetails != null) {
            for (RapDetail rap : rapDetails) {

                for (RapPhim rapPhim : rap.getListRapPhim()) {

                    if (rapPhim.getMaRap().equals(maRap))
                        return rap;
                }
            }
        }

        return null;
    }

    public CumRap timCumRapTheoMa(String maCumRap) {
        // Tìm thông tin cụm rạp dựa vào mã cụm
        for (CumRap cumRap : this.cumRaps) {
            if (cumRap.getMaHeThongRap().equals(maCumRap)) return cumRap;
        }
        return null;
    }
}
