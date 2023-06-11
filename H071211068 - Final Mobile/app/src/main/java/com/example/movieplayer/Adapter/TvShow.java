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
import com.example.movieplayer.R;
import com.example.movieplayer.Activity.TvShowDetail;
import com.example.movieplayer.Model.TvResult;

import java.util.List;

public class TvShow extends RecyclerView.Adapter<TvShow.TvShowViewHolder> {

    private Context context;
    private List<TvResult> tvList;

    public TvShow(Context context, List<TvResult> tvList) {
        this.context = context;
        this.tvList = tvList;
    }

    public void addTvShows(List<TvResult> newTvShows) {
        int startPos = tvList.size();
        tvList.addAll(newTvShows);
        notifyItemRangeInserted(startPos, newTvShows.size());
    }

    @NonNull
    @Override
    public TvShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.item_tvshow, parent, false );
        TvShow.TvShowViewHolder viewHolder = new TvShow.TvShowViewHolder(view);
        viewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(parent.getContext(), TvShowDetail.class);
                TvResult tvResult= new TvResult();
                tvResult.setName(tvList.get(viewHolder.getAdapterPosition()).getName());
                tvResult.setOverview(tvList.get(viewHolder.getAdapterPosition()).getOverview());
                tvResult.setBackdropPath(tvList.get(viewHolder.getAdapterPosition()).getBackdropPath());
                tvResult.setVoteAverage(tvList.get(viewHolder.getAdapterPosition()).getVoteAverage());
                tvResult.setFirstAirDate(tvList.get(viewHolder.getAdapterPosition()).getFirstAirDate());
                tvResult.setPosterPath(tvList.get(viewHolder.getAdapterPosition()).getPosterPath());
                intent.putExtra(TvShowDetail.EXTRA_TV_SHOW, tvResult);
                parent.getContext().startActivity(intent);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowViewHolder holder, int position) {
        holder.tvTitle.setText(tvList.get(position).getName());
        String releaseDate = tvList.get(position).getFirstAirDate();
        if (releaseDate != null && !releaseDate.isEmpty()) {
            String year = releaseDate.substring(0, 4);
            holder.tvYear.setText(year);
        } else {
            holder.tvYear.setText("");
        }
        Glide.with(context).load("https://image.tmdb.org/t/p/w185" + tvList.get(position).getPosterPath())
                .into(holder.tvPoster)
        ;
    }

    @Override
    public int getItemCount() {
        return tvList.size();
    }

    public class TvShowViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout relativeLayout;
        ImageView tvPoster;
        TextView tvTitle,tvYear;

        public TvShowViewHolder(@NonNull View itemView) {
            super(itemView);

            relativeLayout = itemView.findViewById(R.id.tvshowlayout);
            tvPoster = itemView.findViewById(R.id.tvshowimg);
            tvTitle = itemView.findViewById(R.id.tvshowtitle);
            tvYear = itemView.findViewById(R.id.tvshowyear);
        }


    }


}
