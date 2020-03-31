package com.example.androidgroupproject;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class BBC_HeadlineDetails extends AppCompatActivity{

    String title, description, link, date;
    SQLiteDatabase db;
    ContentValues newRowValues;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_headline_details);

        Toolbar tBar = (Toolbar)findViewById(R.id.detailsToolbar);
        setSupportActionBar(tBar);

        Intent fromNewsReader = getIntent();
        Bundle extras = fromNewsReader.getExtras();

        title = extras.getString(BBC_news_list.TITLE);
        description = extras.getString(BBC_news_list.DESCRIPTION);
        link = extras.getString(BBC_news_list.LINK);
        date = extras.getString(BBC_news_list.DATE);

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
        switch (item.getItemId()){
            case R.id.save :
                BBC_MyOpener dbOpener = new BBC_MyOpener(this);
                db = dbOpener.getWritableDatabase();
                newRowValues = new ContentValues();
                newRowValues.put(BBC_MyOpener.COL_TITLE, title);
                newRowValues.put(BBC_MyOpener.COL_DESCRIPTION, description);
                newRowValues.put(BBC_MyOpener.COL_LINK, link);
                newRowValues.put(BBC_MyOpener.COL_DATE, date);
                long newId = db.insert(BBC_MyOpener.TABLE_NAME, null, newRowValues);
                Snackbar.make(findViewById(R.id.detailsToolbar), R.string.ArticleSaved, Snackbar.LENGTH_LONG).show();
        }
        return true;
    }
}
