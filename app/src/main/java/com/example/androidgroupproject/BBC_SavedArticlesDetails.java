package com.example.androidgroupproject;


import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;

public class BBC_SavedArticlesDetails extends AppCompatActivity{

    String title, description, link, date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saved_articles_details);

        Intent fromNewsReader = getIntent();
        Bundle extras = fromNewsReader.getExtras();
        title = extras.getString(BBC_Saved_Articles_List.TITLE);
        description = extras.getString(BBC_Saved_Articles_List.DESCRIPTION);
        link = extras.getString(BBC_Saved_Articles_List.LINK);
        date = extras.getString(BBC_Saved_Articles_List.DATE);

        TextView bbcTitle = findViewById(R.id.SavedDetailsTitle);
        bbcTitle.setText(title);
        TextView bbcDescription = findViewById(R.id.SavedDetailsDescription);
        bbcDescription.setText(description);
        TextView bbcLink = findViewById(R.id.SavedDetailsLink);
        bbcLink.setText(link);
        bbcLink.setMovementMethod(LinkMovementMethod.getInstance());
        TextView bbcDate = findViewById(R.id.SavedDetailsDate);
        bbcDate.setText(date);

    }
}
