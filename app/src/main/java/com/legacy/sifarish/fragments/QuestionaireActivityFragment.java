package com.legacy.sifarish.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.legacy.sifarish.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class QuestionaireActivityFragment extends Fragment {

    public QuestionaireActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_questionaire, container, false);
    }
}
