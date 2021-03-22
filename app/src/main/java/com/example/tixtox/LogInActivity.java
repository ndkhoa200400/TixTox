package com.example.tixtox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import com.google.firebase.auth.FirebaseUser;

import com.facebook.FacebookSdk;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {
    TextView txtRegister;
    private FirebaseAuth firebaseAuth;
    EditText editEmail, editPassword;
    Button btnSignIn;
    ProgressBar progressBar;
    Handler handler;
    ModelPhim modelPhim;
    CallbackManager mCallbackManager;
    LoginButton loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);
        // Initialize firebase
        firebaseAuth = FirebaseAuth.getInstance();

        editEmail = (EditText) findViewById(R.id.editEmal);
        editPassword = (EditText) findViewById(R.id.editPw);
        txtRegister = (TextView) findViewById(R.id.txtRegister);

        txtRegister.setOnClickListener(this);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(this);

        progressBar = (ProgressBar) findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.GONE);




        modelPhim = new ModelPhim();
        FacebookSdk.sdkInitialize(LogInActivity.this);
        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();
        loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                error.printStackTrace();
                try {
                    PackageInfo info = getPackageManager().getPackageInfo("com.example.tixtox", PackageManager.GET_SIGNATURES);
                    for (Signature signature : info.signatures) {
                        MessageDigest md = MessageDigest.getInstance("SHA");
                        md.update(signature.toByteArray());
                        Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    Log.e("KeyHash:", e.toString());
                } catch (NoSuchAlgorithmException e) {
                    Log.e("KeyHash:", e.toString());
                }
            }
        });
    }
    private void handleFacebookAccessToken(AccessToken token) {

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LogInActivity.this, "Authenciation failed",  Toast.LENGTH_LONG).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null)
        {
           Intent intent = new Intent(LogInActivity.this, Welcomactivity.class);
           startActivity(intent);
        }
        else{
            Toast.makeText(this, "Please sign in to continue",  Toast.LENGTH_LONG).show();
        }
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
//            case R.id.btnTest:
//                Thread t = new Thread(){
//                    @Override
//                    public void run() {
//                        ArrayList<Phim> phims = modelPhim.getPhimTheoNgay("01/03/2021", "20/03/2021");
//                        if (phims!= null)
//                        {
//                            for(Phim p: phims)
//                            {
//                                System.out.println(p.getTenPhim());
//                            }
//                            LogInActivity.this.runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//
//                                }
//                            });
//                        }
//                    }
//                };
//
//                t.start();
                //break;


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
                    progressBar.setVisibility(View.GONE);
                    userSignIn();

                } else {
                    Toast.makeText(LogInActivity.this, "Failed to login!", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if (currentUser != null)
        {
            updateUI(currentUser);
        }
    }
}