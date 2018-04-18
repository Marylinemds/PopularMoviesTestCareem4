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

import com.example.android.popularmoviestestcareem.Receiver.ConnectionReceiver;
import com.example.android.popularmoviestestcareem.TestCareemApplication;
import com.example.android.popularmoviestestcareem.Utilities.NetworkUtils;
import com.example.android.popularmoviestestcareem.Utilities.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements MovieAdapter.ListItemClickHandler ,ConnectionReceiver.ConnectionReceiverListener {

    @BindView(R.id.rv_frame_layout) FrameLayout rvFrameLayout;
    @BindView (R.id.error) TextView errorMessage;
    @BindView (R.id.toolbar) Toolbar toolbar;
    @BindView (R.id.rv_images) RecyclerView mMoviesList;

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String MOVIE_DATA_EXTRA = "movie_data_extra";
    public static MovieAdapter movieAdapter;
    public static AlertDialog dialog;
    public static List<Movie> movies = new ArrayList<>();
    public static int releaseYear;

    GridLayoutManager layoutManager;
    EndlessRecyclerViewScrollListener scrollListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

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
        Utils.makeTheQuery(movies, releaseYear, this);

        checkConnection();
    }



    public void setAppBar(){
        if (toolbar != null){
            setSupportActionBar(toolbar);
            toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.white));
        }

        if (getSupportActionBar() != null){
        getSupportActionBar().setTitle("CareemTest");
        }
    }

    public void setRecyclerView(){

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
                    NetworkUtils.QueryMoviesFromPage(String.valueOf(page+ 1), getApplicationContext()) ;
                    MainActivity.movieAdapter.setMovies(movies);
                    MainActivity.movieAdapter.notifyDataSetChanged();

                }else{
                   NetworkUtils.QueryMoviesFromPageAndYear(String.valueOf(page+ 1), String.valueOf(releaseYear), getApplicationContext()) ;
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

    private void checkConnection() {
        boolean isConnected = ConnectionReceiver.isConnected();
        if(!isConnected) {
            //show a No Internet Alert or Dialog
            rvFrameLayout.setVisibility(View.GONE);
            errorMessage.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

        if (!isConnected){
            rvFrameLayout.setVisibility(View.GONE);
            errorMessage.setVisibility(View.VISIBLE);
        }else{
            rvFrameLayout.setVisibility(View.VISIBLE);
            errorMessage.setVisibility(View.GONE);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        // register connection status listener
        TestCareemApplication.getInstance().setConnectionListener(this);
    }

}
