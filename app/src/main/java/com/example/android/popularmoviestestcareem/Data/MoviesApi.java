package com.example.android.popularmoviestestcareem.Data;
import com.example.android.popularmoviestestcareem.Models.Movie;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoviesApi {


    String API_KEY = "api_key";
    String SORT_BY = "sort_by";
    String RELEASE_DATE_LTE = "release_date.lte";
    String PAGE = "page";
    String YEAR = "year";

    @GET("3/discover/movie")
    Call<Movie.Response> getMoviesFromPage(
            @Query(API_KEY) String APIKey,
            @Query(SORT_BY) String sort,
            @Query(RELEASE_DATE_LTE) String dateMax,
            @Query(PAGE) String page
    );

    @GET("3/discover/movie")
    Call<Movie.Response> getMoviesFromPageAndYear(
            @Query(API_KEY) String APIKey,
            @Query(SORT_BY) String sort,
            @Query(PAGE) String page,
            @Query(YEAR) String year
    );


}



