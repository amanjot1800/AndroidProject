package com.example.androidgroupproject;

import android.content.ContentValues;
import android.content.Intent;
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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Guardian extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private ArrayList<Article> articles = new ArrayList<>();
    private MyAdapter adapter;
    private SQLiteDatabase db;
    Cursor results;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardian);
        loadDataFromDatabase();
        Toolbar tBar = findViewById(R.id.toolBar);
        setSupportActionBar(tBar);
        DrawerLayout drawer = findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer, tBar, R.string.open,R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView nv = findViewById(R.id.navigation);
        nv.setNavigationItemSelectedListener(this);

        EditText topic = findViewById(R.id.search);
        Button searchButton = findViewById(R.id.srchBtn);
        ListView listView = findViewById(R.id.list);
        listView.setAdapter(adapter = new MyAdapter());
        progressBar = findViewById(R.id.progBar);
        progressBar.setVisibility(View.VISIBLE);

        searchButton.setOnClickListener(cl -> {
            articles.clear();
            adapter.notifyDataSetChanged();
            Scraper thread = new Scraper();
           // if (!topic.getText().toString().isEmpty())
                thread.execute("https://content.guardianapis.com/search?api-key=1fb36b70-1588-4259-b703-2570ea1fac6a&q="+topic.getText().toString());
//            else
//                Snackbar.make(null, "Please enter a Topic to search", Snackbar.LENGTH_LONG).show();
        });

        //listView.setOnItemLongClickListener();
        listView.setOnItemClickListener((parent, view, pos, id)->{
            Intent goToArticleDetail = new Intent(Guardian.this, ArticleDetail.class);
            Article selectedArticle = articles.get(pos);
            goToArticleDetail.putExtra("title",selectedArticle.getTitle());
            goToArticleDetail.putExtra("url", selectedArticle.getUrl());
            goToArticleDetail.putExtra("sectionName", selectedArticle.getSectionName());
            goToArticleDetail.putExtra("id", selectedArticle.getId());
            startActivity(goToArticleDetail);
        });
//        listView.setOnItemLongClickListener((parent, view, pos, id)-> {
//            Article selectedArticle = adapter.getItem(pos);
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setTitle("Do You want to Delete This?")
//                    .setMessage("The selected row is: "+pos + "\tThe database id id:"+id)
//                    .setPositiveButton("Confirm",(click,arg)->{
//                        articles.remove(pos);adapter.notifyDataSetChanged();
//                        deleteArticle(selectedArticle);})
//                    .setNegativeButton("",(click,arg)->{}).create().show();
//            //           showContact(pos);
//            return true;
//        });
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


        // We want to get all of the columns. Look at MyOpener.java for the definitions:
        String [] columns = {MyOpener.COL_ID, MyOpener.COL_TITLE, MyOpener.COL_URL, MyOpener.COL_SECT};
        //query all the results from the database:
        results = db.query(false, MyOpener.TABLE_NAME, columns, null, null, null, null, null, null);

        //Now the results object has rows of results that match the query.
        //find the column indices:
        int titleColumnIndex = results.getColumnIndex(MyOpener.COL_TITLE);
        int urlColIndex = results.getColumnIndex(MyOpener.COL_URL);
        int sectColIndex = results.getColumnIndex(MyOpener.COL_SECT);
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
            case R.id.guardian:
                Intent goToGuardian = new Intent(Guardian.this, Guardian.class);
                startActivity(goToGuardian);
                break;
            case R.id.nasaImage:
//                Intent goToWeather = new Intent(TestToolBar.this, WeatherForecast.class);
//                startActivity(goToWeather);
                break;
            case R.id.nasaEarth:
//                Intent goToChat = new Intent(TestToolBar.this, chatRoomActivity.class);
//                startActivity(goToChat);
                break;
            case R.id.BBC:
//                Intent goToChat = new Intent(TestToolBar.this, chatRoomActivity.class);
//                startActivity(goToChat);
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
            case R.id.guardian:
                Intent goToGuardian = new Intent(Guardian.this, Guardian.class);
                startActivity(goToGuardian);
                break;
            case R.id.nasaImage:
//                Intent goToWeather = new Intent(TestToolBar.this, WeatherForecast.class);
//                startActivity(goToWeather);
                break;
            case R.id.nasaEarth:
//                Intent goToChat = new Intent(TestToolBar.this, chatRoomActivity.class);
//                startActivity(goToChat);
                break;
            case R.id.BBC:
//                Intent goToChat = new Intent(TestToolBar.this, chatRoomActivity.class);
//                startActivity(goToChat);
                break;}
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

                    newRowValues.put(MyOpener.COL_TITLE, title);
                    newRowValues.put(MyOpener.COL_URL, articleUrl);
                    newRowValues.put(MyOpener.COL_SECT, sectionName);
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

}