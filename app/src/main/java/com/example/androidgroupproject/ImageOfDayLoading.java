package com.example.androidgroupproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageOfDayLoading extends AppCompatActivity {

    /***
     * The base url from which data is downloaded
     */
    private final String URL = "https://api.nasa.gov/planetary/apod?api_key=DgPLcIlnmN0Cwrzcg3e9NraFaYLIDI68Ysc6Zh3d&date=";
    /**
     * These are all the textviews that show image information
     */
    private TextView turl, thdurl, tdate;
    /**
     * Progresbar that updates as the data is downloaded
     */
    private ProgressBar tprogressBar;
    /**
     * Image where the doenloaded image is saved
     */
    private ImageView timage;
    /**
     * Temporary strings used to handle daya
     */
    String url, date, hdurl;
    /**
     * Bitmap used to set image to the ImageView in activity layout
     */
    Bitmap image;



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
        timage = (ImageView) findViewById(R.id.imageViewNASA);

        String date2 = getIntent().getStringExtra("date");

        DownloadData data = new DownloadData();
        data.execute(URL + date2);

        Button saveAndGoToDatabase = findViewById(R.id.saveAndGoToDatabase);
        saveAndGoToDatabase.setOnClickListener( clk -> {

            try {
                FileOutputStream outputStream = openFileOutput(date, Context.MODE_PRIVATE);
                image.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                outputStream.flush();
                outputStream.close();
            }
            catch (IOException ex){
                ex.printStackTrace();
            }

            Intent intent = new Intent(ImageOfDayLoading.this, ImageOfDayList.class);
            intent.putExtra("addingData", true);
            intent.putExtra("date", date);
            intent.putExtra("url", url);
            intent.putExtra("hdurl", hdurl);

            Toast.makeText(this, "Image Information Saved", Toast.LENGTH_LONG).show();
            startActivityForResult(intent, 101);

        });
    }

    /**
     * This AsyncTask class downloads data from the website API. It runs in the baclground thread
     * and initializes all the firlds and fills the textviews after comletion
     */
    private class DownloadData extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {

            try{

                String myUrl = params[0];

                publishProgress(10);

                URL url2 = new URL(myUrl);
                HttpURLConnection conn = (HttpURLConnection) url2.openConnection();
                InputStream inStream = conn.getInputStream();

                publishProgress(25);

                BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null)
                {
                    sb.append(line + "\n");
                }
                String result = sb.toString();

                publishProgress(35);

                JSONObject imageData = new JSONObject(result);

                date = imageData.getString("date");
                url = imageData.getString("url");
                hdurl = imageData.getString("hdurl");

                publishProgress(65);

                URL imageURL = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) imageURL.openConnection();
                connection.connect();

                publishProgress(75);

                if (connection.getResponseCode() == 200){
                    image = BitmapFactory.decodeStream(connection.getInputStream());
                }

                publishProgress(100);
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
            return null;
        }

        /**
         * This method updates the progressBar
         * @param values the values uses to display bar length
         */
        @Override
        protected void onProgressUpdate(Integer... values) {
            tprogressBar.setVisibility(View.VISIBLE);
            tprogressBar.setProgress(values[0]);
        }

        /**
         * This mehtod sets data to all the textviews after the data has been doenloaded
         * @param s ---?
         */
        @Override
        protected void onPostExecute(String s) {

            tdate.setText(date);
            turl.setText(url);
            thdurl.setText(hdurl);
            tprogressBar.setVisibility(View.INVISIBLE);
            timage.setImageBitmap(image);

        }

    }

    /**
     * This methods inflates the options menu
     * @param menu the menu to be inflated
     * @return boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.image_of_the_day_menu, menu);

        return true;
    }
}
