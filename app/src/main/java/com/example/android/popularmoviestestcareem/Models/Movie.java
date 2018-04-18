package com.example.android.popularmoviestestcareem.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maryline on 3/27/2018.
 */

public class Movie implements Parcelable {

    @SerializedName("vote_count")
    long voteCount;

    long id;
    boolean video;

    @SerializedName("vote_average")
    double userRating;

    @SerializedName("title")
    String title;

    double popularity;

    @SerializedName("poster_path")
    String posterPath;

    @SerializedName("original_language")
    String originalLanguage;

    @SerializedName("original_title")
    String originalTitle;

    @SerializedName("genre_ids")
    ArrayList<Integer> genres;

    @SerializedName("backdrop_path")
    String backdropPath;

    boolean adult;

    @SerializedName("overview")
    String synopsis;


    @SerializedName("release_date")
    String releaseDate;


    public long getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public double getUserRating() {
        return userRating;
    }

    public void setUserRating(double userRating) {
        this.userRating = userRating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public ArrayList<Integer> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<Integer> genres) {
        this.genres = genres;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Movie(){

    }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeList(this.genres);
        dest.writeString(this.synopsis);
        dest.writeString(this.releaseDate);
        dest.writeString(this.posterPath);
        dest.writeString(this.backdropPath);
        dest.writeDouble(this.popularity);
        dest.writeString(this.title);
        dest.writeDouble(this.userRating);
        dest.writeLong(this.voteCount);
        dest.writeByte(adult ? (byte) 1 : (byte) 0);
    }

    protected Movie(Parcel in) {
        this.id = in.readLong();
        this.genres = new ArrayList<Integer>();
        in.readList(this.genres, List.class.getClassLoader());
        this.synopsis = in.readString();
        this.releaseDate = in.readString();
        this.posterPath = in.readString();
        this.backdropPath = in.readString();
        this.popularity = in.readDouble();
        this.title = in.readString();
        this.userRating = in.readDouble();
        this.voteCount = in.readLong();
        this.adult = in.readByte() != 0;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        public Movie createFromParcel(Parcel source) {return new Movie(source);}

        public Movie[] newArray(int size) {return new Movie[size];}
    };

    public Movie(long voteCount, long id, boolean video, double userRating, String title, double popularity, String posterPath, String originalLanguage, String originalTitle, ArrayList<Integer> genres, String backdropPath, boolean adult, String synopsis, String releaseDate) {
        this.voteCount = voteCount;
        this.id = id;
        this.video = video;
        this.userRating = userRating;
        this.title = title;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.originalLanguage = originalLanguage;
        this.originalTitle = originalTitle;
        this.genres = genres;
        this.backdropPath = backdropPath;
        this.adult = adult;
        this.synopsis = synopsis;
        this.releaseDate = releaseDate;
    }

    public static final class Response {

        @Expose
        public int page;

        @Expose @SerializedName("results")
        public List<Movie> movies;

        public List<Movie> getMovies() {
            return movies;
        }
    }
}

