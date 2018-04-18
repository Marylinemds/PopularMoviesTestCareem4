package com.example.android.popularmoviestestcareem.Utilities;

import com.example.android.popularmoviestestcareem.Models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class JSONUtils {

    private final static String RESULTS = "results";
    private final static String ORIGINAL_TITLE = "original_title";
    private final static String OVERVIEW = "overview";
    private final static String VOTE_AVERAGE = "vote_average";
    private final static String RELEASE_DATE = "release_date";
    private final static String ID = "id";
    private final static String POSTER_PATH = "poster_path";
    private final static String PIC_SIZE = "w185";

    public static List<Movie> parseJSON(JSONObject jsonData, List<Movie> movies) throws JSONException {

        JSONArray results = jsonData.getJSONArray(RESULTS);

        //iterate through JSON object and set fields to strings
        for (int i = 0; i < results.length(); i++) {

            Movie movie;

            JSONObject resultsData = results.getJSONObject(i);

            String originalTitle = resultsData.getString(ORIGINAL_TITLE);
            String synopsis = resultsData.getString(OVERVIEW);
            String userRating = resultsData.getString(VOTE_AVERAGE);
            String releaseDate = resultsData.getString(RELEASE_DATE);
            String id = resultsData.getString(ID);
            String moviePath = resultsData.getString(POSTER_PATH).replace("\\Tasks", "");

            if (resultsData.getString(POSTER_PATH).equals("null")) {

                movie = new Movie();
                movie.setOriginalTitle(originalTitle);
                movie.setSynopsis(synopsis);
                movie.setUserRating(Double.valueOf(userRating));
                movie.setReleaseDate(releaseDate);
                movie.setId(Long.valueOf(id));
                movie.setPosterPath(moviePath);

                movies.add(movie);
            }
        }
        return movies;
    }
}
