package com.example.tixtox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterAcitivy extends AppCompatActivity {
    Button btnRegister;
    EditText editEmail, editUsername, editPassword;
    ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_acitivy);

        editEmail = (EditText) findViewById(R.id.editEmailR);
        editUsername = (EditText) findViewById(R.id.editUsernameR);
        editPassword = (EditText) findViewById(R.id.editPasswordR);
        progressBar = (ProgressBar) findViewById(R.id.progressBar) ;
        progressBar.setVisibility(View.GONE);

        firebaseAuth = FirebaseAuth.getInstance();

        btnRegister = (Button) findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
    }

    public void register()
    {
        String mail = editEmail.getText().toString().trim();
        String username = editUsername.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        if (mail.isEmpty())
        {
            editEmail.setError("Mail is empty");
            editEmail.requestFocus();
            return;

        }
        if (username.isEmpty())
        {
            editUsername.setError("Username is empty");
            editUsername.requestFocus();
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
        firebaseAuth.createUserWithEmailAndPassword(mail,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                                User user = new User(username, mail);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful())
                                    {
                                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                .setDisplayName(username).build();
                                        FirebaseUser user =  FirebaseAuth.getInstance().getCurrentUser();
                                        user.updateProfile(profileUpdates);
                                        Toast.makeText(RegisterAcitivy.this, "User has been registered successfully", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                        finish();
                                    }
                                    else{

                                        Toast.makeText(RegisterAcitivy.this, "Failed to registered", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(RegisterAcitivy.this, "Failed to registered", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }
}