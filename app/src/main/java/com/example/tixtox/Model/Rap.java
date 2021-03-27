package com.example.tixtox.Model;

public class Rap {
     private String maHeThongRap, tenHeThongRap, biDanh, logo;
     public Rap(){}

    public String getMaHeThongRap() {
        return maHeThongRap;
    }

    public String getTenHeThongRap() {
        return tenHeThongRap;
    }

    public String getBiDanh() {
        return biDanh;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public void setBiDanh(String biDanh) {
        this.biDanh = biDanh;
    }

    public void setMaHeThongRap(String maHeThongRap) {
        this.maHeThongRap = maHeThongRap;
    }

    public void setTenHeThongRap(String tenHeThongRap) {
        this.tenHeThongRap = tenHeThongRap;
    }
}
