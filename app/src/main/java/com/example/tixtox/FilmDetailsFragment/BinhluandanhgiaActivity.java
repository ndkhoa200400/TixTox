package com.example.tixtox.FilmDetailsFragment;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tixtox.Model.Phim;
import com.example.tixtox.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import static com.facebook.FacebookSdk.getApplicationContext;

public class BinhluandanhgiaActivity extends AppCompatActivity {
    FloatingActionButton btnSubmit;
    int ratingValue;
    EditText edtnhapbinhluan;
    TextView txtFeedback;
    RatingBar stars;
    String maphim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        maphim = intent.getStringExtra("maphim");

        setContentView(R.layout.binh_luan_danh_gia);
        txtFeedback = findViewById(R.id.txtfeedback);
        btnSubmit = findViewById(R.id.btnSubmit);
        edtnhapbinhluan = findViewById(R.id.edtBinhLuan);
        stars = findViewById(R.id.ratingBar);
        stars.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingValue = (int)ratingBar.getRating();
                switch (ratingValue){
                    case 1:
                        txtFeedback.setText("Tệ!");
                        break;
                    case 2:
                        txtFeedback.setText("Phim không hay lắm.");
                        break;
                    case 3:
                        txtFeedback.setText("Phim khá hay");
                        break;
                    case 4:
                        txtFeedback.setText("Phim hay");
                        break;
                    case 5:
                        txtFeedback.setText("Phim rất hay, mọi người nên đi xem");
                        break;
                    default:
                        txtFeedback.setText("");
                }
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cmt = edtnhapbinhluan.getText().toString();
                if (cmt.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Mời bạn nhập bình luận", Toast.LENGTH_LONG).show();
                }
                else {
                    edtnhapbinhluan.setText("");
                    FirebaseUser user = FirebaseAuth.getInstance()
                            .getCurrentUser();

                    FirebaseDatabase.getInstance()
                            .getReference("Comment")
                            .child(maphim).push().setValue(new ModelBinhLuan(user.getUid().toString(), user.getDisplayName(), cmt, ratingValue));
                    finish();
                }
            }
        });
    }
}