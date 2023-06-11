package com.example.movieplayer.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.movieplayer.R;
import com.example.movieplayer.Db.DatabaseHelper;
import com.example.movieplayer.Fragment.MovieFragment;
import com.example.movieplayer.Model.MovieResult;

import java.util.Objects;

public class MovieDetail extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_movie";
    String MovieTitle,MovieDate,MovieDesc,MoviePp,MovieBp;
    Double MovieRate;
    ImageView movielogo,movProf,movBp,movFav,movBackbt;
    TextView movtitle,movDate,movrate,movdesc;
    MovieResult movieResult;

    DatabaseHelper databaseHelper;
    boolean isFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        databaseHelper = new DatabaseHelper(this);


        movielogo = findViewById(R.id.movielogo);
        movProf = findViewById(R.id.moviebg);
        movBp = findViewById(R.id.moviepost);
        movFav= findViewById(R.id.moviefavbtn);
        movBackbt= findViewById(R.id.moviebackbtn);

        movtitle = findViewById(R.id.movietitle);
        movDate = findViewById(R.id.moviedate);
        movrate= findViewById(R.id.movierating);
        movdesc= findViewById(R.id.moviedesc);

        movieResult = getIntent().getParcelableExtra(EXTRA_MOVIE);
        MovieTitle = movieResult.getTitle();
        MovieDesc = movieResult.getOverview();
        MovieDate = movieResult.getReleaseDate();
        MovieRate = movieResult.getVoteAverage();
        MoviePp = movieResult.getPosterPath();
        MovieBp = movieResult.getBackdropPath();

        movtitle.setText(MovieTitle);
        movDate.setText(MovieDate);
        movrate.setText(String.valueOf(MovieRate));
        movdesc.setText(MovieDesc);

        Glide.with(getApplicationContext())
                .load("https://image.tmdb.org/t/p/w185"+ MoviePp)
                .into(movProf);
        Glide.with(getApplicationContext())
                .load("https://image.tmdb.org/t/p/original"+ MovieBp)
                .into(movBp);

        if (MovieTitle != null) {
            isFavorite = databaseHelper.isFavorite(MovieTitle, "movie");
        }

        if (isFavorite) {
            movFav.setImageResource(R.drawable.baseline_favorite_24);
        } else {
            movFav.setImageResource(R.drawable.baseline_favorite_border_24);
        }

        movFav.setOnClickListener(view -> {
            if (isFavorite) {
                databaseHelper.removeFavorite(MovieTitle, "movie");
                movFav.setImageResource(R.drawable.baseline_favorite_border_24);
                isFavorite = false;
                Toast.makeText(MovieDetail.this, MovieTitle + " removed from favorites", Toast.LENGTH_SHORT).show();
            } else {
                databaseHelper.addFavorite(MovieTitle, "movie",MoviePp,MovieDate,MovieDesc,MovieBp,MovieRate);
                movFav.setImageResource(R.drawable.baseline_favorite_24);
                isFavorite = true;
                Toast.makeText(MovieDetail.this, MovieTitle + " added to favorites", Toast.LENGTH_SHORT).show();
            }
        });

        movBackbt.setOnClickListener(view -> finish());


    }
}