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
            Intent nasa = new Intent(this, ImageryDatabase.class);
            startActivity(nasa);
        });

        Button nasaImageOfDay = findViewById(R.id.nasaImageofDay);
        nasaImageOfDay.setOnClickListener( click -> {
            Intent go = new Intent(MainActivity.this, ImageOfTheDay.class);
            startActivity(go);
        });


        Button goToBBCnewsReader = findViewById(R.id.goToBBCnewsReader);
        goToBBCnewsReader.setOnClickListener(click ->{
            Intent goTobbcNewsReader = new Intent(MainActivity.this, BBC_NewsReader.class);
            startActivity(goTobbcNewsReader);
        });

        Button guardian = findViewById(R.id.guardianMain);
        guardian.setOnClickListener(click ->{
            Intent guardianGo = new Intent(MainActivity.this, Guardian.class);
            startActivity(guardianGo);
        });

    }
}
