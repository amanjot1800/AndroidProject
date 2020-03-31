package com.example.androidgroupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.androidgroupproject.R;


public class ImageryDatabase extends AppCompatActivity {
    EditText latitude;
    EditText longitude;

    AsyncTask<String, Integer, String> nasaImg;
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagery_database);
        androidx.appcompat.widget.Toolbar tBar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);

        //This loads the toolbar, which calls onCreateOptionsMenu below:
        setSupportActionBar(tBar);
        latitude = findViewById(R.id.lati);
        longitude = findViewById(R.id.loni);


        Button btn = findViewById(R.id.subm);
        btn.setOnClickListener(click -> {
            if(!latitude.getText().toString().isEmpty() && !longitude.getText().toString().isEmpty()) {
                Intent gotoMain = new Intent(this, ImageryNasaActivity.class);
                gotoMain.putExtra("shubham", Double.parseDouble(latitude.getText().toString()));
                gotoMain.putExtra("sharma", Double.parseDouble(longitude.getText().toString()));
                startActivity(gotoMain);
            }else
            {
                Toast.makeText(this,"No value should be empty",Toast.LENGTH_LONG).show();
            }
        });
        Button cc = findViewById(R.id.wqq);
        cc.setOnClickListener( click -> {
            ;
            Intent q = new Intent(this, MainDatabase.class);
            startActivity(q);
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }


}