package com.example.tixtox.Model.Rap;

// Cụm rạp
// VD: CGV, BHD
public class CumRap {
    private String maHeThongRap, tenHeThongRap, biDanh, logo;

    public CumRap() {
    }

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
        this.logo = logo.replace("http", "https");
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
