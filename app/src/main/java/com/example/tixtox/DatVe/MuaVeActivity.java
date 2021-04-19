package com.example.tixtox.DatVe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tixtox.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


import java.util.List;

public class  MuaVeActivity extends AppCompatActivity {
    GridView gridview, gridviewGheDoi;
    ArrayList<VeXemPhim> vexm = new ArrayList<>();
    ArrayList<String> vitri = new ArrayList<>();
    TextView txtSoGhe, txtTongTien, txtPhong, txtRap, txtSuatChieu, txtNgayChieu, txtTenPhim;
    Button btnNext;
    Dialog dialog;
    DatabaseReference dtb;
    String V = "";
    int SoGheDon =0;
    int SoGheDoi =0;

    ArrayList<Integer> ChangeGhe = new ArrayList<>();
    Integer[] image={R.drawable.icon_ghe, R.drawable.icon_ghe, R.drawable.icon_ghe,
            R.drawable.icon_ghe, R.drawable.icon_ghe,
            R.drawable.icon_ghe, R.drawable.icon_ghe,
            R.drawable.icon_ghe, R.drawable.icon_ghe,
            R.drawable.icon_ghe, R.drawable.icon_ghe,
            R.drawable.icon_ghe, R.drawable.icon_ghe,
            R.drawable.icon_ghe, R.drawable.icon_ghe,
            R.drawable.icon_ghe, R.drawable.icon_ghe,
            R.drawable.icon_ghe, R.drawable.icon_ghe,
            R.drawable.icon_ghe, R.drawable.icon_ghe,
            R.drawable.icon_ghe, R.drawable.icon_ghe,
            R.drawable.icon_ghe, R.drawable.icon_ghe,
            R.drawable.icon_ghe, R.drawable.icon_ghe,
            R.drawable.icon_ghe, R.drawable.icon_ghe,
            R.drawable.icon_ghe, R.drawable.icon_ghe,
            R.drawable.icon_ghe, R.drawable.icon_ghe,
            R.drawable.icon_ghe, R.drawable.icon_ghe,
            R.drawable.icon_ghe, R.drawable.icon_ghe,
            R.drawable.icon_ghe, R.drawable.icon_ghe,
            R.drawable.icon_ghe, R.drawable.icon_ghe,
            R.drawable.icon_ghe, R.drawable.icon_ghe,
            R.drawable.icon_ghe, R.drawable.icon_ghe,
            R.drawable.icon_ghe, R.drawable.icon_ghe,
            R.drawable.icon_ghe, R.drawable.icon_ghe,
            R.drawable.icon_ghe, R.drawable.icon_ghe,
            R.drawable.icon_ghe, R.drawable.icon_ghe,
            R.drawable.icon_ghe, R.drawable.icon_ghe,
            R.drawable.icon_ghe, R.drawable.icon_ghe,
            R.drawable.icon_ghe, R.drawable.icon_ghe,
            R.drawable.icon_ghe, R.drawable.icon_ghe,
            R.drawable.icon_ghe, R.drawable.icon_ghe,
            R.drawable.icon_ghe, R.drawable.icon_ghe,
            R.drawable.icon_ghe, R.drawable.icon_ghe,
            R.drawable.icon_ghe, R.drawable.icon_ghe,
            R.drawable.icon_ghe, R.drawable.icon_ghe,
            R.drawable.icon_ghe, R.drawable.icon_ghe,
            R.drawable.icon_ghe, R.drawable.icon_ghe,
            R.drawable.icon_ghe, R.drawable.icon_ghe,
            R.drawable.icon_ghe, R.drawable.icon_ghe,
            R.drawable.icon_ghe, R.drawable.icon_ghe,
            R.drawable.icon_ghe, R.drawable.icon_ghe,
            R.drawable.icon_ghe, R.drawable.icon_ghe,
            R.drawable.icon_ghe, R.drawable.icon_ghe,
            R.drawable.icon_ghe
    };

    Integer[] imageDoi = {R.drawable.icon_ghedoi, R.drawable.icon_ghedoi,
            R.drawable.icon_ghedoi, R.drawable.icon_ghedoi,
            R.drawable.icon_ghedoi};
    int gia = 70000;
    int Cost = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muave);
        dialog = new Dialog(MuaVeActivity.this);
        gridviewGheDoi = (GridView) findViewById(R.id.gridviewGheDoi);
        gridview = (GridView) findViewById(R.id.gridviewGhe);
        txtSoGhe = (TextView) findViewById(R.id.txtSoGhe);
        txtTongTien = (TextView) findViewById(R.id.txtTongTien);
        btnNext = (Button) findViewById(R.id.btnNext);
        dtb = FirebaseDatabase.getInstance().getReference();
        txtNgayChieu = (TextView) findViewById(R.id.txtNgayChieu);
        txtPhong = (TextView) findViewById(R.id.txtRap);
        txtSuatChieu = (TextView) findViewById(R.id.txtSuatChieu);
        txtRap = (TextView) findViewById(R.id.txtTenRap);
        txtTenPhim = (TextView) findViewById(R.id.txtTenPhim);
        if (V.isEmpty()) btnNext.setEnabled(false);
        Intent intent = getIntent();
        txtTenPhim.setText(intent.getStringExtra("Phim_Ten"));
        txtNgayChieu.setText(intent.getStringExtra("Phim_NgayChieu"));
        txtRap.setText(intent.getStringExtra("Phim_Rap"));
        txtSuatChieu.setText(intent.getStringExtra("Phim_ThoiGian"));
        SuatChieu suatchieu = new SuatChieu(intent.getStringExtra("Phim_ID")
                ,intent.getStringExtra("Phim_NgayChieu")
                ,intent.getStringExtra("Phim_ThoiGian"));

//        Toast.makeText(getApplicationContext(), suatchieu.getGhe(), Toast.LENGTH_SHORT).show();


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FirebaseAuth.getInstance()
                        .getCurrentUser() == null)
                    Toast.makeText(getApplicationContext(), "ChuaDangNhap", Toast.LENGTH_SHORT).show();
                else {
                    dialog.setContentView(R.layout.custom_dialog);
                    dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.background_dialog));
                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    dialog.setCancelable(false);
                    Button oke = dialog.findViewById(R.id.btn_Xacnhan_dialog);
                    Button tuchoi = dialog.findViewById(R.id.btn_Huy_dialog);


                    oke.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(MuaVeActivity.this, activity_bill.class);
                            String MaHoaDon = dtb.child("HoaDon").push().getKey();
                            String Key = dtb.child("VeXemPhim").push().getKey();
                            HoaDon bill = new HoaDon(SoGheDoi, SoGheDon, txtTongTien.getText().toString(), "123");
                            VeXemPhim vexem = new VeXemPhim(V, txtTongTien.getText().toString(),
                                    txtNgayChieu.getText().toString(), txtSuatChieu.getText().toString(),
                                    txtTenPhim.getText().toString(),
                                    txtRap.getText().toString(), txtPhong.getText().toString(), MaHoaDon,FirebaseAuth.getInstance()
                                    .getCurrentUser().getUid(),"Đã thanh toán",Key);

                            dtb.child("VeXemPhim").child(Key).setValue(vexem);

                            suatchieu.setGhe(V);
                            dtb.child("SuatChieu").child(suatchieu.getId()).setValue(suatchieu);
                            dtb.child("VeXemPhim").child(Key).child("hoaDon").setValue(MaHoaDon);
                            dtb.child("VeXemPhim").child(Key).child("id").setValue(vexem.getId());
                            dtb.child("VeXemPhim").child(Key).child("TrangThai").setValue(vexem.getTrangThai());
                            dtb.child("VeXemPhim").child(Key).child("MaVe").setValue(vexem.getMaVe());
                            dtb.child("HoaDon").child(MaHoaDon).setValue(bill);
                            dtb.child("HoaDon").child(MaHoaDon).child("Time").setValue(bill.getTime());
                            Toast.makeText(getApplicationContext(), vexem.hoaDon, Toast.LENGTH_SHORT).show();
                            intent.putExtra("Key", Key);
                            intent.putExtra("MaHoaDon", MaHoaDon);
                            startActivity(intent);
                            dialog.dismiss();

                        }
                    });
                    tuchoi.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            }
        });


        gridview = (GridView) findViewById(R.id.gridviewGhe);


        gridview.setAdapter(new ImageAdapterGridView(this, image, 100, 80));
        gridviewGheDoi.setAdapter(new ImageAdapterGridView(this, imageDoi, 100, 200));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ImageView imageView = (ImageView) view;
                if (image[position] == R.drawable.icon_ghe) {
                    imageView.setImageResource(R.drawable.icon_ghe_green);
                    image[position] = R.drawable.icon_ghe_green;
                    String a = ConvertPostoSe(position);
                    vitri.add(a);
                    SoGheDon++;
                    V = "";
                    for (String s : vitri) {
                        V += s + " ";
                    }
                    txtSoGhe.setText(V);
                    if (V.isEmpty()) btnNext.setEnabled(false);
                    else btnNext.setEnabled(true);
                    Toast.makeText(getApplicationContext(), V, Toast.LENGTH_SHORT).show();
                    txtTongTien.setText(Integer.toString(Cost = Cost + gia));
                } else {
                    imageView.setImageResource(R.drawable.icon_ghe);
                    image[position] = R.drawable.icon_ghe;
                    String a = ConvertPostoSe(position);
                    vitri.remove(a);
                    SoGheDon--;
                    V = "";
                    for (String s : vitri) {
                        V += s + " ";
                    }
                    Toast.makeText(getApplicationContext(), V, Toast.LENGTH_SHORT).show();
                    txtSoGhe.setText(V);
                    if (V.isEmpty()) btnNext.setEnabled(false);
                    else btnNext.setEnabled(true);
                    txtTongTien.setText(Integer.toString(Cost = Cost - gia));

                }

            }
        });
        gridviewGheDoi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ImageView imageView = (ImageView) view;
                if (imageDoi[position] == R.drawable.icon_ghedoi) {
                    imageView.setImageResource(R.drawable.icon_ghedoi_green);
                    imageDoi[position] = R.drawable.icon_ghedoi_green;
                    String a = ConVertGheDoi(position);
                    vitri.add(a);
                    SoGheDoi++;
                    V = "";
                    for (String s : vitri) {
                        V += s + " ";
                    }
                    txtSoGhe.setText(V);
                    if (V.isEmpty()) btnNext.setEnabled(false);
                    else btnNext.setEnabled(true);
                    Toast.makeText(getApplicationContext(), V, Toast.LENGTH_SHORT).show();
                    txtTongTien.setText(Integer.toString(Cost = Cost + gia * 2));
                } else {
                    imageView.setImageResource(R.drawable.icon_ghedoi);
                    imageDoi[position] = R.drawable.icon_ghedoi;
                    String a = ConVertGheDoi(position);
                    vitri.remove(a);
                    SoGheDoi--;
                    V = "";
                    for (String s : vitri) {
                        V += s + " ";
                    }
                    if (V.isEmpty()) btnNext.setEnabled(false);
                    else btnNext.setEnabled(true);
                    Toast.makeText(getApplicationContext(), V, Toast.LENGTH_SHORT).show();
                    txtSoGhe.setText(V);
                    txtTongTien.setText(Integer.toString(Cost = Cost - gia * 2));

                }

            }
        });

        for(int i=0;i<vexm.size();i++) {
            Toast.makeText(getApplicationContext(), "afeqwqew" + vexm.get(i).getGhe(), Toast.LENGTH_SHORT).show();
            ChangeGhe = ConverStringtoGhe(vexm.get(i).getGhe());
            for (int j = 0; j < ChangeGhe.size(); j++)
                setImageToX(ChangeGhe.get(j));
        }
        Toast.makeText(getApplicationContext(), "afeqwqew" , Toast.LENGTH_SHORT).show();

    }
    public void setImage(Integer[] t)
    {
        image = t;
    }
    public void setImageToX(int pos) {
        this.image[pos] = R.drawable.ghe_x;
    }

    private class ImageAdapterGridView extends BaseAdapter {
        private Context mcontext;
        Integer[] mimage;
        int h;
        int w;
        View v;

        public ImageAdapterGridView(Context context, Integer[] image, int hi, int wi) {
            mcontext = context;
            mimage = image;
            h = hi;
            w = wi;
        }

        @Override
        public int getCount() {
            return image.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                imageView = new ImageView(mcontext);
                imageView.setLayoutParams(new GridView.LayoutParams(w, h));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(16, 10, 16, 10);

            } else {
                imageView = (ImageView) convertView;
            }
            imageView.setImageResource(mimage[position]);

            return imageView;

        }


    }

    public String ConvertPostoSe(int pos) {
        pos = pos + 1;
        int num;
        num = pos / 11;
        int div;
        div = pos % 11;
        String b = "";
        if (num == 0)
            b = "A" + Integer.toString(div);
        if (num == 1)
            b = "B" + Integer.toString(div);
        if (num == 2)
            b = "C" + Integer.toString(div);
        if (num == 3)
            b = "D" + Integer.toString(div);
        if (num == 4)
            b = "E" + Integer.toString(div);
        if (num == 5)
            b = "F" + Integer.toString(div);
        if (num == 6)
            b = "G" + Integer.toString(div);
        if (num == 7)
            b = "H" + Integer.toString(div);
        return b;
    }

    public String ConVertGheDoi(int pos) {
        pos++;
        if (pos == 1) return "GD1";
        if (pos == 2) return "GD2";
        if (pos == 3) return "GD3";
        if (pos == 4) return "GD4";
        else return "GD5";
    }

    private void Load() {


      /*  dtb.child("VeXemPhim").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                VeXemPhim temp = snapshot.getValue(VeXemPhim.class);
                vexm.add(temp);
                Toast.makeText(getApplicationContext(), temp.getGhe(), Toast.LENGTH_SHORT).show();
                ChangeGhe = ConverStringtoGhe(temp.getGhe());
                for(int j =0;j<ChangeGhe.size();j++){
                    setImageToX(ChangeGhe.get(j));

                }


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/

        dtb.child("VeXemPhim").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> keys = new ArrayList<>();
                vexm.clear();
                for(DataSnapshot keyNode: snapshot.getChildren())
                {
                    keys.add(keyNode.getKey());
                    VeXemPhim xml = keyNode.getValue(VeXemPhim.class);
                    vexm.add(xml);
                    Toast.makeText(getApplicationContext(),"af" +  xml.getGhe(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        for(int i=0;i<vexm.size();i++) {
            Toast.makeText(getApplicationContext(),"af" +  vexm.get(i).getGhe(), Toast.LENGTH_SHORT).show();
            ChangeGhe = ConverStringtoGhe(vexm.get(i).getGhe());
            for(int j =0;j<ChangeGhe.size();j++)
                setImageToX(ChangeGhe.get(i));

        }


    }

    public ArrayList<Integer> ConverStringtoGhe(String l) {
        ArrayList<String> str = new ArrayList<>();
        ArrayList<Integer> result = new ArrayList<>();
        String[] words = l.split(" ");
        for (String i : words) {
            result.add(transfer(i));
        }

        return result;
    }

    public Integer transfer(String l) {
        char a = l.charAt(0);
        if (isNumeric(l.substring(1))) {
            return check(a) + Integer.valueOf(l.substring(1));
        } else return Integer.valueOf(l.substring(2)) + 100;
    }

    public int check(char l) {
        switch (l) {
            case 'A':
                return 0;

            case 'B':
                return 10;


            case 'C':
                return 20;

            case 'D':
                return 30;

            case 'E':
                return 40;

            case 'F':
                return 50;

            case 'G':
                return 60;

            case 'H':
                return 70;



        }
        return 0;
    }


    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}