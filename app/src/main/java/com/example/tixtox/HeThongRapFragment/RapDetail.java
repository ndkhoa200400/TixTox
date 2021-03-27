package com.example.tixtox.HeThongRapFragment;

public class RapDetail {
    private int logo;
    private String tenRap;

    public RapDetail(int logo, String tenRap){
        this.logo = logo;
        this.tenRap = tenRap;
    }

    public int getLogo() {
        return logo;
    }

    public String getTenRap() {
        return tenRap;
    }

    public void setTenRap(String tenRap) {
        this.tenRap = tenRap;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }
}
