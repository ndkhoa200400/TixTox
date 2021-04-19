package com.example.tixtox.Model;

public class KhuyenMai {
    int type; // Loại khuyến mãi, 1 là giảm theo % - 2 là trừ thẳng tiền
    String name;
    double discount;

    public KhuyenMai(int type, String name, double discount) {
        this.type = type;
        this.name = name;
        this.discount = discount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getGiaTienSauKM(double giaTien)
    {
        if (type==1)
        {
            if (discount < 1 && discount > 0)
            {
                return giaTien * (1 - discount);
            }
            else{
                return  (Double)(giaTien * (100-discount))/100.0;
            }
        }else{
            if (giaTien < discount) return 0;
            else return giaTien - discount;
        }
    }
}
