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
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ImageOfDayList extends AppCompatActivity {

    private ArrayList<ImageInformation> imageInformation = new ArrayList<>();
    private MyListAdapter myAdapter;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_of_day_list);

        ListView list = findViewById(R.id.list);
        list.setAdapter(myAdapter = new MyListAdapter());

        loadData();

        addData();

        list.setOnItemClickListener( (list2, view, position ,id) ->
        {
            Bundle data = new Bundle();
            data.putString("date", imageInformation.get(position).getDate());
            data.putString("url", imageInformation.get(position).getUrl());
            data.putString("hdurl", imageInformation.get(position).getHdurl());
            Intent nextActivity = new Intent(ImageOfDayList.this, EmptyActivity.class);
            nextActivity.putExtras(data);
            startActivity(nextActivity);
        });

        list.setOnItemLongClickListener( (p, v, position ,id) -> {

            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Delete")
                    .setMessage("Are you sure that you want to delete?")
                    .setPositiveButton("Delete", (c, arg) -> {
                        db.delete(DbOpener.TABLE_NAME, DbOpener.COL_ID + "= ?",  new String[] {Long.toString(id)});
                        deleteFile(imageInformation.get(position).getDate());
                        imageInformation.remove(position);
                        myAdapter.notifyDataSetChanged();

                    })
                    .setNegativeButton("Cancel", (c, arg) -> {})
                    .create().show();
            return true;
        });

    }

    private void addData(){

        if (getIntent().getBooleanExtra("addingData", false)) {

            String date = getIntent().getStringExtra("date");
            String url = getIntent().getStringExtra("url");
            String hdUrl = getIntent().getStringExtra("hdurl");

            boolean alreadyExits = false;
            for (ImageInformation s : imageInformation) {
                if (s.getDate().equals(date)) {
                    alreadyExits = true;
                }
            }

            if (!alreadyExits) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(DbOpener.DATE, date);
                contentValues.put(DbOpener.URL, url);
                contentValues.put(DbOpener.HDURL, hdUrl);

                long id = db.insert(DbOpener.TABLE_NAME, null, contentValues);

                imageInformation.add(new ImageInformation(date, url, hdUrl, id));
                myAdapter.notifyDataSetChanged();
            }
        }
    }

    private void loadData(){

        DbOpener dbOpener = new DbOpener(this);
        db = dbOpener.getWritableDatabase();

        String[] columns = {DbOpener.COL_ID, DbOpener.DATE, DbOpener.URL, DbOpener.HDURL};
        Cursor results = db.query(DbOpener.TABLE_NAME, columns, null, null, null, null, null);

        int idIndex = results.getColumnIndex(DbOpener.COL_ID);
        int dateIndex = results.getColumnIndex(DbOpener.DATE);
        int urlIndex = results.getColumnIndex(DbOpener.URL);
        int hdUrlIndex = results.getColumnIndex(DbOpener.HDURL);

        while (results.moveToNext()){

            long id = results.getLong(idIndex);
            String date = results.getString(dateIndex);
            String url = results.getString(urlIndex);
            String hdUrl = results.getString(hdUrlIndex);

            imageInformation.add(new ImageInformation(date, url, hdUrl, id));

        }

    }

    private class MyListAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return imageInformation.size();
        }

        @Override
        public Object getItem(int position) {
            return imageInformation.get(position).getDate();
        }

        @Override
        public long getItemId(int position) {
            return imageInformation.get(position).getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();
            View newView = inflater.inflate(R.layout.row_image_of_day, parent, false);
            TextView tview = newView.findViewById(R.id.row_image_of_day);
            tview.setText(getItem(position).toString());
            return newView;
        }
    }

}
