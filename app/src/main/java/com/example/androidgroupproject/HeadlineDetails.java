package com.example.androidgroupproject;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

public class HeadlineDetails extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_headline_details);

        Toolbar tBar = (Toolbar)findViewById(R.id.detailsToolbar);
        setSupportActionBar(tBar);

        Intent fromNewsReader = getIntent();
        Bundle extras = fromNewsReader.getExtras();

        String title = extras.getString(BBCnewsReader.TITLE);
        String description = extras.getString(BBCnewsReader.DESCRIPTION);
        String link = extras.getString(BBCnewsReader.LINK);
        String date = extras.getString(BBCnewsReader.DATE);

        TextView bbcTitle = findViewById(R.id.DetailsTitle);
        bbcTitle.setText(title);
        TextView bbcDescription = findViewById(R.id.DetailsDescription);
        bbcDescription.setText(description);
        TextView bbcLink = findViewById(R.id.DetailsLink);
        bbcLink.setText(link);
        bbcLink.setMovementMethod(LinkMovementMethod.getInstance());
        TextView bbcDate = findViewById(R.id.DetailsDate);
        bbcDate.setText(date);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.details_menu, menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }
}
