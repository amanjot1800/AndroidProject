package com.example.androidgroupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

public class ImageryDatabase extends AppCompatActivity {
    EditText latitude;
    EditText longitude;

    AsyncTask<String, Integer, String> nasaImg;
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagery_database);
        latitude = findViewById(R.id.lati);
        longitude = findViewById(R.id.loni);

        Button btn = findViewById(R.id.subm);
        btn.setOnClickListener(click -> {
            if(!latitude.getText().toString().isEmpty() && !longitude.getText().toString().isEmpty()) {
                Intent gotoMain = new Intent(this, ImageryNasaActivity.class);
                gotoMain.putExtra("shubham", Double.parseDouble(latitude.getText().toString()));
                gotoMain.putExtra("sharma", Double.parseDouble(longitude.getText().toString()));
                startActivity(gotoMain);
            }else
            {
                Toast.makeText(this,"No value should be empty",Toast.LENGTH_LONG).show();
            }
        });
        Button cc = findViewById(R.id.wqq);
        cc.setOnClickListener( click -> {
            ;
            Intent q = new Intent(this, MainDatabase.class);
            startActivity(q);
        });
    }



}