package com.wordpress.ayo218.popularmovie.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Trailers implements Parcelable{
    private String trailer_id;
    private String trailer_key;
    private String trailer_name;

    public Trailers(String trailer_id, String trailer_key, String trailer_name) {
        this.trailer_id = trailer_id;
        this.trailer_key = trailer_key;
        this.trailer_name = trailer_name;
    }

    protected Trailers(Parcel in) {
        trailer_id = in.readString();
        trailer_key = in.readString();
        trailer_name = in.readString();
    }

    public static final Creator<Trailers> CREATOR = new Creator<Trailers>() {
        @Override
        public Trailers createFromParcel(Parcel in) {
            return new Trailers(in);
        }

        @Override
        public Trailers[] newArray(int size) {
            return new Trailers[size];
        }
    };

    public String getTrailer_id() {
        return trailer_id;
    }

    public String getTrailer_key() {
        return trailer_key;
    }

    public String getTrailer_name() {
        return trailer_name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(trailer_id);
        parcel.writeString(trailer_key);
        parcel.writeString(trailer_name);
    }
}
