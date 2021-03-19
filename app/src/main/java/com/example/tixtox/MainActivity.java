package com.example.tixtox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.internal.LinkedTreeMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView txtRegister;
    private FirebaseAuth firebaseAuth;
    EditText editEmail, editPassword;
    Button btnSignIn;
    ProgressBar progressBar;
    Handler handler;
    ModelPhim modelPhim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        editEmail = (EditText) findViewById(R.id.editEmal);
        editPassword = (EditText) findViewById(R.id.editPw);
        txtRegister = (TextView) findViewById(R.id.txtRegister);

        txtRegister.setOnClickListener(this);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(this);

        progressBar = (ProgressBar) findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.GONE);

        Button test = findViewById(R.id.btnTest);
        test.setOnClickListener(this);

        modelPhim = new ModelPhim(handler);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtRegister:
                startActivity(new Intent(this, RegisterAcitivy.class));
                break;
            case R.id.btnSignIn:
                userSignIn();
                break;
            case R.id.btnTest:
                Thread t = new Thread(){
                    @Override
                    public void run() {
                        ArrayList<Phim> phims = modelPhim.getPhimTheoNgay("01/03/2021", "20/03/2021");
                        if (phims!= null)
                        {
                            for(Phim p: phims)
                            {
                                System.out.println(p.getTenPhim());
                            }
                            MainActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                }
                            });
                        }
                    }
                };

                t.start();
                break;


        }
    }

    public void userSignIn() {
        String mail = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        if (mail.isEmpty()) {
            editEmail.setError("Mail is empty");
            editEmail.requestFocus();
            return;

        }

        if (password.isEmpty()) {
            editPassword.setError("Password is empty");
            editPassword.requestFocus();
            return;

        }

        if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            editEmail.setError("Mail is invalid");
            editEmail.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        firebaseAuth.signInWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // redirect to user profile
                } else {
                    Toast.makeText(MainActivity.this, "Failed to login!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}