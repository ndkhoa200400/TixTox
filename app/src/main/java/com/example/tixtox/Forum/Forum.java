package com.example.tixtox.Forum;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.example.tixtox.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

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
                String msg = editMessenge.getText().toString();
                editMessenge.setText("");
//                btnSend.setEnabled(false);
                FirebaseDatabase.getInstance()
                        .getReference("msg")
                        .push()
                        .setValue(new Messenger(msg,
                                FirebaseAuth.getInstance()
                                        .getCurrentUser()
                                        .getDisplayName(), FirebaseAuth.getInstance()
                                .getCurrentUser().getUid())
                        );
                displayChatMessages();
            }
        });
        displayChatMessages();
        return view;
    }

    private void displayChatMessages() {

        adapter = new MessageAdapter(getActivity(), Messenger.class,
                R.layout.messenge_out, FirebaseDatabase.getInstance().getReference("msg")
        );

        listMessenge.setAdapter(adapter);
    }
}