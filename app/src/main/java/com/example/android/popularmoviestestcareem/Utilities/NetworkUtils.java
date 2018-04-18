package com.example.android.popularmoviestestcareem.Utilities;

/**
 * Created by Maryline on 3/27/2018.
 */


import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;
import android.net.NetworkInfo;
import android.net.ConnectivityManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.android.popularmoviestestcareem.Data.MoviesApi;
import com.example.android.popularmoviestestcareem.Models.Movie;
import com.example.android.popularmoviestestcareem.UI.MainActivity;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

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
    private final static String API_KEY = "api_key";
    private final static String SORT_BY = "sort_by";
    private final static String RELEASE_DATE_DESC = "release_date.desc";
    private final static String RELEASE_DATE_LTE = "release_date.lte";
    private final static String RELEASE_DATE_MAX = "2018";
    private final static String PAGE = "page";
    private final static String YEAR = "year";


    public static URL buildUrlFromPage(int page) {
        Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                .appendQueryParameter(API_KEY, APIKey)
                .appendQueryParameter(SORT_BY, RELEASE_DATE_DESC)
                .appendQueryParameter(RELEASE_DATE_LTE, RELEASE_DATE_MAX)
                .appendQueryParameter(PAGE, String.valueOf(page))
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        System.out.println(url);
        return url;
    }

    public static URL buildUrlFromPageandYear(int page, int year) {
        Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                .appendQueryParameter(API_KEY, APIKey)
                .appendQueryParameter(SORT_BY, RELEASE_DATE_DESC)
                .appendQueryParameter(PAGE, String.valueOf(page))
                .appendQueryParameter(YEAR, String.valueOf(year))
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        System.out.println(url);
        return url;
    }


    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public static boolean networkUp(Context context) {
        android.net.ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (cm != null) { networkInfo = cm.getActiveNetworkInfo();}
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }


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
            public void onFailure(Call<Movie.Response> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
}
}

