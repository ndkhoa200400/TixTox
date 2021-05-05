package com.example.tixtox.HeThongRapFragment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.tixtox.R;

public class BangGiaVeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bang_gia_ve);
        ImageView imageView = findViewById(R.id.imgBanggia);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String maCumRap = (String) bundle.get("maCumRap");
        switch (maCumRap)
        {
            case "CGV":
                imageView.setImageResource(R.drawable.cgv_giave);
                break;
            case "BHDStar":
                imageView.setImageResource(R.drawable.bhd_giave);
                break;
            case "CineStar":
                imageView.setImageResource(R.drawable.cinestar_giave);
                break;
            case "Galaxy":
                imageView.setImageResource(R.drawable.galaxy_giave);
                break;
            case "LotteCinima":
                imageView.setImageResource(R.drawable.lotte_giave);
                break;
            case "MegaGS":
                imageView.setImageResource(R.drawable.mega_giave);
                break;
        }
    }
}