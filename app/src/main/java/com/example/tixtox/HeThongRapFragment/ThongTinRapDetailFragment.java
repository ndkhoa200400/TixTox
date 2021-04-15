package com.example.tixtox.HeThongRapFragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tixtox.Model.Rap.RapDetail;
import com.example.tixtox.R;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThongTinRapDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThongTinRapDetailFragment extends Fragment {
    RapDetail rapDetail;


    public ThongTinRapDetailFragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static ThongTinRapDetailFragment newInstance(RapDetail rapDetail) {
        ThongTinRapDetailFragment fragment = new ThongTinRapDetailFragment();
        fragment.setRapDetail(rapDetail);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void setRapDetail(RapDetail rapDetail) {
        this.rapDetail = rapDetail;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_thong_tin_rap_detail, container, false);

        TextView txtTenRap = v.findViewById(R.id.txtTenRapDetail);
        TextView txtDiaChi = v.findViewById(R.id.txtDiaChiRap);

        txtTenRap.setText(this.rapDetail.getTenRap());

        txtDiaChi.setText(this.rapDetail.getDiaChi());

        txtDiaChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Khi ấn vào text sẽ nhảy sang google map

                // Câu query địa chỉ để gán vào google map
                String query = rapDetail.getDiaChi();
                Uri gmmIntentUri = Uri.parse("geo:0,0?q="+ query);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });



        return v;
    }
}