package com.example.androidgroupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Phone extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        Bundle dataToPass = getIntent().getExtras();
        ArticleDetail dFragment = new ArticleDetail();
        dFragment.setArguments( dataToPass ); //pass data to the the fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentLocation, dFragment)
                .commit();
    }
}
