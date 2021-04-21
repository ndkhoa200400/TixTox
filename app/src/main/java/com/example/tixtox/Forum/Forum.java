package com.example.tixtox.Forum;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tixtox.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import static com.facebook.FacebookSdk.getApplicationContext;

public class Forum extends Fragment {
    EditText editMessenge;
    FloatingActionButton btnSend;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();
    ListView messengesList;

    private FirebaseListAdapter<Messenger> adapter;
    ListView listMessenge;
    public Forum() {}

    public static Forum newInstance() {
        Forum fragment = new Forum();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forum, container, false);
        messengesList = view.findViewById(R.id.list_of_messages);
        editMessenge = view.findViewById(R.id.editMessenge);
        listMessenge = (ListView) view.findViewById(R.id.list_of_messages);
        btnSend = view.findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (auth == null)
                    Toast.makeText(getApplicationContext(), "Chưa Đăng Nhập", Toast.LENGTH_SHORT).show();
                else{
                String msg = editMessenge.getText().toString();
                if (msg.length() == 0){
                    Toast.makeText(getApplicationContext(), "Mời bạn nhập bình luận", Toast.LENGTH_SHORT).show();
                }
                else {
//                    btnSend.setEnabled(true);
                    editMessenge.setText("");
//                btnSend.setEnabled(false);
                FirebaseDatabase.getInstance()
                        .getReference("Msg")
                        .push()
                        .setValue(new Messenger(msg,
                                FirebaseAuth.getInstance()
                                        .getCurrentUser()
                                        .getDisplayName(), FirebaseAuth.getInstance()
                                .getCurrentUser().getUid())
                        );
                displayChatMessages();
                }
                }
            }
        });
        displayChatMessages();
        return view;
    }

    private void displayChatMessages() {
        adapter = new MessageAdapter(getActivity(), Messenger.class,
                R.layout.messenge_out, FirebaseDatabase.getInstance().getReference("Msg")
        );
        listMessenge.setAdapter(adapter);
    }

}