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

public class BBCHeadlineDetails extends AppCompatActivity{

    String title, description, link, date;
    SQLiteDatabase db;
    ContentValues newRowValues;


    /**
     * This onCreate Method creates a View to and sets title, description, date, and link.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_headline_details);

        Toolbar tBar = (Toolbar)findViewById(R.id.detailsToolbar);
        setSupportActionBar(tBar);

        Intent fromNewsReader = getIntent();
        Bundle extras = fromNewsReader.getExtras();

        title = extras.getString(BBCNewsList.TITLE);
        description = extras.getString(BBCNewsList.DESCRIPTION);
        link = extras.getString(BBCNewsList.LINK);
        date = extras.getString(BBCNewsList.DATE);

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

    /**
     * This inflates a toolbar which contains buttons for user to interact with.
     * Specifically it contains a save button to save the particular article.
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.details_menu, menu);

        return true;
    }

    /**
     * This contains a option to save a particular article. It saves the article in the
     * database so that it can be viewed by the user later.
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            // Save button to save the article in the SQLiDatabase
            case R.id.save :
                BBCMyOpener dbOpener = new BBCMyOpener(this);
                db = dbOpener.getWritableDatabase();
                newRowValues = new ContentValues();
                newRowValues.put(BBCMyOpener.COL_TITLE, title);
                newRowValues.put(BBCMyOpener.COL_DESCRIPTION, description);
                newRowValues.put(BBCMyOpener.COL_LINK, link);
                newRowValues.put(BBCMyOpener.COL_DATE, date);
                long newId = db.insert(BBCMyOpener.TABLE_NAME, null, newRowValues);
                Snackbar.make(findViewById(R.id.detailsToolbar), R.string.ArticleSaved, Snackbar.LENGTH_LONG).show();
        }
        return true;
    }
}
