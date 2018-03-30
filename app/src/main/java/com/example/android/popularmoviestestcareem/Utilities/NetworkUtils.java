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
import java.util.Scanner;


/**
 * These utilities will be used to communicate with the network.
 */
public class NetworkUtils {


    final static String MOVIE_BASE_URL =
            "https://api.themoviedb.org/3/discover/movie";

    final static String APIKey = "93aea0c77bc168d8bbce3918cefefa45";


    public static URL buildUrlFromPage(int page) {
        Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                .appendQueryParameter("api_key", APIKey)
                .appendQueryParameter("sort_by", "release_date.desc")
                .appendQueryParameter("release_date.lte", "2018")
                .appendQueryParameter("page", String.valueOf(page))
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
                .appendQueryParameter("api_key", APIKey)
                .appendQueryParameter("sort_by", "release_date.desc")
                .appendQueryParameter("page", String.valueOf(page))
                .appendQueryParameter("year", String.valueOf(year))
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
}

