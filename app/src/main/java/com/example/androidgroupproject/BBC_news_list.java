package com.example.androidgroupproject;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class BBC_news_list extends Fragment {

    private AppCompatActivity parentActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View result =  inflater.inflate(R.layout.fragment_bbc_news_list, container, false);

        Button hideBtn = result.findViewById(R.id.hideButton);
        hideBtn.setOnClickListener( clk -> {
            parentActivity.getSupportFragmentManager().beginTransaction().remove(this).commit();
        });

        return null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        parentActivity = (AppCompatActivity)context;
    }
}

