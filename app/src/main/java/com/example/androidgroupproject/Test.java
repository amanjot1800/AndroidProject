package com.example.androidgroupproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

public class Test extends AppCompatActivity {
    SQLiteDatabase db;
    MyChat myAdapter;
    ArrayList<ArrayClass> nasa=new ArrayList<>();
    public static final String IMAGEURL = "item";
    public static final String ITEM_LAT = "LAT";
    public static final String ID = "id";
    public static final String ITEM_LONG = "LONG";
    public static final String ITEM_POSITION = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        boolean isTablet = findViewById(R.id.fragment) != null;
        ListView ls = findViewById(R.id.list);
        db=new DatabaseNasa(this).getReadableDatabase();


        Cursor results= db.rawQuery("SELECT * FROM Imagery",null);
        int nameCollat = results.getColumnIndex(DatabaseNasa.COL_LAT);
        int idColLong = results.getColumnIndex(DatabaseNasa.COL_LONG);
        int ww = results.getColumnIndex(DatabaseNasa.COL_URL);
      // int date = results.getColumnIndex(DatabaseNasa.COL_DATE);
        int id = results.getColumnIndex(DatabaseNasa.COL_ID);

          while(results.moveToNext()){

              String lat = results.getString(nameCollat);
              String lon = results.getString(idColLong);
              String aa = results.getString(ww);
             // String da = results.getString(date);
              long ids = results.getLong(id);

              //add the new Contact to the array list:
              nasa.add(new ArrayClass(lat, lon, aa,ids));
          }
        ls.setAdapter(myAdapter = new MyChat());

//        new ArrayClass(la, lon, url, dt, newId)

        ls.setOnItemClickListener((list, view, position, q) -> {
                    Bundle dataToPass = new Bundle();
                    dataToPass.putString(ITEM_LAT, nasa.get(position).getLatitude());
                    dataToPass.putString(ITEM_LONG, nasa.get(position).getLongitude());
                    dataToPass.putString(IMAGEURL, nasa.get(position).getUrl());
                     dataToPass.putInt(ITEM_POSITION, position);
                    dataToPass.putLong(ID, q);
            if (isTablet) {
                lastfrag dFragment = new lastfrag(); //add a DetailFragment
                dFragment.setArguments(dataToPass);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment, dFragment) //Add the fragment in FrameLayout
                        .commit(); //actually load the fragment.
            }
            else {
                Intent nextActivity = new Intent(Test.this, Lastempty.class);
                nextActivity.putExtras(dataToPass); //send data to next activity
                startActivity(nextActivity); //make the transition
            }
                });


        ls.setOnItemLongClickListener((a, b, c, d) -> {
            ArrayClass selectedContact = nasa.get(c);
            View contact_view = getLayoutInflater().inflate(R.layout.alertlayout, null);

            //get the TextViews
            TextView rowId = contact_view.findViewById(R.id.id);
            TextView rowName = contact_view.findViewById(R.id.name);
            TextView rowNam = contact_view.findViewById(R.id.na);

            rowName.setText(selectedContact.getLatitude());
            rowNam.setText(selectedContact.getLongitude());
            rowId.setText("id:" + selectedContact.getId());
            //  dd.setText("DATE - "+ selectedContact.getDate());

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
    class MyChat extends BaseAdapter {
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

                Picasso.get().load(getItem(position).toString()).into(img);

                return newView;


        }
    }
    protected void deleteContact(ArrayClass c) {
        db.delete(DatabaseNasa.IMAGERY_TABLE, DatabaseNasa.COL_ID + "= ?", new String[]{Long.toString(c.getId())});
    }

}

