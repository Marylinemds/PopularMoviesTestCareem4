package com.example.android.popularmoviestestcareem;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.squareup.picasso.Picasso;


/**
 * Created by Maryline on 3/27/2018.
 */


public class ChildActivity extends AppCompatActivity{

    ImageView movieDisplay;
    TextView originalTitle_tv;
    TextView originalTitle_tv_2;
    TextView releaseDate_tv;
    TextView voteAverage_tv;
    TextView sypnosis_tv;
    Movie movie;
    RatingBar voteAverage_rb;

    String voteAverage;

    public String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);

        Toolbar toolbar = findViewById(R.id.toolbar);

        if (toolbar != null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("CareemTest");
        }


        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {ChildActivity.super.onBackPressed(); } });

        toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.white));

        movieDisplay =  findViewById(R.id.display_movie);
        originalTitle_tv = findViewById(R.id.movie_title);
        originalTitle_tv_2 =  findViewById(R.id.movie_title_2);
        releaseDate_tv = findViewById(R.id.release_date);
        voteAverage_tv =  findViewById(R.id.vote_average_2);
        voteAverage_rb =  findViewById(R.id.vote_average);
        sypnosis_tv =  findViewById(R.id.sypnosis);


        Intent startChildActivityIntent = getIntent();

        if (startChildActivityIntent != null) {
            if (startChildActivityIntent.hasExtra("MyClass")) {
                movie = startChildActivityIntent.getParcelableExtra("MyClass");

                String moviePath = movie.getMoviePath();
                id = movie.getId();
                System.out.println("blabla " + id);


                originalTitle_tv.setText(movie.getOriginalTitle());
                originalTitle_tv_2.setText(movie.getOriginalTitle());
                releaseDate_tv.setText(movie.getReleaseDate().substring(0, 4));
                voteAverage = movie.getUserRating();
                voteAverage_rb.setRating((Float.valueOf(voteAverage)) / 2);
                voteAverage_tv.setText("(" + movie.getUserRating() + "/10)");
                sypnosis_tv.setText(movie.getSynopsis());


                Picasso.with(this).load("http://image.tmdb.org/t/p/" + "w185" + moviePath).into(movieDisplay);
            }
        }
    }

}