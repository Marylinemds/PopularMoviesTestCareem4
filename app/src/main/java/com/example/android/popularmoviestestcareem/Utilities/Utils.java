package com.example.android.popularmoviestestcareem.Utilities;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.example.android.popularmoviestestcareem.Models.Movie;
import com.example.android.popularmoviestestcareem.R;
import com.example.android.popularmoviestestcareem.Tasks.TheMovieAsyncTask;
import com.example.android.popularmoviestestcareem.UI.MainActivity;
import com.shawnlin.numberpicker.NumberPicker;

import java.net.URL;
import java.util.List;

public class Utils {


    public static void buildFilterDialog(final List<Movie> movies, Context context){

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
                        makeTheQuery(movies, MainActivity.releaseYear);
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.dismiss();
                        np.setValue(2018);
                        MainActivity.releaseYear = 0;

                        makeTheQuery(movies, MainActivity.releaseYear);
                    }
                });
        builder.setTitle("Release year");

        np.setFadingEdgeEnabled(true);
        np.setScrollerEnabled(true);

        MainActivity.dialog = builder.create();
    }


    public static void makeTheQuery(List<Movie> movies, int releaseYear) {
        //After clearing the list of movies, making the general query/filtered query

        movies.clear();

        if (MainActivity.releaseYear == 0) {
            URL SearchUrl = NetworkUtils.buildUrlFromPage(1);
            new TheMovieAsyncTask().execute(SearchUrl);
        }else{
            URL SearchUrl = NetworkUtils.buildUrlFromPageandYear(1, releaseYear);
            new TheMovieAsyncTask().execute(SearchUrl);
        }
    }
}
