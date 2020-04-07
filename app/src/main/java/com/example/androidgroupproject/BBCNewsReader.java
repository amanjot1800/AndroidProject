package com.example.androidgroupproject;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class BBCNewsReader extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    /**
     * This creates the toolbar, navigationView and its icons.
     * It also replaces the frame with the required Fragment
     * @param savedInstanceState \
     */
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

        BBCNewsList dFragment = new BBCNewsList();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.BBCframe, dFragment)
                .commit();

    }

    /**
     * This inflates a toolbar which contains buttons for user to interact with.
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bbc_menu, menu);

        return true;
    }

    /**
     * This is called when user click on one of the toolbar buttons
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String message = null;
        switch (item.getItemId()){

            // This button navigates user to the Guardian news search
            case R.id.goToGuardian:
                message = "Guardian News Search";
                Intent gotoGuardian = new Intent(BBCNewsReader.this, Guardian.class);
                startActivity(gotoGuardian);
                break;

            // This button navigates user to the Nasa Image of the day
            case R.id.goToNasaImageOfTheDay:
                message = "Nasa Image of the Day";
                Intent gotoImageOfTheDay = new Intent(BBCNewsReader.this, ImageOfTheDay.class);
                startActivity(gotoImageOfTheDay);
                break;

            // This button navigates user to the Nasa Imaginary Database
            case R.id.goToNasaImageOfTheDaybbc:
                message = "Imaginary Database";
                Intent gotoImaginaryDatabase = new Intent(BBCNewsReader.this, ImageryDatabase.class);
                startActivity(gotoImaginaryDatabase);
                break;

            // This button navigates user to the BBC saved Articles
            case R.id.goToSavedArticle:
                message = "Saved Articles";
                Intent gotoSavedArticles = new Intent(BBCNewsReader.this, BBCSavedArticlesList.class);
                startActivity(gotoSavedArticles);
                break;

            // This button helps the user familiarize with BBC latest news GUI
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

    /**
     * This is called when user click on one of the navigationView buttons
     * @param item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        String message = null;
        switch (item.getItemId()){

            // This button navigates user to the Guardian news search
            case R.id.goToGuardian:
                message = "Guardian News Search";
                Intent gotoGuardian = new Intent(BBCNewsReader.this, Guardian.class);
                startActivity(gotoGuardian);
                break;

            // This button navigates user to the Nasa Image of the day
            case R.id.goToNasaImageOfTheDay:
                message = "Nasa Image of the Day";
                Intent gotoImageOfTheDay = new Intent(BBCNewsReader.this, ImageOfTheDay.class);
                startActivity(gotoImageOfTheDay);
                break;

            // This button navigates user to the Nasa Imaginary Database
            case R.id.goToNasaImageOfTheDaybbc:
                message = "Imaginary Database";
                Intent gotoImaginaryDatabase = new Intent(BBCNewsReader.this, ImageryDatabase.class);
                startActivity(gotoImaginaryDatabase);
                break;

            // This button navigates user to the BBC saved Articles
            case R.id.goToSavedArticle:
                message = "Saved Articles";
                Intent gotoSavedArticles = new Intent(BBCNewsReader.this, BBCSavedArticlesList.class);
                startActivity(gotoSavedArticles);
                break;

            // This button helps the user familiarize with BBC latest news GUI
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
        return false;
    }

}
