package com.example.androidgroupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class BBC_Saved_Articles_List extends AppCompatActivity {


    private ArrayList<BBC_SavedArticle> list = new ArrayList<>();
    private SavedHeadlineAdapter myAdapter;
    SQLiteDatabase db;
    Cursor results;
    public static final String TITLE = "TITLE";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String LINK = "LINK";
    public static final String DATE = "DATE";
    SharedPreferences prefs = null;
    EditText typeField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bbc__saved__articles__list);
        loadDataFromDatabase();

        ListView savedArticles = findViewById(R.id.savedArticles);
        savedArticles.setAdapter( myAdapter = new SavedHeadlineAdapter());
        savedArticles.setOnItemLongClickListener((p, v, position, id)-> {
            BBC_SavedArticle selectedContact = list.get(position);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Remove?")
                    .setMessage("Remove from Saved Articles?")
                    .setPositiveButton("Delete", (click, arg) -> {
                        deleteContact(selectedContact);
                        list.remove(position);
                        myAdapter.notifyDataSetChanged();
                    })
                    .setNeutralButton("Cancel", (click, arg) -> {})
                    .create().show();
            return true;
        });

        savedArticles.setOnItemClickListener((p, v, position, id)-> {
            Bundle dataToPass = new Bundle();
            dataToPass.putString(TITLE, list.get(position).getTitle());
            dataToPass.putString(DESCRIPTION, list.get(position).getDescription());
            dataToPass.putString(LINK, list.get(position).getLink());
            dataToPass.putString(DATE, list.get(position).getDateOfArticle());

            Intent nextActivity = new Intent(BBC_Saved_Articles_List.this, BBC_SavedArticlesDetails.class);
            nextActivity.putExtras(dataToPass);
            startActivity(nextActivity); //make the transition
            Toast.makeText(this, "Saved article no. "+(position+1), Toast.LENGTH_LONG).show();
        });

        TextView favName = findViewById(R.id.savedArticlesTitle);
        prefs = getSharedPreferences("Name", Context.MODE_PRIVATE);
        String savedString = prefs.getString("name", "Bond") +"'s";
        typeField = findViewById(R.id.enterYourName);
        typeField.setText(savedString.substring(0,savedString.length()-2));
        favName.setText(savedString+" Saved Articles");


        EditText name =findViewById(R.id.enterYourName);
        Button enter =findViewById(R.id.enter);
        enter.setOnClickListener(click -> {
            String userGivenName = name.getText().toString();
            if(userGivenName.length()==0){
                Toast.makeText(this, "Please Enter Your Name", Toast.LENGTH_SHORT).show();
            }
            if(userGivenName!=null && userGivenName.length()!=0){
                favName.setText(userGivenName + "'s Saved Articles");
            }
            saveSharedPrefs( typeField.getText().toString());
        });
    }

    private void loadDataFromDatabase() {
        BBC_MyOpener dbOpener = new BBC_MyOpener(this);
        db = dbOpener.getWritableDatabase();

        String [] columns = {BBC_MyOpener.COL_ID, BBC_MyOpener.COL_TITLE, BBC_MyOpener.COL_DESCRIPTION, BBC_MyOpener.COL_LINK, BBC_MyOpener.COL_DATE};
        results = db.query(false, BBC_MyOpener.TABLE_NAME, columns, null, null, null, null, null, null);

        int idColIndex = results.getColumnIndex(BBC_MyOpener.COL_ID);
        int TitleColIndex = results.getColumnIndex(BBC_MyOpener.COL_TITLE);
        int DescriptionColIndex = results.getColumnIndex(BBC_MyOpener.COL_DESCRIPTION);
        int LinkColIndex = results.getColumnIndex(BBC_MyOpener.COL_LINK);
        int DateColIndex = results.getColumnIndex(BBC_MyOpener.COL_DATE);



        while(results.moveToNext())
        {
            long id = results.getLong(idColIndex);
            String title = results.getString(TitleColIndex);
            String description = results.getString(DescriptionColIndex);
            String link = results.getString(LinkColIndex);
            String date = results.getString(DateColIndex);

            BBC_SavedArticle loaded = new BBC_SavedArticle(title, description, link, date, id);
            //add the new Contact to the array list:
            list.add(loaded);
        }
    }

    protected void deleteContact(BBC_SavedArticle s)
    {
        db.delete(BBC_MyOpener.TABLE_NAME, BBC_MyOpener.COL_ID + "= ?", new String[] {Long.toString(s.getId())});
    }

    class SavedHeadlineAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public BBC_SavedArticle getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) { return getItem(position).getId(); }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();

            BBC_SavedArticle thisRow = getItem(position);
            //make a new row:
            View SavedheadlineView = inflater.inflate(R.layout.bbc_headline, parent, false);
            TextView titleView = SavedheadlineView.findViewById(R.id.bbcTitle);
            titleView.setText( thisRow.getTitle());

            return SavedheadlineView;
        }
    }

    private void saveSharedPrefs(String stringToSave)
    {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("name", stringToSave);
        editor.commit();
    }
}
