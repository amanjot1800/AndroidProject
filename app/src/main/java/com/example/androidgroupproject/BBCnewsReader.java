package com.example.androidgroupproject;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class BBCnewsReader extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private HeadlineAdapter myAdapter;
    private ArrayList<Headline> list = new ArrayList<>();
    SQLiteDatabase db;
    private ProgressBar bar;
    private TextView bbcTitle, bbcDescription, bbcDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bbc_news_reader);

        Toolbar tBar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(tBar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer, tBar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        BBC_news_list dFragment = new BBC_news_list();
//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.BBCframe, dFragment)
//                .commit();

        bbcTitle = findViewById(R.id.bbcTitle);
        bbcDescription = findViewById(R.id.bbcDescription);
        bbcDate = findViewById(R.id.bbcPubDate);
        bar = findViewById(R.id.progressBar);
        bar.setVisibility(View.VISIBLE);

        HeadlineQuery req = new HeadlineQuery();
        req.execute("http://feeds.bbci.co.uk/news/world/us_and_canada/rss.xml");
        ListView headlines = findViewById(R.id.news_list);
        headlines.setAdapter( myAdapter = new HeadlineAdapter());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bbc_menu, menu);


	    /* slide 15 material:
	    MenuItem searchItem = menu.findItem(R.id.search_item);
        SearchView sView = (SearchView)searchItem.getActionView();
        sView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }  });

	    */

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }

    class HeadlineAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Headline getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) { return getItem(position).getId(); }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();

            Headline thisRow = getItem(position);
            //make a new row:
            View headlineView = inflater.inflate(R.layout.bbc_headline, parent, false);
            TextView titleView = headlineView.findViewById(R.id.bbcTitle);
            titleView.setText( thisRow.getDescription());
            TextView descriptionView = headlineView.findViewById(R.id.bbcDescription);
            descriptionView.setText( thisRow.getDescription());
            TextView dateView = headlineView.findViewById(R.id.bbcPubDate);
            dateView.setText( thisRow.getDateOfArticle());

            return headlineView;
        }
    }
    private class HeadlineQuery extends AsyncTask<String, Integer, String> {

        String  title, description, date;
        @Override
        protected String doInBackground(String... params) {
            try {
                //get the string url:
                String myUrl = params[0];

                //create the network connection:
                URL url = new URL(myUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                InputStream inStream = conn.getInputStream();

                //create a pull parser:
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(inStream, "UTF-8");


                //now loop over the XML:
                while (xpp.getEventType() != XmlPullParser.END_DOCUMENT)
                {
                    if (xpp.getEventType() == XmlPullParser.START_TAG)
                    {
                        if(xpp.getName().equals("item")){
                            if (xpp.getName().equals("title"))
                            {
                                title = xpp.nextText();
                            }
                            else if (xpp.getName().equals("description"))
                            {
                                description = xpp.nextText();
                            }
                            else if (xpp.getName().equals("pubDate"))
                            {
                                date = xpp.nextText();
                            }
                        }


                        ContentValues newRowValues = new ContentValues();
                        newRowValues.put(BBCmyOpener.COL_TITLE, title);
                        newRowValues.put(BBCmyOpener.COL_DESCRIPTION, description);
                        newRowValues.put(BBCmyOpener.COL_DATE, date);

                        long newId = db.insert(BBCmyOpener.TABLE_NAME, null, newRowValues);
                        list.add(new Headline(title, description , date, newId));
                        }
                    xpp.next(); //advance to next XML event
                }

            } catch (Exception ex) {
            }

            return "done";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            bar.setVisibility(View.VISIBLE);
            bar.setProgress(values[0]);

        }

        @Override
        protected void onPostExecute(String s) {
//            bbcTitle.setText(title);
//            bbcDescription.setText(description);
//            bbcDate.setText(date);
            myAdapter.notifyDataSetChanged();
            bar.setProgress(100);
            bar.setVisibility(View.INVISIBLE);
        }
    }
}
