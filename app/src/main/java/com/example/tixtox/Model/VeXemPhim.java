package com.example.tixtox.Model;

import com.example.tixtox.Model.Rap.RapPhim;

import java.util.ArrayList;

public class VeXemPhim {
    String id;
    String Ghe;
    String GiaTien,ThoiGian;
    String phim;
    String rapphim;
    String PhongChieu;
    public VeXemPhim(){

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

    public VeXemPhim(String ghe, String giaTien, String thoiGian, String phim, String rapphim, String phongChieu) {
        Ghe = ghe;
        GiaTien = giaTien;
        ThoiGian = thoiGian;
        this.phim = phim;
        this.rapphim = rapphim;
        PhongChieu = phongChieu;
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

//    public Phim getPhim() {
//        return phim;
//   }
//
//    public void setPhim(Phim phim) {
//        this.phim = phim;
//    }
//
//    public RapPhim getRapphim() {
//        return rapphim;
//    }
//
//    public void setRapphim(RapPhim rapphim) {
//        this.rapphim = rapphim;
//    }

    public String getPhongChieu() {
        return PhongChieu;
    }

    public void setPhongChieu(String phongChieu) {
        PhongChieu = phongChieu;
    }


}

