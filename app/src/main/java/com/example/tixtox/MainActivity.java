package com.example.tixtox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

import org.w3c.dom.Text;

import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView txtRegister;
    private FirebaseAuth firebaseAuth;
    EditText editEmail, editPassword;
    Button btnSignIn;
    ProgressBar progressBar;
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
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case  R.id.txtRegister:
                startActivity(new Intent(this, RegisterAcitivy.class));
                break;
            case R.id.btnSignIn:
                userSignIn();
                break;
        }
    }

    public void userSignIn(){
        String mail = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        if (mail.isEmpty())
        {
            editEmail.setError("Mail is empty");
            editEmail.requestFocus();
            return;

        }

        if (password.isEmpty())
        {
            editPassword.setError("Password is empty");
            editPassword.requestFocus();
            return;

        }

        if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches())
        {
            editEmail.setError("Mail is invalid");
            editEmail.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        firebaseAuth.signInWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    // redirect to user profile
                }
                else{
                    Toast.makeText(MainActivity.this, "Failed to login!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}