package com.example.tixtox.DatVe;

import java.util.Date;

public class HoaDon {

    int SoGheDoi;
    int SoGheDon;
    String ThanhTien;
    String Gia;


    Long Time;
    String ID_Account;
    public HoaDon(){
        super();
    }
    public HoaDon(int soGheDoi, int soGheDon, String thanhTien, String ID_Account,String Gia) {
        SoGheDoi = soGheDoi;
        SoGheDon = soGheDon;
        ThanhTien = thanhTien;
        Time = new Date().getTime();
        this.ID_Account = ID_Account;
        this.Gia = Gia;
    }


    public Long getTime() {
        return Time;
    }

    public int getSoGheDoi() {
        return SoGheDoi;
    }

    public void setSoGheDoi(int soGheDoi) {
        SoGheDoi = soGheDoi;
    }

    public int getSoGheDon() {
        return SoGheDon;
    }

    public void setSoGheDon(int soGheDon) {
        SoGheDon = soGheDon;
    }

    public String getThanhTien() {
        return ThanhTien;
    }

    public void setThanhTien(String thanhTien) {
        ThanhTien = thanhTien;
    }


    public String getGia() {
        return Gia;
    }

    public String getID_Account() {
        return ID_Account;
    }

    public void setID_Account(String ID_Account) {
        this.ID_Account = ID_Account;
    }


}
