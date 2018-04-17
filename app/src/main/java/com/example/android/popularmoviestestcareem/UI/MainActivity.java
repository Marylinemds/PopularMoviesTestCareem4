package com.example.android.popularmoviestestcareem.UI;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.example.android.popularmoviestestcareem.Adapters.MovieAdapter;
import com.example.android.popularmoviestestcareem.Listeners.EndlessRecyclerViewScrollListener;
import com.example.android.popularmoviestestcareem.Models.Movie;
import com.example.android.popularmoviestestcareem.R;

import com.example.android.popularmoviestestcareem.Tasks.TheMovieAsyncTask;
import com.example.android.popularmoviestestcareem.Utilities.NetworkUtils;
import com.example.android.popularmoviestestcareem.Utilities.Utils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity implements MovieAdapter.ListItemClickHandler{

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String MOVIE_DATA_EXTRA = "movie_data_extra";
    public static MovieAdapter movieAdapter;

    public static AlertDialog dialog;
    RecyclerView mMoviesList;
    public static List<Movie> movies = new ArrayList<>();
    GridLayoutManager layoutManager;

    public static int releaseYear;
    public EndlessRecyclerViewScrollListener scrollListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "Activity created");

        setAppBar();
        setRecyclerView();
        movieAdapter = new MovieAdapter(this);
        mMoviesList.setAdapter(movieAdapter);
        setScrollingListener();
        mMoviesList.addOnScrollListener(scrollListener);
        Utils.buildFilterDialog(movies, this);

        //Recovering the release year in case of screen rotation
        if (savedInstanceState != null) {
            releaseYear = savedInstanceState.getInt("releaseYear");
        }
        Utils.makeTheQuery(movies, releaseYear);

        //Displaying an error message, informing the user if there is no Internet connexion
        FrameLayout rvFrameLayout = findViewById((R.id.rv_frame_layout));
        TextView errorMessage = findViewById(R.id.error);

        if (!NetworkUtils.networkUp(this)){
            rvFrameLayout.setVisibility(View.GONE);
            errorMessage.setVisibility(View.VISIBLE);
        }
    }

    public void setAppBar(){
        Toolbar toolbar = findViewById(R.id.toolbar);

        if (toolbar != null){
            setSupportActionBar(toolbar);
            toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.white));
        }

        if (getSupportActionBar() != null){
        getSupportActionBar().setTitle("CareemTest");
        }
    }

    public void setRecyclerView(){
        mMoviesList =  findViewById(R.id.rv_images);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager = new GridLayoutManager(this, 2);
        } else {
            layoutManager = new GridLayoutManager(this, 4);
        }

        mMoviesList.setLayoutManager(layoutManager);
    }

    public void setScrollingListener(){ scrollListener = new EndlessRecyclerViewScrollListener(layoutManager){
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
        Class destinationActivity = com.example.android.popularmoviestestcareem.UI.ChildActivity.class;

        Intent startChildActivityIntent = new Intent(context, destinationActivity);
        startChildActivityIntent.putExtra(MOVIE_DATA_EXTRA, movie);
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

    }