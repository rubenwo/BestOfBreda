package com.a6.projectgroep.bestofbreda.View.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.a6.projectgroep.bestofbreda.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TermsOfServiceFragment extends DialogFragment {

    public static TermsOfServiceFragment newInstance() {
        TermsOfServiceFragment fragment = new TermsOfServiceFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_terms_of, container, false);
        return v;
    }

}
