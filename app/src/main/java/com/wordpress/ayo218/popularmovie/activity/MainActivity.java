package com.wordpress.ayo218.popularmovie.activity;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.wordpress.ayo218.popularmovie.R;
import com.wordpress.ayo218.popularmovie.fragment.MovieFragment;
import com.wordpress.ayo218.popularmovie.fragment.SortDialogFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragment();
    }

    private void initFragment() {
        MovieFragment fragment = new MovieFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.homeFragment, fragment);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.fragment_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_menu:
                showSortDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showSortDialog() {
        DialogFragment sortDialog = new SortDialogFragment();
        sortDialog.show(getSupportFragmentManager(), SortDialogFragment.TAG);
    }
}
