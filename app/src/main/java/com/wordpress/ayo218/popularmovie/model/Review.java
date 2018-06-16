package com.wordpress.ayo218.popularmovie.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Review implements Parcelable{
    private String author;
    private String content;
    private String reivew_url;

    public Review(String author, String content, String reivew_url) {
        this.author = author;
        this.content = content;
        this.reivew_url = reivew_url;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getReivew_url() {
        return reivew_url;
    }

    protected Review(Parcel in) {
        author = in.readString();
        content = in.readString();
        reivew_url = in.readString();
    }

    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(author);
        parcel.writeString(content);
        parcel.writeString(reivew_url);
    }
}
