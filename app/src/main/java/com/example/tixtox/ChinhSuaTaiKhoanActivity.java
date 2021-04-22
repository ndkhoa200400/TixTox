package com.example.tixtox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class ChinhSuaTaiKhoanActivity extends AppCompatActivity {
    TextView txtEmail;
    EditText editPhone, editName;
    TextView editDOB;
    Button btnSubmit, btnCancel;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chinh_sua_tai_khoan);

        editName = findViewById(R.id.editName_EditAccount);
        txtEmail = findViewById(R.id.txtEmail_EditAccount);
        editDOB = findViewById(R.id.editDOB);
        editPhone = findViewById(R.id.editPhone);

        txtEmail.setText(user.getEmail());


        btnSubmit = findViewById(R.id.btnSubmit);

        editDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDateTime();
            }
        });

        // Lấy thông tin hiện lên
        FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String, String> values = (Map<String, String>) snapshot.getValue();
                if (values != null) {
                    if (values.get("dob") != null) {
                        editDOB.setText(values.get("dob"));
                    }
                    if (values.get("phone") != null) {
                        editPhone.setText(values.get("phone"));
                    }
                    if (values.get("fullname") != null) {
                        editName.setText(values.get("fullname"));
                    }
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
                String newName = editName.getText().toString();
                Pattern pattern = Pattern.compile("^\\d{10}$");
                if (!newPhone.isEmpty())
                    if (newPhone.length() < 10 || !pattern.matcher(newPhone).matches()) {
                        editPhone.setError("Số điện thoại không hợp lệ!");
                        return;
                    }
                Map<String, Object> newInfo = new HashMap<>();
                newInfo.put("fullname", newName);
                newInfo.put("phone", newPhone);
                newInfo.put("dob", newDOB);
                FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).updateChildren(newInfo);
                finish();

            }
        });

        btnCancel = findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    public void selectDateTime() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                c.set(year, month, day);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                editDOB.setText(simpleDateFormat.format(c.getTime()));
            }
        }, year, month, day);
        datePickerDialog.show();

    }

}