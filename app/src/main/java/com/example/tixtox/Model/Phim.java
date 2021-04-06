package com.example.tixtox.Model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;


public class Phim implements Serializable {
    String maPhim, danhGia;
    String tenPhim, biDanh, trailer, hinhAnh, moTa, maNhom;
    String ngayKhoiChieu;
    Phim()
    {

    }

    public String getMaPhim() {
        return maPhim;
    }

    public void setMaPhim(String maPhim) {
        this.maPhim = maPhim;
    }

    public String getDanhGia() {
        return danhGia;
    }

    public void setDanhGia(String danhGia) {
        this.danhGia = danhGia;
    }

    public String getTenPhim() {
        return tenPhim;
    }

    public void setTenPhim(String tenPhim) {
        this.tenPhim = tenPhim;
    }

    public String getBiDanh() {
        return biDanh;
    }

    public void setBiDanh(String biDanh) {
        this.biDanh = biDanh;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer.replace("http", "https");
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh.replace("http", "https");
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getMaNhom() {
        return maNhom;
    }

    public void setMaNhom(String maNhom) {
        this.maNhom = maNhom;
    }

    public String getNgayKhoiChieu() {
        return ngayKhoiChieu;
    }

    public void setNgayKhoiChieu(String ngayKhoiChieu) throws ParseException {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-YYYY");
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        ngayKhoiChieu = ngayKhoiChieu.substring(0, ngayKhoiChieu.indexOf("T"));

        this.ngayKhoiChieu = formatter.format(new SimpleDateFormat("yyyy-MM-dd").parse(ngayKhoiChieu));
    }



}
