package com.example.a23b_11345b_l01.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.a23b_11345b_l01.R;
import com.google.android.material.textview.MaterialTextView;

public class MapFragment extends Fragment {

    private MaterialTextView map_LBL_title;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        findViews(view);
        return view;
    }

    private void findViews(View view) {
        map_LBL_title = view.findViewById(R.id.map_LBL_title);
    }

    public void zoomOnRecord(String name){
        map_LBL_title.setText(name);
    }
}