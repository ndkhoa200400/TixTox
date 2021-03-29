package com.example.tixtox.Model.Rap;

import android.util.Patterns;

import java.util.ArrayList;

public class RapDetail {
    private String maRap;
    private String tenRap;
    private String diaChi;
    private ArrayList<RapPhim> listRapPhim;

    public RapDetail() {

    }

    public RapDetail(String tenRap, String diaChi, ArrayList<RapPhim> listRapPhim) {
        this.tenRap = tenRap;
        this.diaChi = diaChi;
        this.listRapPhim = listRapPhim;
    }

    public String getTenRap() {
        return tenRap;
    }

    public void setTenRap(String tenRap) {
        this.tenRap = tenRap;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public ArrayList<RapPhim> getListRapPhim() {
        return listRapPhim;
    }

    public void setListRapPhim(ArrayList<RapPhim> listRapPhim) {
        this.listRapPhim = listRapPhim;
    }

    public String getMaRap(){
        return this.maRap;
    }
    public void setMaRap(String maRap)
    {
        this.maRap = maRap;
    }
}