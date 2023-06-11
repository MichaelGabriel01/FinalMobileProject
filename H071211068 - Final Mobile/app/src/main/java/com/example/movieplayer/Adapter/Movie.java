package com.example.movieplayer.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieplayer.Activity.MovieDetail;
import com.example.movieplayer.Model.MovieResult;
import com.example.movieplayer.R;

import java.util.List;

public class Movie extends RecyclerView.Adapter<Movie.MovieViewHolder> {

    private Context context;
    private List<MovieResult> movieList;

    public Movie(Context context, List<MovieResult> movieList) {
        this.context = context;
        this.movieList = movieList;
    }
    public void addMovies(List<MovieResult> newMovies) {
        int startPos = movieList.size();
        movieList.addAll(newMovies);
        notifyItemRangeInserted(startPos, newMovies.size());
    }
    @NonNull
    @Override
    public Movie.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.item_movie, parent, false );

        Movie.MovieViewHolder viewHolder = new Movie.MovieViewHolder(view);
        viewHolder.movieLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(parent.getContext(), MovieDetail.class);
                MovieResult movieResult = new MovieResult();
                movieResult.setTitle(movieList.get(viewHolder.getAdapterPosition()).getTitle());
                movieResult.setOverview(movieList.get(viewHolder.getAdapterPosition()).getOverview());
                movieResult.setBackdropPath(movieList.get(viewHolder.getAdapterPosition()).getBackdropPath());
                movieResult.setVoteAverage(movieList.get(viewHolder.getAdapterPosition()).getVoteAverage());
                movieResult.setReleaseDate(movieList.get(viewHolder.getAdapterPosition()).getReleaseDate());
                movieResult.setPosterPath(movieList.get(viewHolder.getAdapterPosition()).getPosterPath());
                intent.putExtra(MovieDetail.EXTRA_MOVIE, movieResult);
                parent.getContext().startActivity(intent);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Movie.MovieViewHolder holder, int position) {
        holder.movieTitle.setText(movieList.get(position).getTitle());
        String releaseDate = movieList.get(position).getReleaseDate();
        if (releaseDate != null && !releaseDate.isEmpty()) {
            String year = releaseDate.substring(0, 4);
            holder.movieYear.setText(year);
        } else {
            holder.movieYear.setText("");
        }
        Glide.with(context).load("https://image.tmdb.org/t/p/w185" + movieList.get(position).getPosterPath())
                .into(holder.moviePoster)
        ;
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }


    public class MovieViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout movieLayout;
        ImageView moviePoster;
        TextView movieTitle, movieYear;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            moviePoster = itemView.findViewById(R.id.movieimg);
            movieTitle = itemView.findViewById(R.id.movietitle);
            movieYear = itemView.findViewById(R.id.movieyear);
            movieLayout = itemView.findViewById(R.id.movielayout);

            movieLayout.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    MovieResult movie = movieList.get(position);
                    Intent intent = new Intent(context, MovieDetail.class);
                    intent.putExtra(MovieDetail.EXTRA_MOVIE, movie);
                    context.startActivity(intent);
                }
            });
        }

}}
