package com.example.tixtox.AccountFragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterInside;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.tixtox.GlideApp;
import com.example.tixtox.HomeActivity;
import com.example.tixtox.LogInActivity;
import com.example.tixtox.R;
import com.example.tixtox.User;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaiKhoanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaiKhoanFragment extends Fragment {
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    ImageView myPhoto;
    Button btnSignOut;
    TextView txtDOB, txtName, txtEmail, txtPhone;
    ProgressBar progressBar;

    public TaiKhoanFragment() {
        // Required empty public constructor
    }

    public static TaiKhoanFragment newInstance() {
        TaiKhoanFragment fragment = new TaiKhoanFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseUser = firebaseAuth.getCurrentUser();



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_tai_khoan, container, false);
        progressBar = view.findViewById(R.id.progressBar_Account);
        progressBar.setVisibility(View.VISIBLE);
        txtEmail = view.findViewById(R.id.txtEmail_Account);
        txtDOB = view.findViewById(R.id.txtDOB_Account);
        txtName = view.findViewById(R.id.txtAccountName_Account);
        txtPhone = view.findViewById(R.id.txtPhone_Account);
        myPhoto = view.findViewById(R.id.imgAvatar_Account);
        btnSignOut = view.findViewById(R.id.btnSignOut_Account);

        String name = firebaseUser.getDisplayName();
        String email = firebaseUser.getEmail();
        String phone = firebaseUser.getPhoneNumber();

        Uri photoURL = firebaseUser.getPhotoUrl();

        txtName.setText(name);
        txtEmail.setText(email);
        txtPhone.setText(phone);
        if (photoURL != null)
        {
            Glide.with(this).load(photoURL.toString())
                      //  .apply(new RequestOptions().transform(new CenterInside(),new RoundedCorners(70)))
                        .into(myPhoto);
        }

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();

                for (UserInfo userInfo : firebaseUser.getProviderData()) {
                    if (userInfo.getProviderId().equals("facebook.com")) {
                        LoginManager.getInstance().logOut();
                    }
                }

                startActivity(new Intent(view.getContext(), HomeActivity.class));
            }
        });
        progressBar.setVisibility(View.GONE);

        if (name.isEmpty())
        {
            progressBar.setVisibility(View.VISIBLE);
            firebaseDatabase.getReference("Users").child("9BbnfecEY1dgZnyqLSm3TfH2I6t2").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String name2 = snapshot.child("fullname").getValue().toString();
                    if (!name2.isEmpty())
                    {
                        txtName.setText(name2);
                        progressBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });

        }
        return view;
    }
}