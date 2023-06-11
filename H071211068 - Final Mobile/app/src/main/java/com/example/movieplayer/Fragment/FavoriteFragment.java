package com.example.movieplayer.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.movieplayer.R;
import com.example.movieplayer.Adapter.Favorite;
import com.example.movieplayer.Db.DatabaseHelper;
import com.example.movieplayer.Model.MovieResult;
import com.example.movieplayer.Model.TvResult;

import java.util.List;

public class FavoriteFragment extends Fragment {

    private RecyclerView favRV;
    private Favorite favAdapter;
    private DatabaseHelper databaseHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fav, container, false);

        favRV = view.findViewById(R.id.favRV);
        favRV.setLayoutManager(new GridLayoutManager(getContext(), 2));
        favAdapter = new Favorite(getContext());
        favRV.setAdapter(favAdapter);

        databaseHelper = new DatabaseHelper(getContext());

        favAdapter.fetchFavoriteMovies();
        favAdapter.fetchFavoriteTVShows();

        return view;
    }
}
