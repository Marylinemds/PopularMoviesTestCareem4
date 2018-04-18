package com.example.android.popularmoviestestcareem.Utilities;

/**
 * Created by Maryline on 3/27/2018.
 */

import android.net.NetworkInfo;
import android.net.ConnectivityManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.android.popularmoviestestcareem.Data.MoviesApi;
import com.example.android.popularmoviestestcareem.Models.Movie;
import com.example.android.popularmoviestestcareem.UI.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * These utilities will be used to communicate with the network.
 */
public class NetworkUtils {


    private final static String APIKey = "93aea0c77bc168d8bbce3918cefefa45";
    private final static String MOVIE_BASE_URL = "https://api.themoviedb.org/";
    private final static String RELEASE_DATE_DESC = "release_date.desc";
    private final static String RELEASE_DATE_MAX = "2018";


    public static void QueryMoviesFromPage(String page, final Context context){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MOVIE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MoviesApi moviesApi = retrofit.create(MoviesApi.class);

        Call<Movie.Response> call = moviesApi.getMoviesFromPage(APIKey, RELEASE_DATE_DESC, RELEASE_DATE_MAX, page);

        call.enqueue(new Callback<Movie.Response> () {
            @Override
            public void onResponse(@NonNull Call<Movie.Response>  call, @NonNull Response<Movie.Response>  response) {
                Movie.Response jsonMovies = response.body();
                Utils.cleanAndAddMovies(jsonMovies);
                    MainActivity.movieAdapter.setMovies(MainActivity.movies);
                    MainActivity.movieAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(@NonNull Call<Movie.Response> call, @NonNull Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    public static void QueryMoviesFromPageAndYear(String page, String year, final Context context){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MOVIE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MoviesApi moviesApi = retrofit.create(MoviesApi.class);

        Call<Movie.Response> call = moviesApi.getMoviesFromPageAndYear(APIKey, RELEASE_DATE_DESC, page,String.valueOf(MainActivity.releaseYear));

        call.enqueue(new Callback<Movie.Response> () {
            @Override
            public void onResponse(@NonNull Call<Movie.Response>  call, @NonNull Response<Movie.Response>  response) {
                Movie.Response jsonMovies = response.body();

                Utils.cleanAndAddMovies(jsonMovies);

                MainActivity.movieAdapter.setMovies(MainActivity.movies);
                MainActivity.movieAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(@NonNull Call<Movie.Response> call, @NonNull Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
}
}

