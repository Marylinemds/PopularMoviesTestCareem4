package com.example.android.popularmoviestestcareem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.squareup.picasso.Picasso;


/**
 * Created by Maryline on 3/27/2018.
 */

public class ChildActivity extends AppCompatActivity{

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);

        Log.d(TAG, "Activity created");

        //Setting the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);

        if (toolbar != null){
            setSupportActionBar(toolbar);

            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {ChildActivity.super.onBackPressed(); } });

            toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.white));
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("CareemTest");

        //Identifying all the items
        ImageView movieDisplay =  findViewById(R.id.display_movie);
        TextView originalTitle_tv = findViewById(R.id.movie_title);
        TextView originalTitle_tv_2 =  findViewById(R.id.movie_title_2);
        TextView releaseDate_tv = findViewById(R.id.release_date);
        TextView voteAverage_tv =  findViewById(R.id.vote_average_2);
        RatingBar voteAverage_rb =  findViewById(R.id.vote_average);
        TextView sypnosis_tv =  findViewById(R.id.sypnosis);

        //Getting the information from the item clicked on MainActivity, and assign it to the layout
        Intent startChildActivityIntent = getIntent();

        if (startChildActivityIntent != null) {
            if (startChildActivityIntent.hasExtra("MyClass")) {
                Movie movie = startChildActivityIntent.getParcelableExtra("MyClass");

                String moviePath = movie.getMoviePath();
                String voteAverage = movie.getUserRating();
                String voteAverageText = "(" + movie.getUserRating()+ "/10" + ")";

                originalTitle_tv.setText(movie.getOriginalTitle());
                originalTitle_tv_2.setText(movie.getOriginalTitle());
                releaseDate_tv.setText(movie.getReleaseDate().substring(0, 4));
                voteAverage_rb.setRating((Float.valueOf(voteAverage)) / 2);
                voteAverage_tv.setText(voteAverageText);
                sypnosis_tv.setText(movie.getSynopsis());

                Picasso.with(this).load("http://image.tmdb.org/t/p/" + "w185" + moviePath).into(movieDisplay);
            }
        }
    }
}