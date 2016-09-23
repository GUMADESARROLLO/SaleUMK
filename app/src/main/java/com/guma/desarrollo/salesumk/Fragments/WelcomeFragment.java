package com.guma.desarrollo.salesumk.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.guma.desarrollo.salesumk.Activity.MainActivity;
import com.guma.desarrollo.salesumk.R;


public class WelcomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_welcome, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("SALES [UMK]");
        return view;
    }

}
