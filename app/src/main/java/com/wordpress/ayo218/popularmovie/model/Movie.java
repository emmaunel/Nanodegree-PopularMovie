package com.wordpress.ayo218.popularmovie.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "movie")
public class Movie implements Parcelable{

    @PrimaryKey(autoGenerate = true)
    private int id;
    private long movie_id;
    private String movie_title;
    private String poster_path;
    private String backdrop_path;
    private String overview;
    private String release_date;
    private String vote_average;

    @Ignore
    public Movie(long movie_id, String movie_title, String poster_path,
                 String backdrop_path, String overview, String release_date,
                 String vote_average) {
        this.movie_id = movie_id;
        this.movie_title = movie_title;
        this.poster_path = poster_path;
        this.backdrop_path = backdrop_path;
        this.overview = overview;
        this.release_date = release_date;
        this.vote_average = vote_average;
    }

    public Movie(int id, long movie_id, String movie_title,
                 String poster_path, String backdrop_path, String overview,
                 String release_date, String vote_average) {
        this.id = id;
        this.movie_id = movie_id;
        this.movie_title = movie_title;
        this.poster_path = poster_path;
        this.backdrop_path = backdrop_path;
        this.overview = overview;
        this.release_date = release_date;
        this.vote_average = vote_average;
    }

    private Movie(Parcel in) {
        movie_id = in.readLong();
        movie_title = in.readString();
        poster_path = in.readString();
        backdrop_path = in.readString();
        overview = in.readString();
        release_date = in.readString();
        vote_average = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getMovie_title() {
        return movie_title;
    }

    public long getMovie_id() {
        return movie_id;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getVote_average() {
        return vote_average;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(movie_id);
        parcel.writeString(movie_title);
        parcel.writeString(poster_path);
        parcel.writeString(backdrop_path);
        parcel.writeString(overview);
        parcel.writeString(release_date);
        parcel.writeString(vote_average);
    }
}
