package com.example.android.popularmoviestestcareem.Tasks;

import android.os.AsyncTask;

import com.example.android.popularmoviestestcareem.Models.Movie;
import com.example.android.popularmoviestestcareem.UI.MainActivity;
import com.example.android.popularmoviestestcareem.Utilities.JSONUtils;
import com.example.android.popularmoviestestcareem.Utilities.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class TheMovieAsyncTask extends AsyncTask<URL, Void, String> {
    private List<Movie> movies = MainActivity.movies;

    @Override
    protected String doInBackground(URL... params) {

        URL url = params[0];
        String movieData = null;
        try {
            movieData = NetworkUtils.getResponseFromHttpUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movieData;
    }

    @Override
    protected void onPostExecute(String jsonData) {

        //List<Movie> movies = MainActivity.movieAdapter.getMovies();

        if (jsonData != null) {
            try {

                JSONUtils.parseJSON(jsonData, movies);

                MainActivity.movieAdapter.setMovies(movies);
                MainActivity.movieAdapter.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }}
