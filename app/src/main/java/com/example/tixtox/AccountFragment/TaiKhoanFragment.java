package com.example.tixtox.AccountFragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.tixtox.ChinhSuaTaiKhoanActivity;
import com.example.tixtox.DatVe.DanhSachVe;
import com.example.tixtox.HomeActivity;
import com.example.tixtox.R;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class TaiKhoanFragment extends Fragment {
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    ImageView myPhoto;
    Button btnSignOut, btnEditAccount;
    TextView txtDOB, txtName, txtEmail, txtPhone,txtVeDaMua;
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
        View view = inflater.inflate(R.layout.fragment_tai_khoan, container, false);
        progressBar = view.findViewById(R.id.progressBar_Account);
        progressBar.setVisibility(View.VISIBLE);
        txtEmail = view.findViewById(R.id.txtEmail_EditAccount);
        txtDOB = view.findViewById(R.id.editDOB);
        txtName = view.findViewById(R.id.txtName_EditAccount);
        txtPhone = view.findViewById(R.id.editPhone);
        txtVeDaMua = view.findViewById(R.id.txtVeDaMua);
        myPhoto = view.findViewById(R.id.imgAvatar_EditAccount);
        btnSignOut = view.findViewById(R.id.btnSignOut_Account);
        btnEditAccount = view.findViewById(R.id.btnSubmit);

        String name = firebaseUser.getDisplayName();
        String email = firebaseUser.getEmail();
        String phone = firebaseUser.getPhoneNumber();
        Uri photouri = firebaseUser.getPhotoUrl();
        String photoURL = "";

        txtName.setText(name);
        txtEmail.setText(email);
        txtPhone.setText(phone);
        if (photouri != null) {
            photoURL = photouri.toString();
            // Resize lại ảnh để hiển thị tốt
            for (UserInfo userInfo : firebaseUser.getProviderData()) {
                if (userInfo.getProviderId().equals("facebook.com")) {
                    photoURL += "?height=300";
                } else if (userInfo.getProviderId().equals("google.com")) {
                    photoURL = photoURL.replace("s96-c", "s400-c");

                }
            }

            Glide.with(this).load(photoURL)
                    .apply(new RequestOptions()
                            .fitCenter()
                            .format(DecodeFormat.PREFER_ARGB_8888)
                            .override(Target.SIZE_ORIGINAL))
                    .into(myPhoto);
        } else {
            myPhoto.setImageResource(R.drawable.icon_user);
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

        btnEditAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    startActivity(new Intent(view.getContext(), ChinhSuaTaiKhoanActivity.class));
            }
        });


        // Láy thông tin trên firebase
        FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String, String> values = (Map<String, String>) snapshot.getValue();
                if (values.get("dob") != null)
                {
                    txtDOB.setText(values.get("dob"));
                }
                if (values.get("phone") != null)
                {
                    txtPhone.setText(values.get("phone"));
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        txtVeDaMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), DanhSachVe.class);
                intent.putExtra("MaTK",FirebaseAuth.getInstance()
                        .getCurrentUser().getUid());
                startActivity(intent);
            }
        });
        return view;
    }
}