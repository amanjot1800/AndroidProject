package com.example.androidgroupproject;


import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BBCSavedArticlesDetails extends AppCompatActivity{

    String title, description, link, date;

    /**
     * This onCreate Method creates a View to and sets title, description, date, and link.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saved_articles_details);

        Intent fromNewsReader = getIntent();
        Bundle extras = fromNewsReader.getExtras();
        title = extras.getString(BBCSavedArticlesList.TITLE);
        description = extras.getString(BBCSavedArticlesList.DESCRIPTION);
        link = extras.getString(BBCSavedArticlesList.LINK);
        date = extras.getString(BBCSavedArticlesList.DATE);

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
