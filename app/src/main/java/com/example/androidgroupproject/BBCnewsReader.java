package com.example.androidgroupproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class BBCnewsReader extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private HeadlineAdapter myAdapter;
    private ArrayList<Headline> list = new ArrayList<>();
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

//        BBC_news_list dFragment = new BBC_news_list();
//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.BBCframe, dFragment)
//                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bbc_menu, menu);


	    /* slide 15 material:
	    MenuItem searchItem = menu.findItem(R.id.search_item);
        SearchView sView = (SearchView)searchItem.getActionView();
        sView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }  });

	    */

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        return false;
    }

    class HeadlineAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Headline getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) { return getItem(position).getId(); }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();

            Headline thisRow = getItem(position);
            //make a new row:
            View headlineView = inflater.inflate(R.layout.bbc_headline, parent, false);
            TextView tView = headlineView.findViewById(R.id.bbcTitle);
            tView.setText( thisRow.getTitle());
            return headlineView;
        }
    }
}
