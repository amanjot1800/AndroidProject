package com.example.androidgroupproject;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.androidgroupproject.R;
import com.google.android.material.navigation.NavigationView;


public class ImageryDatabase extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    EditText latitude;
    EditText longitude;
    SharedPreferences shared = null;
    SharedPreferences sha = null;
    AsyncTask<String, Integer, String> nasaImg;
    ProgressBar mProgressBar;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagery_database);
        shared = getSharedPreferences("NasaImg", Context.MODE_PRIVATE);
        sha = getSharedPreferences("ss", Context.MODE_PRIVATE);

        androidx.appcompat.widget.Toolbar tBar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolr);


        //This loads the toolbar, which calls onCreateOptionsMenu below:
        setSupportActionBar(tBar);

        //For NavigationDrawer:
        DrawerLayout drawer = findViewById(R.id.shubham);
        toggle = new ActionBarDrawerToggle(this,
                drawer, tBar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        ///  getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = findViewById(R.id.qqq);
        navigationView.setNavigationItemSelectedListener(this);

        latitude = findViewById(R.id.lati);
        String savedLat = shared.getString("ReserveName", "");
        latitude.setText(savedLat);
        longitude = findViewById(R.id.loni);
        String savedlong = sha.getString("Reserve", "");
        longitude.setText(savedlong);
        Button btn = findViewById(R.id.subm);
        btn.setOnClickListener(click -> {
            if (!latitude.getText().toString().isEmpty() && !longitude.getText().toString().isEmpty()) {
                saveShared(latitude.getText().toString());
                save(longitude.getText().toString());
                Intent gotoMain = new Intent(this, ImageryNasaActivity.class);
                gotoMain.putExtra("shubham", Double.parseDouble(latitude.getText().toString()));
                gotoMain.putExtra("sharma", Double.parseDouble(longitude.getText().toString()));
                startActivity(gotoMain);
            } else {
                Toast.makeText(this, R.string.qq, Toast.LENGTH_LONG).show();
            }
        });
        Button cc = findViewById(R.id.wqq);
        cc.setOnClickListener(click -> {

            Intent qq = new Intent(this, Test.class);
            startActivity(qq);
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }


    private void saveShared(String stringToSave) {
        SharedPreferences.Editor editor = shared.edit();
        editor.putString("ReserveName", stringToSave);
        editor.commit();
    }

    private void save(String stringToSave) {
        SharedPreferences.Editor editor = sha.edit();
        editor.putString("Reserve", stringToSave);
        editor.commit();
    }

    public boolean onNavigationItemSelected(MenuItem item) {

        String message = null;

        switch (item.getItemId()) {
            case R.id.hlpe:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

                alertDialogBuilder.setMessage(R.string.yo)
                        .setNegativeButton("Cancel", (click, s) -> {

                        })
                        .create().show();
                break;

            case R.id.shubhamsearch:
                Intent test = new Intent(this, Test.class);
                startActivity(test);



        }


        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.gur:

                Intent gotoGuardian = new Intent(this, Guardian.class);
                startActivity(gotoGuardian);
                break;

            case R.id.nasaimg:

                Intent gotoImageOfTheDay = new Intent(this, ImageOfTheDay.class);
                startActivity(gotoImageOfTheDay);
                break;

            case R.id.bbc:
                Intent aa = new Intent(this, BBC_NewsReader.class);
                startActivity(aa);

        }
        return true;
    }
}

