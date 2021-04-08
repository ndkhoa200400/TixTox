package com.example.tixtox.Model;

import android.view.View;

import com.example.tixtox.Model.Phim;
import com.example.tixtox.Model.Rap.CumRap;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;

public class ModelPhim {

    final String url = "https://movie0706.cybersoft.edu.vn/api/QuanLyPhim/";
    ArrayList result;

    private static ModelPhim modelPhim;
    private ArrayList<Phim> phimDangChieu = null;
    private ArrayList<Phim> phimSapChieu = null;

    public ModelPhim() {

    }

    public static ModelPhim getInstance() throws IOException {
        if (modelPhim == null) {
            modelPhim = new ModelPhim();


        }

        return modelPhim;
    }

    private ArrayList<Phim> getPhimsDangChieu() throws IOException, ParseException {
        // Hàm dùng để gọi API để lấy các phim đang chiếu
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/YYYY");
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime before = now.minusDays(14);
        return getPhimTheoNgay(dtf.format(before), dtf.format(now));
    }

    private ArrayList<Phim> getPhimsSapChieu() throws IOException, ParseException {
        // Hàm dùng để gọi API để lấy các phim sắp chiếu
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/YYYY");
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime after = now.plusDays(28);
        return getPhimTheoNgay(dtf.format(now), dtf.format(after));
    }

    private Date parseGioChieu(String h) throws ParseException {
        // "2019-01-01T10:10:00" => 2019-01-01 và 10:10:10
        h = h.replace("T", " ");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return simpleDateFormat.parse(h);

    }
    public  HashMap<String, HashMap<String, ArrayList<Date>>> getThongTinPhim(String MaPhim) throws IOException, JSONException, ParseException {

        // Lấy thông tin chi tiết của một phim
        // Trả về rạp, ngày chiếu va thông tin phim chi tiết
        String link = url+"/LayThongTinPhim?MaPhim=" + MaPhim;

        ResponseBody responseBody = query(link);


        if (responseBody != null) {
            Gson gson = new Gson();
            String str = responseBody.string();
            LinkedTreeMap data = gson.fromJson(str, LinkedTreeMap.class);
            responseBody.close();

//            Phim p = new Phim();
//            p.setTenPhim((String) data.get("tenPhim").toString());
//            p.setTrailer((String) data.get("trailer").toString());
//            p.setMoTa((String) data.get("moTa").toString());
//            p.setMaNhom((String) data.get("maNhom").toString());
//            p.setNgayKhoiChieu((String) data.get("ngayKhoiChieu").toString());
//            p.setHinhAnh((String) data.get("hinhAnh").toString());
//            p.setBiDanh((String) data.get("biDanh").toString());
//            p.setMaPhim((String) data.get("maPhim").toString());
//            p.setDanhGia((String) data.get("danhGia").toString());

            ArrayList<LinkedTreeMap> d = (ArrayList<LinkedTreeMap>) data.get("lichChieu");

            // Lưu thông tin lịch chiếu của từng cụm rạp.
            // Value: cụm rạp - Key: {rạp details : ngày giờ chiếu}
            HashMap<String, HashMap<String, ArrayList<Date>>> thongTinLichChieu = new HashMap<>();

            // Phần tử trong lịch chiếu gồm có thông tin rạp, mã rạp, mã phim, ngày giờ chiếu, thời lượng.
            for (LinkedTreeMap lichChieu: d)
            {
                // Thông tin rạp có mã rạp thuộc hệ thống nào
                LinkedTreeMap thongtinrap = (LinkedTreeMap) lichChieu.get("thongTinRap");
                String maCumRap = (String) thongtinrap.get("maHeThongRap");
                maCumRap = (String) maCumRap.substring(0, maCumRap.indexOf("."));

                String maRapDetail = (String) thongtinrap.get("maRap").toString();
                maRapDetail = maRapDetail.substring(0, maRapDetail.indexOf("."));

                Date gioChieu = parseGioChieu((String) lichChieu.get("ngayChieuGioChieu"));
                // Nếu thông tin lịch chiếu đã tồn tại mã cụm rạp
                if (thongTinLichChieu.containsKey(maCumRap))
                {
                    // Thêm thông tin rạp detail vào mã cụm rạp
                    HashMap<String, ArrayList<Date>> temp = thongTinLichChieu.get(maCumRap);
                    if (temp.containsKey(maRapDetail))
                    {
                       temp.get(maRapDetail).add(gioChieu);
                    }
                    // Nếu chưa tồn tại rạp details trong một mã cụm rạp thì tạo mới trong thông tin chi tiết
                    else
                    {
                        ArrayList<Date> dates = new ArrayList<>();
                        dates.add(gioChieu);
                        temp.put(maRapDetail, dates);
                    }
                }
                // Nếu thông tin lịch chiếu chưa có mã cụm rạp
                else{
                    HashMap<String, ArrayList<Date>> temp = new HashMap<>();
                    ArrayList<Date> dates = new ArrayList<>();
                    dates.add(gioChieu);
                    temp.put(maRapDetail, dates);
                    thongTinLichChieu.put(maCumRap, temp);


                }

            }
            return thongTinLichChieu;
        }
        return null;
    }

    public ArrayList<Phim> getPhimTheoNgay(String dateFrom, String dateTo) throws IOException, ParseException {


        ResponseBody responseBody = query(url + "LayDanhSachPhimTheoNgay?maNhom=GP01&soTrang=1&soTrang=10&tuNgay=" + dateFrom + "&denNgay=" + dateTo);

        if (responseBody != null) {
            Gson gson = new Gson();

            ArrayList data = gson.fromJson(responseBody.string(), ArrayList.class);
            ;
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
        return new ArrayList<>();

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




    public static void setModelPhim(ModelPhim modelPhim) {
        ModelPhim.modelPhim = modelPhim;
    }

    public void setPhimDangChieu(ArrayList<Phim> phimDangChieu) {
        this.phimDangChieu = phimDangChieu;
    }

    public ArrayList<Phim> getPhimSapChieu() throws IOException, ParseException {
        if (phimSapChieu == null)   modelPhim.setPhimSapChieu(modelPhim.getPhimsSapChieu());
        return phimSapChieu;
    }

    public ArrayList<Phim> getPhimDangChieu() throws IOException, ParseException {
        if (phimDangChieu == null)  modelPhim.setPhimDangChieu(modelPhim.getPhimsDangChieu());
        return phimDangChieu;
    }
    public void setPhimSapChieu(ArrayList<Phim> phimSapChieu) {
        this.phimSapChieu = phimSapChieu;
    }
}
