package com.example.android.popularmoviestestcareem.Utilities;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.example.android.popularmoviestestcareem.Models.Movie;
import com.example.android.popularmoviestestcareem.R;
import com.example.android.popularmoviestestcareem.UI.MainActivity;
import com.shawnlin.numberpicker.NumberPicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    private final static String RESULTS = "results";
    private final static String ORIGINAL_TITLE = "original_title";
    private final static String OVERVIEW = "overview";
    private final static String VOTE_AVERAGE = "vote_average";
    private final static String RELEASE_DATE = "release_date";
    private final static String ID = "id";
    private final static String POSTER_PATH = "poster_path";
    private final static String PIC_SIZE = "w185";



    public static void buildFilterDialog(final List<Movie> movies, final Context context){

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // Get the layout inflater
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        View view = inflater.inflate(R.layout.numberpicker, null);

        final NumberPicker np = view.findViewById(R.id.number_picker);
        builder.setView(view)
                // Add action buttons
                .setPositiveButton("done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.dismiss();
                        movies.clear();

                        MainActivity.releaseYear = np.getValue();
                        makeTheQuery(movies, MainActivity.releaseYear, context);

                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.dismiss();
                        np.setValue(2018);
                        MainActivity.releaseYear = 0;

                        makeTheQuery(movies, MainActivity.releaseYear, context);
                    }
                });
        builder.setTitle("Release year");

        np.setFadingEdgeEnabled(true);
        np.setScrollerEnabled(true);

        MainActivity.dialog = builder.create();
    }


    public static void makeTheQuery(List<Movie> movies, int releaseYear, Context context) {
        //After clearing the list of movies, making the general query/filtered query

        movies.clear();

        if (MainActivity.releaseYear == 0) {
            NetworkUtils.QueryMoviesFromPage("1",context) ;



        }else{
            NetworkUtils.QueryMoviesFromPageAndYear("1", String.valueOf(releaseYear),context) ;

        }
    }

    public static void cleanAndAddMovies(Movie.Response resultsMovies){
        if (resultsMovies != null) {
            List<Movie> movies =  resultsMovies.getMovies();

            for (int i = 0; i<movies.size(); i++){
                Movie movie = movies.get(i);

                if (movie.getPosterPath() != null){
                    MainActivity.movies.add(movie);
                }
            }
    }
}}
