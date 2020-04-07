package com.example.androidgroupproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainDatabase extends AppCompatActivity {
  //  private MyChat myAdapter;
    SQLiteDatabase db;
    public static ArrayList<ArrayClass> nasa = new ArrayList<ArrayClass>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent fromMain = getIntent();
         nasa.clear();
        loadDataFromDatabase();
     // Getting the data from the intent
        String la = fromMain.getStringExtra("s");
        String lon = fromMain.getStringExtra("h");
        String url = fromMain.getStringExtra("b");

        boolean xits = false;
        for (ArrayClass ss: nasa ) {
            if (ss.getLongitude().equals(lon) && ss.getLatitude().equals(la) ) {
                xits = true;
            }
        }

            if (!xits) {
                // putting data in the content values
                ContentValues newRowValues = new ContentValues();
                newRowValues.put(DatabaseNasa.COL_LAT, la);
                newRowValues.put(DatabaseNasa.COL_LONG, lon);
               // newRowValues.put(DatabaseNasa.COL_DATE, dt);
                newRowValues.put(DatabaseNasa.COL_URL, url);
                long newId = db.insert(DatabaseNasa.IMAGERY_TABLE, null, newRowValues);
                nasa.add(new ArrayClass(la, lon, url, newId));
               // myAdapter.notifyDataSetChanged();
            }


  setResult(500);
  finish();





    }




    private void loadDataFromDatabase() {
        //get a database connection:
        DatabaseNasa dbOpener = new DatabaseNasa(this);
        db = dbOpener.getWritableDatabase();


        // We want to get all of the columns. Look at MyOpener.java for the definitions:
        String[] columns = {DatabaseNasa.COL_ID,DatabaseNasa.COL_LAT, DatabaseNasa.COL_LONG, DatabaseNasa.COL_URL};
        //query all the results from the database:
        Cursor results = db.query(false, DatabaseNasa.IMAGERY_TABLE, columns, null, null, null, null, null, null);

        //Now the results object has rows of results that match the query.
        //find the column indices:

        int nameCollat = results.getColumnIndex(DatabaseNasa.COL_LAT);
        int idColLong = results.getColumnIndex(DatabaseNasa.COL_LONG);
        int ww = results.getColumnIndex(DatabaseNasa.COL_URL);
      //  int date = results.getColumnIndex(DatabaseNasa.COL_DATE);
        int id = results.getColumnIndex(DatabaseNasa.COL_ID);

        //iterate over the results, return true if there is a next item:
        while (results.moveToNext()) {
            String lat = results.getString(nameCollat);
            String lon = results.getString(idColLong);
            String aa = results.getString(ww);
            //String da = results.getString(date);
            long ids = results.getLong(id);

            //add the new Contact to the array list:
            nasa.add(new ArrayClass(lat, lon, aa, ids));
        }

    }

}