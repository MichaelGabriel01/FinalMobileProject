package com.example.movieplayer.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.movieplayer.R;
import com.example.movieplayer.Db.DatabaseHelper;
import com.example.movieplayer.Model.MovieResult;
import com.example.movieplayer.Model.TvResult;

import java.util.Objects;

public class TvShowDetail extends AppCompatActivity {

    public static final String EXTRA_TV_SHOW = "extra_tv_show";
    String televTitle, televDate, televDesc, televPp, televBp;
    Double televRate;
    ImageView tvLogo, tvProf, tvBp, tvFav, tvBackbt;
    TextView tvtitle, tvDate, tvrate, tvdesc;
    TvResult tvresult;

    DatabaseHelper databaseHelper;
    boolean isFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvshow_detail);

        databaseHelper = new DatabaseHelper(this);

        tvLogo = findViewById(R.id.tvshowlogo);
        tvProf = findViewById(R.id.tvshowbg);
        tvBp = findViewById(R.id.tvshowpost);
        tvFav = findViewById(R.id.tvshowfavbtn);
        tvBackbt = findViewById(R.id.tvshowbackbtn);

        tvtitle = findViewById(R.id.tvshowtitle);
        tvDate = findViewById(R.id.tvshowdate);
        tvrate = findViewById(R.id.tvshowrating);
        tvdesc = findViewById(R.id.tvshowdesc);

        tvresult = getIntent().getParcelableExtra(EXTRA_TV_SHOW);
        televTitle = tvresult.getName();
        televDesc = tvresult.getOverview();
        televDate = tvresult.getFirstAirDate();
        televRate = tvresult.getVoteAverage();
        televPp = tvresult.getPosterPath();
        televBp = tvresult.getBackdropPath();

        if (televTitle != null) {
            tvtitle.setText(televTitle);
        } else {
            tvtitle.setText("N/A");
        }

        if (televDate != null) {
            tvDate.setText(televDate);
        } else {
            tvDate.setText("N/A");
        }

        if (televRate != null) {
            tvrate.setText(String.valueOf(televRate));
        } else {
            tvrate.setText("N/A");
        }

        if (televDesc != null) {
            tvdesc.setText(televDesc);
        } else {
            tvdesc.setText("N/A");
        }

        Glide.with(getApplicationContext())
                .load("https://image.tmdb.org/t/p/w185" + televPp)
                .into(tvProf);
        Glide.with(getApplicationContext())
                .load("https://image.tmdb.org/t/p/original" + televBp)
                .into(tvBp);

        if (televTitle != null) {
            isFavorite = databaseHelper.isFavorite(televTitle, "tv_show");
        }

        if (isFavorite) {
            tvFav.setImageResource(R.drawable.baseline_favorite_24);
        } else {
            tvFav.setImageResource(R.drawable.baseline_favorite_border_24);
        }

        tvFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFavorite) {
                    databaseHelper.removeFavorite(televTitle, "tv_show");
                    tvFav.setImageResource(R.drawable.baseline_favorite_border_24);
                    isFavorite = false;
                    Toast.makeText(TvShowDetail.this, televTitle + " removed from favorites", Toast.LENGTH_SHORT).show();
                } else {
                    databaseHelper.addFavorite(televTitle, "tv_show",televPp,televDate,televDesc,televBp,televRate);
                    tvFav.setImageResource(R.drawable.baseline_favorite_24);
                    isFavorite = true;
                    Toast.makeText(TvShowDetail.this, televTitle + " added to favorites", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvBackbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
