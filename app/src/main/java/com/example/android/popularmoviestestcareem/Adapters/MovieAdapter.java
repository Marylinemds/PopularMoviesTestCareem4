package com.example.android.popularmoviestestcareem.Adapters;

import com.example.android.popularmoviestestcareem.Models.Movie;
import com.example.android.popularmoviestestcareem.R;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Maryline on 3/27/2018.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ImageViewHolder> {


    final static public String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";
    final static public String PIC_SIZE = "w185";
    final private ListItemClickHandler mOnClickHandler;


    private List<Movie> movies;


    public MovieAdapter(ListItemClickHandler listener) {
        mOnClickHandler = listener;
    }


    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {



        Context context = parent.getContext();
        int layoutIdForPoster = R.layout.movieitem;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForPoster, parent, false);


        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder viewHolder, int position) {

        Context context = viewHolder.poster.getContext();
        Movie movie = movies.get(position);

        String moviePath = movie.getPosterPath();

        Picasso.with(context).load( IMAGE_BASE_URL + PIC_SIZE + moviePath).into(viewHolder.poster);

    }

    @Override
    public int getItemCount() {
        return movies == null ? 0 : movies.size();

    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView poster;

        private ImageViewHolder(View itemView) {
            super(itemView);

            poster = itemView.findViewById(R.id.movie_poster);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Movie movie = movies.get(position);
            mOnClickHandler.onClick(movie);
        }
    }

    public interface ListItemClickHandler{
        void onClick(Movie movie);
    }

}


