package com.example.android.popularmoviestestcareem;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Maryline on 3/27/2018.
 */

public class Movie implements Parcelable {

    String originalTitle;
    String moviePath;
    String synopsis; //called overview in the api
    String userRating; //called vote_average in the api
    String releaseDate;
    String picSize;
    String popularity;
    String id;

    public Movie(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }


    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getMoviePath() {
        return moviePath;
    }

    public void setMoviePath(String moviePath) {
        this.moviePath = moviePath;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getUserRating() {
        return userRating;
    }

    public void setUserRating(String userRating) {
        this.userRating = userRating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPicSize() {
        return picSize;
    }

    public void setPicSize(String picSize) {
        this.picSize = picSize;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(this.originalTitle);
        dest.writeString(this.moviePath);
        dest.writeString(this.synopsis);
        dest.writeString(this.userRating);
        dest.writeString(this.releaseDate);
        dest.writeString(this.picSize);
        dest.writeString(this.id);

    }

    protected Movie(Parcel in) {
        this.originalTitle = in.readString();
        this.moviePath = in.readString();
        this.synopsis = in.readString();
        this.userRating = in.readString();
        this.releaseDate = in.readString();
        this.picSize = in.readString();
        this.id = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}

