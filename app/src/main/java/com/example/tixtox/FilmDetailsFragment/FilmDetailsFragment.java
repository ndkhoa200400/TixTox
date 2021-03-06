package com.example.tixtox.FilmDetailsFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tixtox.Model.Phim;
import com.example.tixtox.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FilmDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FilmDetailsFragment extends Fragment {
    Phim phim;
    ImageView poster;
    TextView txtFilmName, txtReleaseDate, txtRating, txtContent;
    public FilmDetailsFragment() {
    }

    public static FilmDetailsFragment newInstance() {
        FilmDetailsFragment fragment = new FilmDetailsFragment();
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

        View view =  inflater.inflate(R.layout.fragment_thong_tin_phim, container, false);

        poster = view.findViewById(R.id.imgPoster);
        txtFilmName = view.findViewById(R.id.txtFilmName);
        txtRating = view.findViewById(R.id.txtRating);
        txtReleaseDate = view.findViewById(R.id.txtReleaseDate);
        txtContent = view.findViewById(R.id.txtContent);

        txtFilmName.setText(phim.getTenPhim());
        txtReleaseDate.setText(phim.getNgayKhoiChieu());
        txtRating.setText(phim.getDanhGia());
        txtContent.setText(phim.getMoTa());

        Glide.with(this).load(phim.getHinhAnh().toString())
                //  .apply(new RequestOptions().transform(new CenterInside(),new RoundedCorners(70)))
                .into(poster);

        return view;
    }

    public void setPhim(Phim p)
    {
        this.phim = p;
    }
}