package com.example.androidgroupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

/**
 * This Activity is just used to navigate to the fragment in the phone
 */
public class EmptyActivity extends AppCompatActivity {

    /**
     * Creates and navigates to the fragment where all the details of the image are shown
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);

        ImageOfDayFragment dFragment = new ImageOfDayFragment();
        dFragment.setArguments(getIntent().getExtras());

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.NasaEmptyActivity, dFragment)
                .commit();

    }
}
