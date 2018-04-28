package com.wordpress.ayo218.popularmovie;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Ayo on 4/27/2018.
 */

public class MainFragment extends Fragment {

    // TODO: 4/28/2018 Build Layout
    // TODO: 4/28/2018 Fill with dummy data
    // TODO: 4/28/2018 is scattered gridview available? 
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        
        return view;
    }
}
