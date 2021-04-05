package com.example.tixtox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class ChinhSuaTaiKhoanActivity extends AppCompatActivity {
    TextView txtName, txtEmail;
    EditText editPhone, editDOB;
    Button btnSubmit;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chinh_sua_tai_khoan);

        txtName = findViewById(R.id.txtName_EditAccount);
        txtEmail = findViewById(R.id.txtEmail_EditAccount);
        editDOB = findViewById(R.id.editDOB);
        editPhone = findViewById(R.id.editPhone);

        txtEmail.setText(user.getEmail());
        txtName.setText(user.getDisplayName());

        btnSubmit = findViewById(R.id.btnSubmit);

        // Lấy thông tin hiện lên
        FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String, String> values = (Map<String, String>) snapshot.getValue();
                if (values.get("dob") != null) {
                    editDOB.setText(values.get("dob"));
                }
                if (values.get("phone") != null) {
                    editPhone.setText(values.get("phone"));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newDOB = editDOB.getText().toString();
                String newPhone = editPhone.getText().toString();

                Pattern pattern = Pattern.compile("^\\d{10}$");
                if (newPhone.length() < 10 && !pattern.matcher(newPhone).matches()) {
                    Toast.makeText(getApplicationContext(), "Số điện thoại không hợp lệ", Toast.LENGTH_LONG).show();
                    return;
                }
                Map<String, Object> newInfo = new HashMap<>();
                newInfo.put("phone", newPhone);
                newInfo.put("dob", newDOB);
                FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).updateChildren(newInfo);
                finish();

            }
        });
    }
}