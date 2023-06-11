package com.example.movieplayer.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.movieplayer.R;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movie = findViewById(R.id.moviebtn);
        tvshow = findViewById(R.id.tvshowbtn);
        fav = findViewById(R.id.favbtn);
}