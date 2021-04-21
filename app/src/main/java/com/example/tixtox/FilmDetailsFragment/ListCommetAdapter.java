package com.example.tixtox.FilmDetailsFragment;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tixtox.Forum.Messenger;
import com.example.tixtox.HomeActivity;
import com.example.tixtox.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.Query;

public class ListCommetAdapter extends FirebaseListAdapter<ModelBinhLuan> {
    private Activity activity;
    public ListCommetAdapter(Activity activity, Class<ModelBinhLuan> modelClass, int modelLayout, Query ref) {
        super(activity, modelClass, modelLayout, ref);
        this.activity = activity;
    }

    @Override
    protected void populateView(View v, ModelBinhLuan model, int position) {
        TextView txtName = v.findViewById(R.id.txtName);
        TextView txtContent = v.findViewById(R.id.txtContent);
        txtName.setText(model.getTxtName());
        txtContent.setText(model.getTxtContent());
    }
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ModelBinhLuan comment = getItem(position);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if (user != null) {
//            view = activity.getLayoutInflater().inflate(R.layout.comment_layout, viewGroup, false);
//        }
//        else
//            view = activity.getLayoutInflater().inflate(R.layout.comment_layout, viewGroup, false);
        view = activity.getLayoutInflater().inflate(R.layout.comment_layout, viewGroup, false);
        populateView(view, comment, position);

        return view;
    }
    @Override
    public int getViewTypeCount() {
        // return the total number of view types. this value should never change
        // at runtime
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        // return a value between 0 and (getViewTypeCount - 1)
        return position % 2;
    }
}
