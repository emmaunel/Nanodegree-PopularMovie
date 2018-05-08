package com.wordpress.ayo218.popularmovie.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wordpress.ayo218.popularmovie.R;
import com.wordpress.ayo218.popularmovie.fragment.MovieFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragment();
    }

    private void initFragment() {
        MovieFragment fragment = new MovieFragment();
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction =manager.beginTransaction();
        transaction.replace(R.id.homeFragment, fragment);
        transaction.commit();

    }
}
