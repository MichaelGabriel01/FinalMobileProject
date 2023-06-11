package com.example.movieplayer.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.movieplayer.Config.TvShowInterface;
import com.example.movieplayer.Model.MovieResult;
import com.example.movieplayer.Model.TvResponse;
import com.example.movieplayer.R;
import com.example.movieplayer.Adapter.Movie;
import com.example.movieplayer.Model.MovieResponse;
import com.example.movieplayer.Config.ApiClient;
import com.example.movieplayer.Config.MovieInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MovieFragment extends Fragment {
    int PAGE = 1;
    private boolean isLoading = false;
    private Movie adapter;
    Context context;
    RecyclerView rv;
    String API_KEY = "85181ab6ea2a4b85eea4a8f73a95f729";
    String CATEGORY = "popular";
    String LANGUAGE = "en-US";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rv = view.findViewById(R.id.MovieRV);
        rv.setHasFixedSize(true);

        context = requireContext();

        rv.setLayoutManager(new GridLayoutManager(context, 2));
        CallRetrofit();
    }

    private void CallRetrofit() {
        MovieInterface movieInterface = ApiClient.getClient().create(MovieInterface.class);
        Call<MovieResponse> call = movieInterface.getMovie(CATEGORY, API_KEY, LANGUAGE, PAGE);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<MovieResult> movieResult = response.body().getResults();
                    adapter = new Movie(getContext(), movieResult);
                    rv.setAdapter(adapter);
                } else {
                    // Handle the case where the response body is null or the request was not successful
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                // Handle failure
            }
        });

        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                if (!isLoading && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount) {
                    PAGE++;
                    isLoading = true;
                    loadMoreData();
                }
            }
        });
    }

    private void loadMoreData() {
        MovieInterface movieInterface = ApiClient.getClient().create(MovieInterface.class);
        Call<MovieResponse> call = movieInterface.getMovie(CATEGORY, API_KEY, LANGUAGE, PAGE);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<MovieResult> newList = response.body().getResults();
                    adapter.addMovies(newList);
                    isLoading = false;
                } else {
                    // Handle the case where the response body is null or the request was not successful
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                // Handle failure
            }
        });
    }
}
