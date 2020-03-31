package com.example.androidgroupproject;


import android.app.AlertDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class BBC_NewsReader extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bbc_news_reader);

        Toolbar tBar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(tBar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer, tBar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        BBC_news_list dFragment = new BBC_news_list();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.BBCframe, dFragment)
                .commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bbc_menu, menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String message = null;
        switch (item.getItemId()){

            case R.id.goToGuardian:
                message = "Guardian News Search";
                Intent gotoGuardian = new Intent(BBC_NewsReader.this, Guardian.class);
                startActivity(gotoGuardian);
                break;

            case R.id.goToNasaImageOfTheDay:
                message = "Nasa Image of the Day";
                Intent gotoImageOfTheDay = new Intent(BBC_NewsReader.this, ImageOfTheDay.class);
                startActivity(gotoImageOfTheDay);
                break;

            case R.id.goToNasaImageOfTheDaybbc:
                message = "Imaginary Database";
                Intent gotoImaginaryDatabase = new Intent(BBC_NewsReader.this, ImageryDatabase.class);
                startActivity(gotoImaginaryDatabase);
                break;

            case R.id.goToSavedArticle:
                message = "Saved Articles";
                Intent gotoSavedArticles = new Intent(BBC_NewsReader.this, BBC_Saved_Articles_List.class);
                startActivity(gotoSavedArticles);
                break;

            case R.id.help:
                message = "Help";
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("Help")
                        .setIcon(R.drawable.info)
                        .setMessage(R.string.bbc_help)
                        .setPositiveButton("Ok", (c,clk) -> {})
                        .create().show();
                break;
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        String message = null;
        switch (item.getItemId()){

            case R.id.goToGuardian:
                message = "Guardian News Search";
                Intent gotoGuardian = new Intent(BBC_NewsReader.this, Guardian.class);
                startActivity(gotoGuardian);
                break;

            case R.id.goToNasaImageOfTheDay:
                message = "Nasa Image of the Day";
                Intent gotoImageOfTheDay = new Intent(BBC_NewsReader.this, ImageOfTheDay.class);
                startActivity(gotoImageOfTheDay);
                break;

            case R.id.goToNasaImageOfTheDaybbc:
                message = "Imaginary Database";
                Intent gotoImaginaryDatabase = new Intent(BBC_NewsReader.this, ImageryDatabase.class);
                startActivity(gotoImaginaryDatabase);
                break;

            case R.id.goToSavedArticle:
                message = "Saved Articles";
                Intent gotoSavedArticles = new Intent(BBC_NewsReader.this, BBC_Saved_Articles_List.class);
                startActivity(gotoSavedArticles);
                break;

            case R.id.help:
                message = "Saved Articles";
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("Help")
                        .setIcon(R.drawable.help)
                        .setMessage(R.string.bbc_help)
                        .setPositiveButton("Ok", (c,clk) -> {})
                        .create().show();
                break;
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        return false;
    }

}
