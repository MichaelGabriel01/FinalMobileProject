package com.example.movieplayer.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.movieplayer.Adapter.Movie;
import com.example.movieplayer.Adapter.TvShow;
import com.example.movieplayer.Config.ApiClient;
import com.example.movieplayer.Config.TvShowInterface;
import com.example.movieplayer.Model.MovieResult;
import com.example.movieplayer.Model.TvResponse;
import com.example.movieplayer.Model.TvResult;
import com.example.movieplayer.R;


import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvShowFragment extends Fragment {
    int PAGE = 1;
    private boolean isLoading = false;
    private TvShow adapter;
    Context context;
    RecyclerView rv;
    String API_KEY = "85181ab6ea2a4b85eea4a8f73a95f729";
    String CATEGORY = "on_the_air";
    String LANGUAGE = "en-US";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tvshow, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rv = view.findViewById(R.id.tvshowRV);
        rv.setHasFixedSize(true);

        context = requireContext();

        rv.setLayoutManager(new GridLayoutManager(context, 2));
        callRetrofit();
    }

    private void callRetrofit() {
        TvShowInterface tvInterface = ApiClient.getClient().create(TvShowInterface.class);
        Call<TvResponse> call;
        call = tvInterface.getTvShow(CATEGORY, API_KEY, LANGUAGE, PAGE);
        call.enqueue(new Callback<TvResponse>() {
            @Override
            public void onResponse(Call<TvResponse> call, Response<TvResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<TvResult> tvadapter = response.body().getResults();
                    adapter = new TvShow(getContext(), tvadapter);
                    rv.setAdapter(adapter);
                } else {
                    // Handle unsuccessful response
                }
            }

            @Override
            public void onFailure(Call<TvResponse> call, Throwable t) {

            }
            // ...
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
        TvShowInterface tvInterface = ApiClient.getClient().create(TvShowInterface.class);
        Call<TvResponse> call = tvInterface.getTvShow(CATEGORY, API_KEY, LANGUAGE, PAGE);
        call.enqueue(new Callback<TvResponse>() {
            @Override
            public void onResponse(Call<TvResponse> call, Response<TvResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<TvResult> newList = response.body().getResults();
                    adapter.addTvShows(newList);
                    isLoading = false;
                } else {
                    // Handle unsuccessful response
                }
            }
            @Override
            public void onFailure(Call<TvResponse> call, Throwable t) {

            }
});}
}
