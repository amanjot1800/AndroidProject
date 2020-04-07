package com.example.androidgroupproject;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static com.example.androidgroupproject.MyOpener.COL_SECT;
import static com.example.androidgroupproject.MyOpener.COL_TITLE;
import static com.example.androidgroupproject.MyOpener.COL_URL;
import static com.example.androidgroupproject.MyOpener.TABLE_FAV;

public class Guardian extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private ArrayList<Article> articles = new ArrayList<>();
    private MyAdapter adapter;
    private SQLiteDatabase db;
    Cursor results;
    private ProgressBar progressBar;
    SharedPreferences prefs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardian);
        loadDataFromDatabase();
        boolean isTablet = findViewById(R.id.fragmentLocation)!=null;

        Toolbar tBar = findViewById(R.id.toolBar);
        setSupportActionBar(tBar);
        DrawerLayout drawer = findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer, tBar, R.string.open,R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView nv = findViewById(R.id.navigation);
        nv.setNavigationItemSelectedListener(this);

        Button fav = findViewById(R.id.favouriteButton);
        Intent goToFav = new Intent(Guardian.this, Favourite.class);
        fav.setOnClickListener(b->startActivity(goToFav));
        EditText topic = findViewById(R.id.search);
        ImageButton searchButton = findViewById(R.id.srchBtn);
        ListView listView = findViewById(R.id.list);
        listView.setAdapter(adapter = new MyAdapter());
        progressBar = findViewById(R.id.progBar);
        progressBar.setVisibility(View.VISIBLE);

        searchButton.setOnClickListener(cl -> {
            saveSharedPrefs( topic.getText().toString());
            db.execSQL(" DROP TABLE IF EXISTS " + MyOpener.TABLE_NAME);
            new MyOpener(this).onCreate(db);
            articles.clear();
            adapter.notifyDataSetChanged();
            Scraper thread = new Scraper();
            if (!topic.getText().toString().isEmpty())
                thread.execute("https://content.guardianapis.com/search?api-key=1fb36b70-1588-4259-b703-2570ea1fac6a&q="+topic.getText().toString());
            else
                Toast.makeText(this, "Please enter a Topic to search", Toast.LENGTH_SHORT).show();
        });

        //listView.setOnItemLongClickListener();
        listView.setOnItemClickListener((parent, view, pos, id)->{
            Bundle dataToPass = new Bundle();
            Article selectedArticle = articles.get(pos);
            dataToPass.putString("title",selectedArticle.getTitle());
            dataToPass.putString("url", selectedArticle.getUrl());
            dataToPass.putString("sectionName", selectedArticle.getSectionName());
            dataToPass.putLong("id", selectedArticle.getId());

            if(isTablet){
                ArticleDetail fragment = new ArticleDetail();
                fragment.setArguments(dataToPass);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentLocation, fragment)
                        .addToBackStack("")
                        .commit();
            }
            else//isPhone
            {
                Intent goToArticleDetail = new Intent(Guardian.this, Phone.class);
                goToArticleDetail.putExtras(dataToPass);
                startActivity(goToArticleDetail);
            }
        });
        listView.setOnItemLongClickListener((parent, view, pos, id)-> {
            Article selectedArticle = adapter.getItem(pos);
            ContentValues newRowValues = new ContentValues();

            newRowValues.put(COL_TITLE, selectedArticle.getTitle());
            newRowValues.put(COL_URL, selectedArticle.getUrl());
            newRowValues.put(COL_SECT, selectedArticle.getSectionName());

            //Now insert in the database:
            long newId = db.insert(TABLE_FAV, null, newRowValues);
            Toast.makeText(this, "Added to favourites", Toast.LENGTH_LONG).show();
            return true;
        });
        prefs = getSharedPreferences("Topic", Context.MODE_PRIVATE);
        String savedString = prefs.getString("topic", "Tesla");
        topic.setText(savedString);
    }

    protected void deleteArticle(Article article)
    {
        db.delete(MyOpener.TABLE_NAME, MyOpener.COL_ID + "= ?", new String[] {Long.toString(article.getId())});
    }
    private void loadDataFromDatabase()
    {
        //get a database connection:
        MyOpener dbOpener = new MyOpener(this);
        db = dbOpener.getWritableDatabase();
       // dbOpener.onUpgrade(db,MyOpener.VERSION_NUM, 2);

        // We want to get all of the columns. Look at MyOpener.java for the definitions:
        String [] columns = {MyOpener.COL_ID, COL_TITLE, COL_URL, COL_SECT};
        //query all the results from the database:
        results = db.query(false, MyOpener.TABLE_NAME, columns, null, null, null, null, null, null);

        //Now the results object has rows of results that match the query.
        //find the column indices:
        int titleColumnIndex = results.getColumnIndex(COL_TITLE);
        int urlColIndex = results.getColumnIndex(COL_URL);
        int sectColIndex = results.getColumnIndex(COL_SECT);
        int idColIndex = results.getColumnIndex(MyOpener.COL_ID);

        //iterate over the results, return true if there is a next item:
        while(results.moveToNext())
        {
            String title = results.getString(titleColumnIndex);
            String url = results.getString(urlColIndex);
            String sect = results.getString(sectColIndex);
            long id = results.getLong(idColIndex);

            //add the new Contact to the array list:
            articles.add(new Article(title, url, sect, id));
        }

        //At this point, the contactsList array has loaded every row from the cursor.
    }

    @Override
    public boolean onNavigationItemSelected( MenuItem item) {

        switch(item.getItemId())
        {

            case R.id.nasaImage:
                Intent go = new Intent(this, ImageOfTheDay.class);
                startActivity(go);
                break;
            case R.id.nasaEarth:
                Intent go1 = new Intent(this, ImageryDatabase.class);
                startActivity(go1);
                break;
            case R.id.BBC:
            Intent go2 = new Intent(this, BBCNewsReader.class);
            startActivity(go2);
            break;
            case R.id.help:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Instructions for How to Use the Interface")
                        .setMessage(getString(R.string.helpAlert) +
                                getString(R.string.helpAlert2) +
                                getString(R.string.helpAlert3))
                        .setPositiveButton("Close",(click,arg)->{}).create().show();
                break;
        }

        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        drawerLayout.closeDrawer(GravityCompat.START);
        // Toast.makeText(this, "NavigationDrawer: " + message, Toast.LENGTH_LONG).show();
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.nasaImage:
                Intent go = new Intent(this, ImageOfTheDay.class);
                startActivity(go);
                break;
            case R.id.nasaEarth:
                Intent go1 = new Intent(this, ImageryDatabase.class);
                startActivity(go1);
                break;
            case R.id.BBC:
                Intent go2 = new Intent(this, BBCNewsReader.class);
                startActivity(go2);
                break;
            case R.id.help:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Instructions for How to Use the Interface")
                        .setMessage(getString(R.string.helpAlert) +
                                getString(R.string.helpAlert2) +
                                getString(R.string.helpAlert3))
                        .setPositiveButton("Close",(click,arg)->{}).create().show();
                break;
        }
            return true;
    }

    private class Scraper extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream stream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"), 8);
                StringBuilder builder = new StringBuilder();
                String line = null;

                while ((line = reader.readLine()) != null) {
                    builder.append(line + "\n");
                }
                String result = builder.toString();
                JSONObject jObject = new JSONObject(result);
                JSONObject responseObj = jObject.getJSONObject("response");
                JSONArray array = responseObj.getJSONArray("results");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject articleObject = array.getJSONObject(i);
                    String title = articleObject.getString("webTitle");
                    String articleUrl = articleObject.getString("webUrl");
                    String sectionName = articleObject.getString("sectionName");
                    ContentValues newRowValues = new ContentValues();

                    newRowValues.put(COL_TITLE, title);
                    newRowValues.put(COL_URL, articleUrl);
                    newRowValues.put(COL_SECT, sectionName);
                    //Now insert in the database:
                    long newId = db.insert(MyOpener.TABLE_NAME, null, newRowValues);
                    Article article = new Article(title, articleUrl, sectionName,newId);
                    articles.add(article);
                }
                publishProgress(100);

            } catch (Exception e) {
                Log.i("Error", e.getMessage());
            }

            //publishProgress(100);
            return "done";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            if (articles.isEmpty())
                Snackbar.make(findViewById(R.id.list), "No Articles found", Snackbar.LENGTH_LONG).show();
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(100);
        }

        @Override
        protected void onPostExecute(String s) {
            adapter.notifyDataSetChanged();
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return articles.size();
        }

        @Override
        public Article getItem(int position) {
            return articles.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View newView = inflater.inflate(R.layout.article_layout, parent, false);
            TextView tView = newView.findViewById(R.id.tf);
            tView.setText((position+1)+". "+getItem(position).getTitle());
            return newView;
        }

        @Override
        public long getItemId(int position) {
            return (getItem(position).getId());
        }
    }
    private void saveSharedPrefs(String stringToSave)
    {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("topic", stringToSave);
        editor.commit();
    }

}
