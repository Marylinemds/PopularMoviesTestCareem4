package com.example.android.popularmoviestestcareem;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.android.popularmoviestestcareem.Utilities.NetworkUtils;
import com.shawnlin.numberpicker.NumberPicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity implements MovieAdapter.ListItemClickHandler{

    private static final String TAG = MainActivity.class.getSimpleName();

    AlertDialog dialog;
    MovieAdapter movieAdapter;
    RecyclerView mMoviesList;
    List<Movie> movies = new ArrayList<>();

    private int releaseYear;
    private EndlessRecyclerViewScrollListener scrollListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "Activity created");

        //Setting the appBar
        Toolbar toolbar = findViewById(R.id.toolbar);

        if (toolbar != null){
            setSupportActionBar(toolbar);
            toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.white));
        }

        getSupportActionBar().setTitle("CareemTest");

        //Setting the RecyclerView
        mMoviesList =  findViewById(R.id.rv_images);
        GridLayoutManager layoutManager;

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager = new GridLayoutManager(this, 2);
        } else {
            layoutManager = new GridLayoutManager(this, 4);
        }

        mMoviesList.setLayoutManager(layoutManager);

        //Setting the adapter
        movieAdapter = new MovieAdapter(this);
        mMoviesList.setAdapter(movieAdapter);


        //Setting the scrollListener
        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager){
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list

                if (releaseYear == 0) {
                    URL SearchUrl = NetworkUtils.buildUrlFromPage(page + 1);
                    new TheMovieAsyncTask().execute(SearchUrl);
                }else{
                    URL SearchUrl = NetworkUtils.buildUrlFromPageandYear(page + 1, releaseYear);
                    new TheMovieAsyncTask().execute(SearchUrl);
                }
            }
        };

        // Adds the scroll listener to RecyclerView
        mMoviesList.addOnScrollListener(scrollListener);

        //Building the AlertDialog to filter the movies by Date
        buildFilterDialog();

        //Recovering the release year in case of screen rotation
        if (savedInstanceState != null) {
            releaseYear = savedInstanceState.getInt("releaseYear");
        }
        makeTheQuery();

        //Displaying an error message, informing the user if there is no Internet connexion
        FrameLayout rvFrameLayout = findViewById((R.id.rv_frame_layout));
        TextView errorMessage = findViewById(R.id.error);

        if (!networkUp()){
            rvFrameLayout.setVisibility(View.GONE);
            errorMessage.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.action_filter){
            dialog.show();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(Movie movie) {

        Context context = MainActivity.this;
        Class destinationActivity = ChildActivity.class;

        Intent startChildActivityIntent = new Intent(context, destinationActivity);
        startChildActivityIntent.putExtra("MyClass", movie);
        startActivity(startChildActivityIntent);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        super.onSaveInstanceState(savedInstanceState);

        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.

        savedInstanceState.putInt("releaseYear", releaseYear);

    }

    public void buildFilterDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        // Get the layout inflater
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.numberpicker, null);

        final NumberPicker np = view.findViewById(R.id.number_picker);
        builder.setView(view)
                // Add action buttons
                .setPositiveButton("done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.dismiss();
                        movies.clear();

                        releaseYear = np.getValue();
                        makeTheQuery();

                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.dismiss();
                        np.setValue(2018);
                        releaseYear = 0;

                        makeTheQuery();
                    }
                });
        builder.setTitle("Release year");


        np.setFadingEdgeEnabled(true);
        np.setScrollerEnabled(true);


        dialog = builder.create();

    }


    private void makeTheQuery() {
        //After clearing the list of movies, making the general query/filtered query

        movies.clear();

        if (releaseYear == 0) {
            URL SearchUrl = NetworkUtils.buildUrlFromPage(1);
            new TheMovieAsyncTask().execute(SearchUrl);
            Log.d(TAG, "Simple query with year" + releaseYear );
        }else{
            URL SearchUrl = NetworkUtils.buildUrlFromPageandYear(1, releaseYear);
            new TheMovieAsyncTask().execute(SearchUrl);
            Log.d(TAG, "Filtered query with year " + releaseYear);
        }

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

                    JSONObject obj = new JSONObject(jsonData);
                    JSONArray results = obj.getJSONArray("results");

                        //iterate through JSON object and set fields to strings
                        for (int i = 0; i < results.length(); i++) {

                            Movie movie;

                            JSONObject resultsData = results.getJSONObject(i);

                            String originalTitle = resultsData.getString("original_title");
                            String synopsis = resultsData.getString("overview");
                            String userRating = resultsData.getString("vote_average");
                            String releaseDate = resultsData.getString("release_date");
                            String id = resultsData.getString("id");
                            String moviePath = resultsData.getString("poster_path").replace("\\Tasks", "");
                            String picSize = "w185";

                            if (resultsData.getString("poster_path") != "null") {

                                movie = new Movie();
                                movie.setOriginalTitle(originalTitle);
                                movie.setSynopsis(synopsis);
                                movie.setUserRating(userRating);
                                movie.setReleaseDate(releaseDate);
                                movie.setId(id);
                                movie.setMoviePath(moviePath);
                                movie.setPicSize(picSize);

                                movies.add(movie);

                                movieAdapter.setMovies(movies);
                                movieAdapter.notifyDataSetChanged();
                            }
                        }

                            movieAdapter.setMovies(movies);
                            movieAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }}

    private boolean networkUp() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (cm != null) { networkInfo = cm.getActiveNetworkInfo();}
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }
    }
