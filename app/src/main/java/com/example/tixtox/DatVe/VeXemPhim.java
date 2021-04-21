package com.example.tixtox.DatVe;

import com.example.tixtox.Model.Rap.RapPhim;

import java.util.ArrayList;

public class VeXemPhim {

    String id;



    String Ghe;
    String GiaTien,ThoiGian;
    String SuatChieu;
    String phim;
    String rapphim;
    String PhongChieu;
    String hoaDon;
    String HinhAnh;


    String MaVe;
    String TrangThai;
    public String getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        HinhAnh = hinhAnh;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(String trangThai) {
        TrangThai = trangThai;
    }
    public VeXemPhim(String ghe, String giaTien, String thoiGian, String suatChieu, String phim, String rapphim,
                     String phongChieu, String hoaDon,String mId, String mTrangThai,String mMave,String hinhAnh) {
        Ghe = ghe;
        GiaTien = giaTien;
        ThoiGian = thoiGian;
        SuatChieu = suatChieu;
        this.phim = phim;
        this.rapphim = rapphim;
        PhongChieu = phongChieu;
        this.hoaDon = hoaDon;
        id = mId;
        TrangThai = mTrangThai;
        MaVe = mMave;
        this.HinhAnh = hinhAnh;
    }

    public String getMaVe() {
        return MaVe;
    }

    public void setMaVe(String maVe) {
        MaVe = maVe;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public VeXemPhim(){
        super();
    }

    public String getSuatChieu() {
        return SuatChieu;
    }

    public String getPhim() {
        return phim;
    }

    public void setPhim(String phim) {
        this.phim = phim;
    }

    public String getRapphim() {
        return rapphim;
    }

    public void setRapphim(String rapphim) {
        this.rapphim = rapphim;
    }



    public String getGhe() {
        return Ghe;
    }

    public void setGhe(String ghe) {
        Ghe = ghe;
    }

    public String getGiaTien() {
        return GiaTien;
    }

    public void setGiaTien(String giaTien) {
        GiaTien = giaTien;
    }

    public String getThoiGian() {
        return ThoiGian;
    }

    public void setThoiGian(String thoiGian) {
        ThoiGian = thoiGian;
    }


    public String getPhongChieu() {
        return PhongChieu;
    }

    public void setPhongChieu(String phongChieu) {
        PhongChieu = phongChieu;
    }


}

