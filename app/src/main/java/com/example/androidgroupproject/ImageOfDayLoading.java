package com.example.androidgroupproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageOfDayLoading extends AppCompatActivity {

    private final String URL = "https://api.nasa.gov/planetary/apod?api_key=DgPLcIlnmN0Cwrzcg3e9NraFaYLIDI68Ysc6Zh3d&date=";
    private TextView turl, thdurl, tdate;
    private ProgressBar tprogressBar;
    String url, date, hdurl;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_show);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tdate = (TextView) findViewById(R.id.date);
        thdurl = (TextView) findViewById(R.id.hdurl);
        turl = (TextView) findViewById(R.id.url);
        tprogressBar = (ProgressBar) findViewById(R.id.progressBar);


        String date2 = getIntent().getStringExtra("date");

        ReadData data = new ReadData();
        data.execute(URL + date2);

        Button saveAndGoToDatabase = findViewById(R.id.saveAndGoToDatabase);
        saveAndGoToDatabase.setOnClickListener( clk -> {
            Intent intent = new Intent(ImageOfDayLoading.this, ImageOfDayList.class);
            intent.putExtra("date", date);
            intent.putExtra("url", url);
            intent.putExtra("hdurl", hdurl);
            startActivityForResult(intent, 101);

        });


    }

    private class ReadData extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {

            try{

                String myUrl = params[0];

                publishProgress(25);

                URL url2 = new URL(myUrl);
                HttpURLConnection conn = (HttpURLConnection) url2.openConnection();
                InputStream inStream = conn.getInputStream();

                publishProgress(50);

                BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null)
                {
                    sb.append(line + "\n");
                }
                String result = sb.toString();
                Log.i("test", result);

                publishProgress(75);

                JSONObject imageData = new JSONObject(result);

                date = imageData.getString("date");
                url = imageData.getString("url");
                hdurl = imageData.getString("hdurl");


                publishProgress(100);


            }
            catch (Exception ex){
                ex.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            tprogressBar.setVisibility(View.VISIBLE);
            tprogressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {

            tdate.setText(date);
            turl.setText(url);
            thdurl.setText(hdurl);
            tprogressBar.setVisibility(View.INVISIBLE);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.image_of_the_day_menu, menu);

        return true;
    }
}
