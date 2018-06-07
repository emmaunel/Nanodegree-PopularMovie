package com.wordpress.ayo218.popularmovie.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import com.wordpress.ayo218.popularmovie.model.Movie;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String TAG = "AppDatabase";
    private static final Object LOCK = new Object();
    private static final String DATABASE_TABLE = "movies";
    private static AppDatabase sInstance;

    // TODO: 6/6/2018 Now make sure that the same movie is not added twice 
    public static AppDatabase getsInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(TAG, "Creating new Database Instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, AppDatabase.DATABASE_TABLE)
                        // TODO: 6/6/2018 Remove this line-
                        .allowMainThreadQueries()
                        .build();
            }
        }
        Log.d(TAG, "getsInstance: Getting the instance");
        return sInstance;
    }

    public abstract MovieDao movieDao();
}
