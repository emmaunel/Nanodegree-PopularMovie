package com.wordpress.ayo218.popularmovie.activity;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.wordpress.ayo218.popularmovie.R;
import com.wordpress.ayo218.popularmovie.fragment.FavoritesFragment;
import com.wordpress.ayo218.popularmovie.fragment.MovieFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private int selectedNavigationItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.homeFragment, new MovieFragment()).commit();
            setTitle(getString(R.string.navigation_item_explore));
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        toolbar.setNavigationIcon(R.drawable.ic_menu);
        toolbar.setNavigationOnClickListener(navigationView -> drawerLayout.openDrawer(GravityCompat.START));
        navigationView.setNavigationItemSelectedListener(this);

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setChecked(true);
        switch (item.getItemId()) {
            case R.id.drawer_item_explore:
                if (selectedNavigationItem != 0) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.homeFragment, new MovieFragment()).commit();
                    selectedNavigationItem = 0;
                }
                drawerLayout.closeDrawers();
                updateTitle();
                return true;
            case R.id.drawer_item_favorites:
                if (selectedNavigationItem != 1) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.homeFragment, new FavoritesFragment())
                            .commit();
                    selectedNavigationItem = 1;
                }
                drawerLayout.closeDrawers();
                updateTitle();
                return true;
            default:
                return false;
        }
    }


    private void updateTitle() {
        if (selectedNavigationItem == 0) {
            setTitle(getString(R.string.navigation_item_explore));
        } else if (selectedNavigationItem == 1) {
            setTitle(getString(R.string.navigation_item_favorites));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // TODO: if fragment is in favorites return to explore fragment
    }
}
