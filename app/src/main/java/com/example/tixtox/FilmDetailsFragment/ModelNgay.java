package com.example.tixtox.FilmDetailsFragment;

public class ModelNgay {
    private String thu;
    private String ngay;
    private String thang;
    private String nam;

    public ModelNgay(String thu, String ngay, String thang, String nam){
        this.thu = thu;
        this.ngay = ngay;
        this.thang = thang;
        this.nam = nam;
    }

    public ModelNgay() {

    }

    public String getNam() {
        return nam;
    }

    public String getNgay() {
        return ngay;
    }

    public String getThang() {
        return thang;
    }

    public String getThu() {
        return thu;
    }

    public void setNam(String nam) {
        this.nam = nam;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public void setThang(String thang) {
        this.thang = thang;
    }

    public void setThu(String thu) {
        this.thu = thu;
    }
}
