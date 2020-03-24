package com.example.androidgroupproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ArticleDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        Intent intent= getIntent();
        TextView title = findViewById(R.id.titleEdit);
        TextView url = findViewById(R.id.urlEdit);
        TextView section = findViewById(R.id.sectionEdit);
        TextView link = findViewById(R.id.urlEdit);

        String t= intent.getStringExtra("title");
        String sect= intent.getStringExtra("sectionName");
        String u= intent.getStringExtra("url");

        title.setText(t);
        url.setText(u);
        section.setText(sect);
//        link.setOnClickListener(c->{
//            String urli = u;
//            Intent i = new Intent(Intent.ACTION_VIEW);
//            i.setData( Uri.parse(urli) );
//            startActivity(i);
//        });
    }
}
