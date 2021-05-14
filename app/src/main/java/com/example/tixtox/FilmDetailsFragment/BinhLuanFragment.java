package com.example.tixtox.FilmDetailsFragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tixtox.Model.Phim;
import com.example.tixtox.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.facebook.FacebookSdk.getApplicationContext;

public class BinhLuanFragment extends Fragment {
    TextView txtRating, txtNosRating;
    float totalScore = 0;
    RatingBar stars;
    Button btnnhapbinhluan;
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


        listComments = view.findViewById(R.id.listbinhluan);

        btnnhapbinhluan = view.findViewById(R.id.nhapbinhluan);
        btnnhapbinhluan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), BinhluandanhgiaActivity.class);
                intent.putExtra("maphim", phim.getMaPhim());
                startActivity(intent);

            }
        });
        displayComment();

        return view;
    }
    private void displayComment() {
        adapter = new ListCommentAdapter(getActivity(), ModelBinhLuan.class,
                R.layout.comment_layout, FirebaseDatabase.getInstance().getReference("Comment").child(phim.getMaPhim())
        );
        listComments.setAdapter(adapter);
        FirebaseDatabase.getInstance().getReference("Comment").child(phim.getMaPhim()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String, Map<String,String>> values = (Map<String, Map<String,String>>) snapshot.getValue();
                ArrayList<ModelBinhLuan> bl = new ArrayList<>();
                if (values != null) {
                    txtNosRating.setText(values.size() + " người bình luận");
                }
                else{
                    txtNosRating.setText( "0 người bình luận");
                }
               
               for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                   ModelBinhLuan modelBinhLuan=dataSnapshot.getValue(ModelBinhLuan.class);
                   bl.add(modelBinhLuan);
               }

               for (ModelBinhLuan modelBinhLuan:bl){
                    totalScore += modelBinhLuan.getRatingScore();
               }
                System.out.println("tong rating "+totalScore/bl.size());
//               System.out.println("size " + bl.size());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        displayComment();
      
    }

    public void setPhim(Phim p)
    {
        this.phim = p;
    }
}