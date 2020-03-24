package com.example.androidgroupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button ns = findViewById(R.id.nasa);
        ns.setOnClickListener(click ->{
            Intent nasa = new Intent(this, Guardian.class);
            startActivity(nasa);
        });

        Button nasaImageOfDay = findViewById(R.id.nasaImageofDay);
        nasaImageOfDay.setOnClickListener( click -> {
            Intent go = new Intent(MainActivity.this, ImageOfTheDay.class);
            startActivity(go);
        });

    }
}
