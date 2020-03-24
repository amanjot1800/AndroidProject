package com.example.androidgroupproject;


import android.app.AlertDialog;
import android.content.Intent;
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
import android.widget.Toast;

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
    public static final String TITLE = "TITLE";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String LINK = "LINK";
    public static final String DATE = "DATE";
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

        bar = findViewById(R.id.progressBar);
        bar.setVisibility(View.VISIBLE);

        HeadlineQuery req = new HeadlineQuery();
        req.execute("http://feeds.bbci.co.uk/news/world/us_and_canada/rss.xml");
        ListView headlines = findViewById(R.id.news_list);
        headlines.setAdapter( myAdapter = new HeadlineAdapter());
        headlines.setOnItemClickListener((p, v, position, id)-> {
            Bundle dataToPass = new Bundle();
            dataToPass.putString(TITLE, list.get(position).getTitle());
            dataToPass.putString(DESCRIPTION, list.get(position).getDescription());
            dataToPass.putString(LINK, list.get(position).getLink());
            dataToPass.putString(DATE, list.get(position).getDateOfArticle());

            Intent nextActivity = new Intent(BBCnewsReader.this, HeadlineDetails.class);
            nextActivity.putExtras(dataToPass);
            startActivity(nextActivity); //make the transition
            Toast.makeText(this, "News no. "+(position+1), Toast.LENGTH_LONG).show();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bbc_menu, menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.help:
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("Help")
                        .setIcon(R.drawable.help)
                        .setMessage("This App will bring you the latest news from BBC.")
                        .setPositiveButton("Ok", (c,clk) -> {})
                        .create().show();
        }

        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.help:
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("Help")
                        .setIcon(R.drawable.help)
                        .setMessage("This App will bring you the latest news from BBC.")
                        .setPositiveButton("Ok", (c,clk) -> {})
                        .create().show();
        }
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
            titleView.setText( thisRow.getTitle());

            return headlineView;
        }
    }
    private class HeadlineQuery extends AsyncTask<String, Integer, String> {

        String  title, description, link, date;
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

                int count = 1;

                //now loop over the XML:
                while (xpp.getEventType() != XmlPullParser.END_DOCUMENT)
                {
                    switch (xpp.getEventType()) {

                        case XmlPullParser.START_TAG:
                            String name = xpp.getName();
                            if (name.equals("title")) {
                                xpp.next();
                                String tempTitle = xpp.getText();
                                if (!tempTitle.contains("BBC News - US & Canada")) {
                                    title = tempTitle;
                                    Log.i("Item", String.valueOf(count++));
                                    Log.i("Title is:", title);
                                    publishProgress(30);
                                }
                            }
                            else if (name.equals("description")) {
                                xpp.next();
                                String tempDescription = xpp.getText();
                                if(!tempDescription.contains("BBC News - US & Canada")){
                                    description = tempDescription;
                                    Log.i("Description is:", description);
                                    publishProgress(30);
                                }
                            }
                            else if (name.equals("link")) {
                                xpp.next();
                                String tempLink = xpp.getText();
                                if(!tempLink.equals("https://www.bbc.co.uk/news/")){
                                    link = tempLink;
                                    Log.i("Link is:", link);
                                    publishProgress(30);
                                }
                            }
                            else if (name.equals("pubDate")) {
                                xpp.next();
                                date = xpp.getText();
                                publishProgress(30);
                                Log.i("Publish Date is: ", date);
                                publishProgress(30);
                            }
                            if(title!=null && description!=null && date!=null) {
//                                ContentValues newRowValues = new ContentValues();
//                                newRowValues.put(BBCmyOpener.COL_TITLE, title);
//                                newRowValues.put(BBCmyOpener.COL_DESCRIPTION, description);
//                                newRowValues.put(BBCmyOpener.COL_LINK, link);
//                                newRowValues.put(BBCmyOpener.COL_DATE, date);
//                                long newId = db.insert(BBCmyOpener.TABLE_NAME, null, newRowValues);

                                list.add(new Headline(title, description, link, date));
                                title = null;
                                description = null;
                                link = null;
                                date = null;
                            }
                            break;
                        case XmlPullParser.TEXT:
                            break;
                    }
                    xpp.next();//look at next XML tag
                }
                publishProgress(100);
            } catch (Exception ex) {}

            return "done";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            bar.setVisibility(View.VISIBLE);
            bar.setProgress(values[0]);

        }

        @Override
        protected void onPostExecute(String s) {
            myAdapter.notifyDataSetChanged();

            bar.setVisibility(View.INVISIBLE);
        }
    }
}
