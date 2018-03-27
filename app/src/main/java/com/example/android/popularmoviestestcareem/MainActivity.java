package com.example.android.popularmoviestestcareem;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.android.popularmoviestestcareem.Utilities.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieAdapter.ListItemClickHandler{

    private static final String TAG = MainActivity.class.getSimpleName();

    MovieAdapter movieAdapter;
    RecyclerView mMoviesList;
    public int mNumberItems;


    List<Movie> movies = new ArrayList<>();


    private EndlessRecyclerViewScrollListener scrollListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mMoviesList = (RecyclerView) findViewById(R.id.rv_images);
        GridLayoutManager layoutManager;

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager = new GridLayoutManager(this, 2);
        } else {
            layoutManager = new GridLayoutManager(this, 4);
        }

        mMoviesList.setLayoutManager(layoutManager);

        movieAdapter = new MovieAdapter(this);

        mMoviesList.setAdapter(movieAdapter);

        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager){
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list


                URL SearchUrl = NetworkUtils.buildUrlFromPage(page + 1);
                new TheMovieAsyncTask().execute(SearchUrl);

            }

        };

        // Adds the scroll listener to RecyclerView
        mMoviesList.addOnScrollListener(scrollListener);



        makeTheQuery();

    }


    @Override
    public void onClick(Movie movie) {

        Context context = MainActivity.this;

        Class destinationActivity = ChildActivity.class;

        Intent startChildActivityIntent = new Intent(context, destinationActivity);
        startChildActivityIntent.putExtra("MyClass", movie);
        startActivity(startChildActivityIntent);
    }



    private void makeTheQuery() {
        movies.clear();

        URL SearchUrl = NetworkUtils.buildUrlFromPage(1);


        new TheMovieAsyncTask().execute(SearchUrl);


    }


    public class TheMovieAsyncTask extends AsyncTask<URL, Void, String> {


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


            System.out.println("JSON " + jsonData);
            if (jsonData != null) {
                try {

                    //System.out.println("http://image.tmdb.org/t/p/" + picSize + moviePath);
                    JSONObject obj = new JSONObject(jsonData);
                    JSONArray results = obj.getJSONArray("items");



                        //iterate through JSON object and set fields to strings
                        for (int i = 0; i < results.length(); i++) {

                            Movie movie;

                            JSONObject resultsData = results.getJSONObject(i);

                            String originalTitle = resultsData.getString("original_title");
                            String synopsis = resultsData.getString("overview");
                            String userRating = resultsData.getString("vote_average");
                            String releaseDate = resultsData.getString("release_date");
                            String popularity = resultsData.getString("popularity");
                            String id = resultsData.getString("id");
                            String moviePath = resultsData.getString("poster_path").replace("\\Tasks", "");
                            String picSize = "w185";

                            movie = new Movie();
                            movie.setMoviePath(moviePath);
                            movie.setOriginalTitle(originalTitle);
                            movie.setPicSize(picSize);
                            movie.setReleaseDate(releaseDate);
                            movie.setSynopsis(synopsis);
                            movie.setUserRating(userRating);
                            movie.setPopularity(popularity);
                            movie.setId(id);

                            movies.add(movie);

                            mNumberItems = results.length();

                            movieAdapter.setMovies(movies);
                            movieAdapter.notifyDataSetChanged();
                        }



                            movieAdapter.setMovies(movies);
                            movieAdapter.notifyDataSetChanged();



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }}






    }


