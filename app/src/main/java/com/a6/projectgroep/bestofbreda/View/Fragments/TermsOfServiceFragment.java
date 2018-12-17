package com.a6.projectgroep.bestofbreda.View.Fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.a6.projectgroep.bestofbreda.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TermsOfServiceFragment extends DialogFragment {

    private TextView tosText;

    public static TermsOfServiceFragment newInstance() {
        TermsOfServiceFragment fragment = new TermsOfServiceFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_terms_of, container, false);
        tosText = v.findViewById(R.id.termsofactivity_text);
        tosText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.vvvhartvannoordholland.nl/media/hartvannoordholland/org/documents/algemene%20voorw_vvv_versie041209_%20engels.pdf"));
                startActivity(browserIntent);
            }
        });

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }
}
