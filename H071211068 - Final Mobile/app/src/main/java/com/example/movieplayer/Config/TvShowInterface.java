package com.example.movieplayer.Config;

import com.example.movieplayer.Model.TvResponse;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TvShowInterface {
    @GET("/3/tv/{category}")
    Call<TvResponse> getTvShow(
            @Path("category") String category,
            @Query("api_key") String api_key,
            @Query("language") String language,
            @Query("page") int page
    );

}