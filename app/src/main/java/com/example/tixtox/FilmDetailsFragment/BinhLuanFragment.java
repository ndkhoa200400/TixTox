package com.example.tixtox.FilmDetailsFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tixtox.Forum.Messenger;
import com.example.tixtox.Model.Phim;
import com.example.tixtox.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class BinhLuanFragment extends Fragment {
    TextView txtRating, txtNosRating;
    EditText edtComment;
    FloatingActionButton btnSend;
    ListView listComments;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();
    private Phim phim;
    private FirebaseListAdapter<ModelBinhLuan> adapter;

    public BinhLuanFragment() {}

    public static BinhLuanFragment newInstance() {
        BinhLuanFragment fragment = new BinhLuanFragment();
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
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_binh_luan, container, false);
        txtRating = view.findViewById(R.id.txtRating);
        txtNosRating = view.findViewById(R.id.txtNosRating);
        listComments = view.findViewById(R.id.listComemts);
        edtComment = view.findViewById(R.id.edtComment);
        btnSend = view.findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cmt = edtComment.getText().toString();
                edtComment.setText("");
//                btnSend.setEnabled(false);



                FirebaseUser user = FirebaseAuth.getInstance()
                        .getCurrentUser();

                FirebaseDatabase.getInstance()
                        .getReference("Comment")

                        .child(phim.getMaPhim()).push().setValue(new ModelBinhLuan(user.getUid().toString(), user.getDisplayName(), cmt));
                displayComment();
            }
        });
        displayComment();
        return view;
    }
    private void displayComment() {

        adapter = new ListCommetAdapter(getActivity(), ModelBinhLuan.class,
                R.layout.comment_layout, FirebaseDatabase.getInstance().getReference("Comment").child(phim.getMaPhim())
        );
        listComments.setAdapter(adapter);
    }
    public void setPhim(Phim p)
    {
        this.phim = p;
    }
}