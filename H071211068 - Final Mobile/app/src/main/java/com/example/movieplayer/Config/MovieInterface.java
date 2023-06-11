package com.example.movieplayer.Config;

import com.example.movieplayer.Adapter.Movie;

import com.example.movieplayer.Model.MovieResponse;
import com.example.movieplayer.R;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieInterface {
    @GET("/3/movie/{category}")
    Call<MovieResponse> getMovie(
            @Path("category") String category,
            @Query("api_key") String api_key,
            @Query("language") String language,
            @Query("page") int page
    );

}