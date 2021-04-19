package com.example.tixtox.AccountFragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.tixtox.Forum.Messenger;
import com.example.tixtox.HomeActivity;
import com.example.tixtox.Model.KhuyenMai;
import com.example.tixtox.R;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class AdminFragment extends Fragment {

    ListView listView;
    ProgressBar progressBar;
    public static AdminFragment newInstance() {
        AdminFragment fragment = new AdminFragment();


        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_admin, container, false);

        progressBar = v.findViewById(R.id.progressBar4);
        progressBar.setVisibility(View.VISIBLE);
        displayListView();

        EditText editTenKm = v.findViewById(R.id.editTenKm);
        EditText editSoGiam = v.findViewById(R.id.editSoGiam);
        CheckBox chkPhanTram = v.findViewById(R.id.chkPhanTram);

        listView =  v.findViewById(R.id.listviewKhuyenMai);
        Button btnThemMoi = v.findViewById(R.id.btnThemMoiKM);

        btnThemMoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String tenKM = editTenKm.getText().toString();
                double discount = Double.parseDouble(editSoGiam.getText().toString());
                int isPercentage = chkPhanTram.isChecked() == true ? 1 : 2;

                if (isPercentage == 2 && discount > 100)
                {
                    editSoGiam.setError("Số tiền giảm không hợp lệ!");
                    return;
                }

                KhuyenMai khuyenMai = new KhuyenMai(isPercentage, tenKM, discount);


                FirebaseDatabase.getInstance()
                        .getReference("KhuyenMai").push().setValue(khuyenMai);
                displayListView();

            }
        });


        Button btnDangXuat = v.findViewById(R.id.btnDangXuat);
        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                firebaseAuth.signOut();


                startActivity(new Intent(v.getContext(), HomeActivity.class));

            }
        });
        return v;

    }

    void displayListView()
    {
        FirebaseDatabase.getInstance()
                .getReference("KhuyenMai").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot == null)
                {

                }
                else{

                    HashMap<String, HashMap<String, String>> khuyenMais =(HashMap<String, HashMap<String, String>>) snapshot.getValue();
                    ArrayList<KhuyenMai> khuyenMaiArrayList = new ArrayList<>();
                    for (String key: khuyenMais.keySet())
                    {
                        HashMap<String, String> obj = khuyenMais.get(key);
                        String name = obj.get("name");
                        double discount = Double.parseDouble(String.valueOf(obj.get("discount")));
                        int type = Integer.parseInt(String.valueOf(obj.get("type")));

                        khuyenMaiArrayList.add(new KhuyenMai(type, name, discount));

                    }

                    KhuyenMaiAdapter khuyenMaiAdapter = new KhuyenMaiAdapter(getContext(), R.layout.layout_khuyen_mai, khuyenMaiArrayList);
                    listView.setAdapter(khuyenMaiAdapter);
                    progressBar.setVisibility(View.GONE);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}