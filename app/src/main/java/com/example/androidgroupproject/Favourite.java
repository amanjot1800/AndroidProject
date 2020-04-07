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
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.androidgroupproject.MyOpener.COL_SECT;
import static com.example.androidgroupproject.MyOpener.COL_TITLE;
import static com.example.androidgroupproject.MyOpener.COL_URL;
import static com.example.androidgroupproject.MyOpener.TABLE_FAV;

public class Favourite extends AppCompatActivity {
    private ArrayList<Article> articles = new ArrayList<>();
    private MyAdapter adapter;
    private SQLiteDatabase db;
    Cursor results;

    /**
     * This method inflates a listView and populating it with the data in the favourite article table
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        boolean isTablet = findViewById(R.id.fragmentLocation)!=null;

        ListView listView = findViewById(R.id.listV);
        listView.setAdapter(adapter = new MyAdapter());
        loadDataFromDatabase();

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
                Intent goToArticleDetail = new Intent(Favourite.this, Phone.class);
                goToArticleDetail.putExtras(dataToPass);
                startActivity(goToArticleDetail);
            }
        });

        listView.setOnItemLongClickListener((parent, view, pos, id)-> {
            Article selectedArticle = adapter.getItem(pos);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Do You want to Delete This?")
                    .setMessage("The selected article is: "+articles.get(pos).getTitle())
                    .setPositiveButton("Confirm",(click,arg)->{
                        articles.remove(pos);adapter.notifyDataSetChanged();
                        deleteArticle(selectedArticle);})
                    .setNegativeButton("Cancel",(click,arg)->{adapter.notifyDataSetChanged();}).create().show();
            //           showContact(pos);
            return true;
        });
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

    /**
     * This method is called in the onCreate method, which loads all the articles from the table in database into the listView.
     */
    private void loadDataFromDatabase()
    {
        //get a database connection:
        MyOpener dbOpener = new MyOpener(this);
        db = dbOpener.getWritableDatabase();

        // We want to get all of the columns. Look at MyOpener.java for the definitions:
        String [] columns = {MyOpener.COL_ID, COL_TITLE, COL_URL, COL_SECT};
        //query all the results from the database:
        results = db.query(false, TABLE_FAV, columns, null, null, null, null, null, null);

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

    /**This method takes in an article and deletes it from the current database
     * @param article object of type article which is to be deleted
     */
    protected void deleteArticle(Article article)
    {
        db.delete(TABLE_FAV, MyOpener.COL_ID + "= ?", new String[] {Long.toString(article.getId())});
    }
}
