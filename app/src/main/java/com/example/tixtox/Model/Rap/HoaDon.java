package com.example.tixtox.Model.Rap;

public class HoaDon {
    String idtk;
    String MaGD;
    int Soghedoi;
    int Soghedon;

    public HoaDon(String idtk, String maGD, int soghedoi, int soghedon, String thanhtien) {
        this.idtk = idtk;
        MaGD = maGD;
        Soghedoi = soghedoi;
        Soghedon = soghedon;
        Thanhtien = thanhtien;
    }

    String Thanhtien;


    public String getIdtk() {
        return idtk;
    }

    public void setIdtk(String idtk) {
        this.idtk = idtk;
    }

    public String getMaGD() {
        return MaGD;
    }

    public void setMaGD(String maGD) {
        MaGD = maGD;
    }

    public int getSoghedoi() {
        return Soghedoi;
    }

    public void setSoghedoi(int soghedoi) {
        Soghedoi = soghedoi;
    }

    public int getSoghedon() {
        return Soghedon;
    }

    public void setSoghedon(int soghedon) {
        Soghedon = soghedon;
    }

    public String getThanhtien() {
        return Thanhtien;
    }

    public void setThanhtien(String thanhtien) {
        Thanhtien = thanhtien;
    }





}
