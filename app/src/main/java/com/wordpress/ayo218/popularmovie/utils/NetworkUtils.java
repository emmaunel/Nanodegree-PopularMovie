package com.wordpress.ayo218.popularmovie.utils;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

public class NetworkUtils {
    private static final String TAG = "NetworkUtils";

    public static URL buildUrl(String movieUrl) {
        Uri buildUrl = Uri.parse(movieUrl).buildUpon().build();

        URL url = null;
        try{
            url = new URL(buildUrl.toString());
            Log.e(TAG, "buildUrl: " + url);
        } catch (MalformedURLException e){
            Log.e(TAG, "buildUrl: " + e.getMessage());
        }

        Log.i(TAG, "buildUrl: " + url);

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            Log.i(TAG, "Connected");

            StringBuilder output = new StringBuilder();
            if (in != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(in, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();

                while (line != null) {
                    output.append(line);
                    line = reader.readLine();
                }
            }
            return output.toString();
        } finally {
            urlConnection.disconnect();
        }
    }
}
