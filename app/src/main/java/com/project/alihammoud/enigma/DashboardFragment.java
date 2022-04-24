package com.project.alihammoud.enigma;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class DashboardFragment extends Fragment {


BottomNavigationView bottomNavigationView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);


        bottomNavigationView = getActivity().findViewById(R.id.nav_bar);
        bottomNavigationView.setVisibility(View.VISIBLE);



        return view;
    }
}