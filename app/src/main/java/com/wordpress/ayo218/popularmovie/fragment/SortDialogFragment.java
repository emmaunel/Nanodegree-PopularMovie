package com.wordpress.ayo218.popularmovie.fragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import com.wordpress.ayo218.popularmovie.R;

public class SortDialogFragment extends DialogFragment{

    public static final String TAG = "SortDialogFragment";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.DialogStyle);
        builder.setTitle(R.string.sort_by_title);
        builder.setNegativeButton(R.string.action_cancel, null);
        builder.setSingleChoiceItems(R.array.pref_sort_by_labels, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        return builder.create();
    }
}
