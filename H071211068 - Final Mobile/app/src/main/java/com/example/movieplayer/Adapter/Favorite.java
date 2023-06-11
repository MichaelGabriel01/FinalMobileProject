package com.example.movieplayer.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieplayer.R;
import com.example.movieplayer.Activity.MovieDetail;
import com.example.movieplayer.Activity.TvShowDetail;
import com.example.movieplayer.Db.DatabaseHelper;
import com.example.movieplayer.Model.MovieResult;
import com.example.movieplayer.Model.TvResult;

import java.util.ArrayList;
import java.util.List;

public class Favorite extends RecyclerView.Adapter<Favorite.FavViewHolder> {

    private Context context;
    private List<MovieResult> favoriteMovies;
    private List<TvResult> favoriteTVShows;
    private DatabaseHelper databaseHelper;

    public Favorite(Context context) {
        this.context = context;
        favoriteMovies = new ArrayList<>();
        favoriteTVShows = new ArrayList<>();
        databaseHelper = new DatabaseHelper(context);
    }

    public void fetchFavoriteMovies() {
        favoriteMovies = databaseHelper.getFavoriteMovies();
        notifyDataSetChanged();
    }

    public void fetchFavoriteTVShows() {
        favoriteTVShows = databaseHelper.getFavoriteTVShows();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.item_fav, parent, false);
        databaseHelper = new DatabaseHelper(parent.getContext());
        return new FavViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavViewHolder holder, int position) {
        if (position < favoriteMovies.size()) {
            MovieResult movie = favoriteMovies.get(position);
            holder.bindMovie(movie);
            holder.itemView.setOnClickListener(view -> {
                Intent intent = new Intent(context, MovieDetail.class);
                intent.putExtra(MovieDetail.EXTRA_MOVIE, movie);
                context.startActivity(intent);
            });
        } else {
            int tvShowPosition = position - favoriteMovies.size();
            TvResult tvShow = favoriteTVShows.get(tvShowPosition);
            holder.bindTVShow(tvShow);
            holder.itemView.setOnClickListener(view -> {
                Intent intent = new Intent(context, TvShowDetail.class);
                intent.putExtra(TvShowDetail.EXTRA_TV_SHOW, tvShow);
                context.startActivity(intent);
            });
        }
    }





    @Override
    public int getItemCount() {
        return favoriteMovies.size() + favoriteTVShows.size();
    }

    public class FavViewHolder extends RecyclerView.ViewHolder {
        ImageView favimg;
        TextView favtitle,favyear;


        public FavViewHolder(@NonNull View itemView) {
            super(itemView);

            favimg = itemView.findViewById(R.id.favimg);
            favtitle = itemView.findViewById(R.id.favtitle);
            favyear = itemView.findViewById(R.id.favyear);
        }

        public void bindData(Object data) {
            if (data instanceof MovieResult) {
                bindMovie((MovieResult) data);
            } else if (data instanceof TvResult) {
                bindTVShow((TvResult) data);
            }
        }

        private void bindMovie(MovieResult movie) {
            favtitle.setText(movie.getTitle());
            String releaseDate = movie.getReleaseDate();
            if (releaseDate != null && !releaseDate.isEmpty()) {
                String year = releaseDate.substring(0, 4);
                favyear.setText(year);
            } else {
                favyear.setText("");
            }
            Glide.with(context)
                    .load("https://image.tmdb.org/t/p/w185" + movie.getPosterPath())
                    .into(favimg);
        }


        private void bindTVShow(TvResult tvShow) {
            favtitle.setText(tvShow.getName());
            String releaseDate = tvShow.getFirstAirDate();
            if (releaseDate != null && !releaseDate.isEmpty()) {
                String year = releaseDate.substring(0, 4);
                favyear.setText(year);
            } else {
                favyear.setText("");
            }
            Glide.with(context)
                    .load("https://image.tmdb.org/t/p/w185" + tvShow.getPosterPath())
                    .into(favimg);
        }
    }
}
