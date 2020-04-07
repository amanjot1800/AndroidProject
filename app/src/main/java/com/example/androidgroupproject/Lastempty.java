package com.example.androidgroupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Lastempty extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lastempty);
        Bundle dataToPass = getIntent().getExtras();
        lastfrag dFragment = new lastfrag(); //add a DetailFragment
        dFragment.setArguments( dataToPass );//pass it a bundle for information

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment, dFragment) //Add the fragment in FrameLayout
                .commit(); //actually load the fragment.
    }
}
