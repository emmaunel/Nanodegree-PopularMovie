package com.wordpress.ayo218.popularmovie.database.favorites;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import com.wordpress.ayo218.popularmovie.model.Movie;

@Database(entities = {Movie.class}, version = 1)
public abstract class FavoriteDatabase extends RoomDatabase {

    private static final String TAG = "FavoriteDatabase";
    private static final Object LOCK = new Object();
    private static final String DATABASE_TABLE = "favorites";
    private static FavoriteDatabase sInstance;

    public static FavoriteDatabase getsInstance(Context context) {
        if (sInstance == null){
            synchronized (LOCK){
                Log.i(TAG, "getsInstance: Creating new Database Instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        FavoriteDatabase.class, FavoriteDatabase.DATABASE_TABLE)
                        .allowMainThreadQueries()
                        .build();
            }
        }
        Log.i(TAG, "getsInstance: Getting the instance");

        return sInstance;
    }

    public abstract FavoriteDao favoriteDao();
}
