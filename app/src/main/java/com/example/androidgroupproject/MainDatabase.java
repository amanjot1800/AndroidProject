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
    private MyChat myAdapter;
    SQLiteDatabase db;
    public static ArrayList<ArrayClass> nasa = new ArrayList<ArrayClass>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_database);
        Intent fromMain = getIntent();
         nasa.clear();
        loadDataFromDatabase();
        String dt = fromMain.getStringExtra("u");
        String la = fromMain.getStringExtra("s");
        String lon = fromMain.getStringExtra("h");
        String url = fromMain.getStringExtra("b");
        ListView ls = findViewById(R.id.theListView);
        ls.setAdapter(myAdapter = new MyChat());
        boolean xits = false;
        for (ArrayClass s: nasa ) {
            if (s.getDate().equals(dt)) {
                xits = true;
            }
        }

            if (!xits) {
                ContentValues newRowValues = new ContentValues();
                newRowValues.put(DatabaseNasa.COL_LAT, la);
                newRowValues.put(DatabaseNasa.COL_LONG, lon);
                newRowValues.put(DatabaseNasa.COL_DATE, dt);
                newRowValues.put(DatabaseNasa.COL_URL, url);
                long newId = db.insert(DatabaseNasa.IMAGERY_TABLE, null, newRowValues);
                nasa.add(new ArrayClass(la, lon, url, dt, newId));
                myAdapter.notifyDataSetChanged();
            }








     /*   ContentValues newRowValues = new ContentValues();
        newRowValues.put(DatabaseNasa.COL_LAT, la);
        newRowValues.put(DatabaseNasa.COL_LONG, lon);
        newRowValues.put(DatabaseNasa.COL_DATE, dt);
        long newId = db.insert(DatabaseNasa.IMAGERY_TABLE, null, newRowValues);
        nasa.add(new ArrayClass(la, lon, dt, newId));
        myAdapter.notifyDataSetChanged();*/


        ls.setOnItemLongClickListener((a, b, c, d) -> {
            ArrayClass selectedContact = nasa.get(c);
            View contact_view = getLayoutInflater().inflate(R.layout.alertlayout, null);

            //get the TextViews
            TextView rowId = contact_view.findViewById(R.id.id);
            TextView rowName = contact_view.findViewById(R.id.name);
            TextView rowNam = contact_view.findViewById(R.id.na);
            TextView dd = contact_view.findViewById(R.id.s);

            rowName.setText("Latitude : " + selectedContact.getLatitude());
            rowNam.setText("Latitude : " + selectedContact.getLongitude());
            rowId.setText("id:" + selectedContact.getId());
            dd.setText("DATE - "+ selectedContact.getDate());

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            //alertDialogBuilder.setTitle("A title")
            alertDialogBuilder.setTitle("You clicked on item #" + c)
                    .setView(contact_view)
                    .setMessage("Do you want to delete it")
                    .setPositiveButton("Delete", (click, s) -> {
                        deleteContact(selectedContact); //remove the contact from database
                        nasa.remove(c); //remove the contact from contact list
                        myAdapter.notifyDataSetChanged(); //there is one less item so update the list
                    })

                    .setNegativeButton("Cancel", (click, s) -> {

                    })
                    .create().show();
            return true;


        });
    }


    private class MyChat extends BaseAdapter {
        @Override
        public int getCount() {
            return nasa.size();
        }

        @Override
        public Object getItem(int position) {
            return nasa.get(position).getUrl();
        }

        @Override
        public long getItemId(int position) {
            return nasa.get(position).getId();
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();
            View newView = inflater.inflate(R.layout.listlayout, parent, false);
            TextView text = newView.findViewById(R.id.y);
            text.setText(nasa.get(position).getLatitude().toString());
            ImageView img = newView.findViewById(R.id.aa);
            if (getItem(position).toString() != null) {
                Picasso.get().load(getItem(position).toString()).into(img);
                // Picasso.get().load("https://earthengine.googleapis.com/api/thumb?thumbid=a36d12a495624c09ddfff5cec1d39afb&token=e1dbd920320f88e885c2a062cebe66ec").into(img);
                return newView;
            } else
                return newView;

        }
    }

    private void loadDataFromDatabase() {
        //get a database connection:
        DatabaseNasa dbOpener = new DatabaseNasa(this);
        db = dbOpener.getWritableDatabase();


        // We want to get all of the columns. Look at MyOpener.java for the definitions:
        String[] columns = {DatabaseNasa.COL_ID,DatabaseNasa.COL_LAT, DatabaseNasa.COL_LONG, DatabaseNasa.COL_URL, DatabaseNasa.COL_DATE};
        //query all the results from the database:
        Cursor results = db.query(false, DatabaseNasa.IMAGERY_TABLE, columns, null, null, null, null, null, null);

        //Now the results object has rows of results that match the query.
        //find the column indices:

        int nameCollat = results.getColumnIndex(DatabaseNasa.COL_LAT);
        int idColLong = results.getColumnIndex(DatabaseNasa.COL_LONG);
        int ww = results.getColumnIndex(DatabaseNasa.COL_URL);
        int date = results.getColumnIndex(DatabaseNasa.COL_DATE);
        int id = results.getColumnIndex(DatabaseNasa.COL_ID);

        //iterate over the results, return true if there is a next item:
        while (results.moveToNext()) {
            String lat = results.getString(nameCollat);
            String lon = results.getString(idColLong);
            String aa = results.getString(ww);
            String da = results.getString(date);
            long ids = results.getLong(id);

            //add the new Contact to the array list:
            nasa.add(new ArrayClass(lat, lon, aa, da,ids));
        }
    }
    protected void deleteContact(ArrayClass c) {
        db.delete(DatabaseNasa.IMAGERY_TABLE, DatabaseNasa.COL_ID + "= ?", new String[]{Long.toString(c.getId())});
    }
}