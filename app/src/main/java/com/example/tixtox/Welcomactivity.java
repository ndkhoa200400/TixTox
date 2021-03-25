package com.example.tixtox;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;

import java.io.IOException;

public class Welcomactivity extends AppCompatActivity {
    ImageView myPhoto;
    TextView txtDOB, txtName, txtEmail, txtPhone;
    Button btnSignOut;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcomactivity);

        mAuth = FirebaseAuth.getInstance();

        final FirebaseUser mUser = mAuth.getCurrentUser();

        myPhoto = findViewById(R.id.imageView);

        txtEmail = findViewById(R.id.txtEmail);
        txtName  = findViewById(R.id.txtName);

        btnSignOut = findViewById(R.id.btnSignOut);

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                LoginManager.getInstance().logOut();
                finish();
            }
        });

        if (mUser != null)
        {
            String name = mUser.getDisplayName();
            String email = mUser.getEmail();
            Uri photoURL = mUser.getPhotoUrl();
            if (photoURL != null)
            {
//                GlideApp.with(this).load(photoURL.toString())
//
//                        //.apply(new RequestOptions().transform(new CenterInside(),new RoundedCorners(70)))
//                        .into(myPhoto);
            }

            txtName.setText(name);
            txtEmail.setText(email);

        }
    }
}