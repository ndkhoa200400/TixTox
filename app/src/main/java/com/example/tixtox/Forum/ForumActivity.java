package com.example.tixtox.Forum;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tixtox.R;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class ForumActivity extends AppCompatActivity {
    EditText editMessenge;
    FloatingActionButton btnSend;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();
    private FirebaseListAdapter<Messenger> adapter;
    ListView listMessenge;
    private static int SIGN_IN_REQUEST_CODE = 9600;

    public ForumActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_forum);
        editMessenge = (EditText) findViewById(R.id.editMessenge);
        listMessenge = (ListView) findViewById(R.id.list_of_messages);
        btnSend = (FloatingActionButton) findViewById(R.id.btnSend);
//        btnSend.setEnabled(false);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = editMessenge.getText().toString();
                editMessenge.setText("");
//                btnSend.setEnabled(false);
                FirebaseDatabase.getInstance()
                        .getReference("msg")
                        .push()
                        .setValue(new Messenger(msg,
                                FirebaseAuth.getInstance()
                                        .getCurrentUser()
                                        .getDisplayName())
                        );
                displayChatMessages();
            }
        });
        if (user == null) {
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .build(),
                    SIGN_IN_REQUEST_CODE
            );
        } else {
            Toast.makeText(this,
                    "Welcome " + FirebaseAuth.getInstance()
                            .getCurrentUser()
                            .getDisplayName(),
                    Toast.LENGTH_LONG)
                    .show();
            displayChatMessages();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SIGN_IN_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this,
                        "Successfully signed in. Welcome!",
                        Toast.LENGTH_LONG)
                        .show();
                displayChatMessages();
            } else {
                Toast.makeText(this,
                        "We couldn't sign you in. Please try again later.",
                        Toast.LENGTH_LONG)
                        .show();

                finish();
            }
        }
    }

    private void displayChatMessages() {

        adapter = new MessageAdapter(this, Messenger.class,
                R.layout.messenge, FirebaseDatabase.getInstance().getReference("msg")
        );

        listMessenge.setAdapter(adapter);
    }

}