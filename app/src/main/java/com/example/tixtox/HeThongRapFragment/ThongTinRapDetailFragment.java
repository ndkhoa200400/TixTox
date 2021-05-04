package com.example.tixtox.HeThongRapFragment;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.tixtox.HomeFragment.PhimsFragment;
import com.example.tixtox.Model.ModelRap;
import com.example.tixtox.Model.Rap.RapDetail;
import com.example.tixtox.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThongTinRapDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThongTinRapDetailFragment extends Fragment {
    RapDetail rapDetail;
    String maCumRap;


    public ThongTinRapDetailFragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static ThongTinRapDetailFragment newInstance(RapDetail rapDetail, String maCumRap) {
        ThongTinRapDetailFragment fragment = new ThongTinRapDetailFragment();
        fragment.rapDetail = rapDetail;
        fragment.maCumRap = maCumRap;
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
        View v = inflater.inflate(R.layout.fragment_thong_tin_rap_detail, container, false);

        TextView txtTenRap = v.findViewById(R.id.txtTenRapDetail);
        TextView txtDiaChi = v.findViewById(R.id.txtDiaChiRap);
        ConstraintLayout layoutDiaChi = v.findViewById(R.id.layoutDiaChi);

        ConstraintLayout layoutGiaVe = v.findViewById(R.id.layoutGiaVe);
        txtTenRap.setText(this.rapDetail.getTenRap());

        txtDiaChi.setText(this.rapDetail.getDiaChi());

        layoutDiaChi.setOnClickListener(new View.OnClickListener() {
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
        layoutGiaVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(getContext(), BangGiaVeActivity.class);
                intent.putExtra("maCumRap",maCumRap);

                startActivity(intent);

            }
        });
        Geocoder geocoder = new Geocoder(getContext());
        try {
            List<Address> addresses = geocoder.getFromLocationName(this.rapDetail.getDiaChi(), 1);
            if (addresses != null)
            {
                Address a = addresses.get(0);
                MapsFragment mapsFragment = MapsFragment.newInstance(new LatLng(a.getLatitude(), a.getLongitude()));
                FragmentTransaction trasection = getChildFragmentManager().beginTransaction();
                trasection.replace(R.id.fragment, mapsFragment);
                trasection.commit();
            }

        }catch (IOException e)
        {

        }

        ((ProgressBar)getActivity().findViewById(R.id.progressBar7)).setVisibility(View.GONE);

        return v;
    }
}