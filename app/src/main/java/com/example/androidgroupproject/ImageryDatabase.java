package com.example.androidgroupproject;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

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


     // getting the navigation id
        NavigationView navigationView = findViewById(R.id.qqq);
        navigationView.setNavigationItemSelectedListener(this);

        latitude = findViewById(R.id.lati);
        String savedLat = shared.getString("ReserveName", "");
        latitude.setText(savedLat);
        longitude = findViewById(R.id.loni);
        // using shared prefrence variable
        String savedlong = sha.getString("Reserve", "");
        longitude.setText(savedlong);
        Button btn = findViewById(R.id.subm);
        btn.setOnClickListener(click -> {
            // checkin the condition the data latitude and the longitude shoul not be empty
            if (!latitude.getText().toString().isEmpty() && !longitude.getText().toString().isEmpty()) {
                saveShared(latitude.getText().toString());
                save(longitude.getText().toString());
                // making intent to go the new activity
                Intent gotoMain = new Intent(this, ImageryNasaActivity.class);
                gotoMain.putExtra("shubham", Double.parseDouble(latitude.getText().toString()));
                gotoMain.putExtra("sharma", Double.parseDouble(longitude.getText().toString()));
                startActivity(gotoMain);
            } else {
                // if values are empty show the toast
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

  // used to save the data in file to access it
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

    // method used to make the navigation menu
    public boolean onNavigationItemSelected(MenuItem item) {

        String message = null;
      /// this is used when to click the help button
        switch (item.getItemId()) {
            case R.id.hlpe:
                // to sho the description of the help
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

                alertDialogBuilder.setMessage(R.string.yo)
                        .setNegativeButton("Cancel", (click, s) -> {

                        })
                        .create().show();
                break;
            // used to go to the favorites list
            case R.id.shubhamsearch:
                Intent test = new Intent(this, Test.class);
                startActivity(test);



        }


        return false;
    }
//    use to select the items and perform function on it
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    // used the switch case
        switch (item.getItemId()) {
            case R.id.gur:
               // this goes to the guardian activity
                Intent gotoGuardian = new Intent(this, Guardian.class);
                startActivity(gotoGuardian);
                break;

            case R.id.nasaimg:
          // this goes to image of the data activity
                Intent gotoImageOfTheDay = new Intent(this, ImageOfTheDay.class);
                startActivity(gotoImageOfTheDay);
                break;
           // this goes to the bbc news reader
            case R.id.bbc:
                Intent aa = new Intent(this, BBCNewsReader.class);
                startActivity(aa);

        }
        return true;
    }
}

