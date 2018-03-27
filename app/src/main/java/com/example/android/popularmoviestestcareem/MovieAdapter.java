package com.example.android.popularmoviestestcareem;

import android.content.Context;
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

    final private ListItemClickHandler mOnClickHandler;
    int count;


    List<Movie> movies;

    public MovieAdapter(ListItemClickHandler listener) {
        mOnClickHandler = listener;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // crear context + layout inflater + create view del movieitem inflando
        // crear imageview holder y pasar el view que acabo de inflar + return imageview


        Context context = parent.getContext();
        int layoutIdForPoster = R.layout.movieitem;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForPoster, parent, shouldAttachToParentImmediately);
        ImageViewHolder viewHolder = new ImageViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ImageViewHolder viewHolder, int position) {

        Context context = viewHolder.poster.getContext();


        Movie movie = movies.get(position);

        String moviePath = movie.getMoviePath();

        Picasso.with(context).load("http://image.tmdb.org/t/p/" + "w185" + moviePath).into(viewHolder.poster);


        count = movies.size();

    }

    @Override
    public int getItemCount() {
        return movies == null ? 0 : movies.size();

    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView poster;

        public ImageViewHolder(View itemView) {
            super(itemView);

            poster = (ImageView) itemView.findViewById(R.id.movie_poster);

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


