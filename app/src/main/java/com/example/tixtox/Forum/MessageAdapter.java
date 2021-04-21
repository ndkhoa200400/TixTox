package com.example.tixtox.Forum;

import android.app.Activity;
import android.text.format.DateFormat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tixtox.HomeActivity;
import com.example.tixtox.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.Query;

public class MessageAdapter extends FirebaseListAdapter<Messenger> {
    private Activity activity;
    public MessageAdapter(Activity activity, Class<Messenger> modelClass, int modelLayout, Query ref) {
        super(activity, modelClass, modelLayout, ref);
        this.activity = (HomeActivity) activity;
    }

    @Override
    protected void populateView(View v, Messenger model, int position) {
        TextView txtTaikhoan = (TextView)v.findViewById(R.id.message_user);
        TextView txtTime = (TextView)v.findViewById(R.id.message_time);
        TextView txtMessage = (TextView)v.findViewById(R.id.message_text);

        txtMessage.setText(model.getMessageText());
        txtTaikhoan.setText(model.getMessageUser());

        txtTime.setText(DateFormat.format("dd/MM/yyyy (HH:mm:ss)",
                model.getMessageTime()));
    }
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        Messenger messenger = getItem(position);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            if (messenger.getUserID().equals(user.getUid()))
                view = activity.getLayoutInflater().inflate(R.layout.message_in, viewGroup, false);
            else
                view = activity.getLayoutInflater().inflate(R.layout.messenge_out, viewGroup, false);
        }
        else
            view = activity.getLayoutInflater().inflate(R.layout.messenge_out, viewGroup, false);

        //generating view
        //view = activity.getLayoutInflater().inflate(R.layout.messenge, viewGroup, false);
        populateView(view, messenger, position);

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
