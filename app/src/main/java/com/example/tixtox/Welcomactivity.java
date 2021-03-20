package com.example.tixtox;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class Welcomactivity extends AppCompatActivity {
    ImageView myPhoto;
    TextView txtEmail, txtName;
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
            String photoURL = mUser.getPhotoUrl().toString();
            Glide.with(this).load(photoURL).into(myPhoto);
            txtName.setText(name);
            txtEmail.setText(email);
        }
    }
}