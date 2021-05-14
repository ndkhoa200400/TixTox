package com.example.tixtox.Model;

public class Membership {
    int cotMoc[] = {0, 20, 40, 70};
    String loaiThanhVien[] = {"Thành viên K","Thành viên Cu", "Thành viên Ag", "Thành viên Au"};
    int position = 0;
    static Membership instance;
    private Membership()
    {

    }
    public static Membership getInstace(int point) {
        if (instance == null) instance = new Membership();
        instance.capNhatDiem(point);
        return instance;
    }

    public String getLoaiThanhVien()
    {
        return loaiThanhVien[position];
    }

    public int getCotMocKeTiep()
    {
        if (position < cotMoc.length - 1)
            return cotMoc[position+1];
        return 0;
    }

    public void capNhatDiem(int point){
        if (position < cotMoc.length - 1)
            if (point >= cotMoc[position+1]) {
                if (position < cotMoc.length ) position ++;
            }

    }

    public  int getCotMocHienTai()
    {
        return cotMoc[position];
    }

    public int getViTriHienTai()
    {
        return position;
    }
}
