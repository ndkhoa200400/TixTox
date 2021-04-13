package com.example.tixtox.Model.Rap;


// Các phòng có trong một rạp
// Rạp 1, Rạp 2 của BHD Bitexco
public class RapPhim {
    private String maRap, tenRap;

    public RapPhim() {
    }

    public String getMaRap() {
        return maRap;
    }

    public void setMaRap(String maRap)
    {
        if (maRap.indexOf(".")>0)
            maRap = maRap.substring(0,maRap.indexOf("."));
        this.maRap = maRap;
    }

    public String getTenRap() {
        return tenRap;
    }

    public void setTenRap(String tenRap) {
        this.tenRap = tenRap;
    }
}
