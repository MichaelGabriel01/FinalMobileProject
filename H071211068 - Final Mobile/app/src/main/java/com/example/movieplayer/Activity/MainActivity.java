package com.example.movieplayer.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.movieplayer.Fragment.FavoriteFragment;
import com.example.movieplayer.Fragment.MovieFragment;
import com.example.movieplayer.R;
import com.example.movieplayer.Fragment.TvShowFragment;

public class MainActivity extends AppCompatActivity {

    ImageView tv,movie,fav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.tvshowbtn);
        movie = findViewById(R.id.moviebtn);
        fav = findViewById(R.id.favbtn);


        FragmentManager fragmentManager = getSupportFragmentManager();
        MovieFragment MovieFragment = new MovieFragment();
        Fragment fragment = fragmentManager.findFragmentByTag(MovieFragment.class.getSimpleName());
        if (!(fragment instanceof MovieFragment)) {
            fragmentManager.beginTransaction().add(R.id.container, MovieFragment,
                    MovieFragment.class.getSimpleName()).commit();
        }

        tv.setOnClickListener( v -> switchFragment(new TvShowFragment()) );
        movie.setOnClickListener( v -> switchFragment(new MovieFragment()));
        fav.setOnClickListener( v -> switchFragment(new FavoriteFragment()));
    }

    private void switchFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if(fragment instanceof MovieFragment){
            transaction.replace(R.id.container, fragment, MovieFragment.class.getSimpleName()).commit();
        } else {
            transaction.replace(R.id.container, fragment, MovieFragment.class.getSimpleName()).addToBackStack(null).commit();
        }

    }
}