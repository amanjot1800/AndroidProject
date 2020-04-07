package com.example.androidgroupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
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

public class BBCSavedArticlesList extends AppCompatActivity {


    private ArrayList<BBCSavedArticle> list = new ArrayList<>();
    private SavedHeadlineAdapter myAdapter;
    SQLiteDatabase db;
    Cursor results;
    public static final String TITLE = "TITLE";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String LINK = "LINK";
    public static final String DATE = "DATE";
    SharedPreferences prefs = null;
    EditText typeField;

    /**
     * This gets called when user clicks on the saved article list. It creates the ListView and populates with the articles
     * that were saved by the user previously
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bbc__saved__articles__list);
        loadDataFromDatabase();

        ListView savedArticles = findViewById(R.id.savedArticles);
        savedArticles.setAdapter( myAdapter = new SavedHeadlineAdapter());
        savedArticles.setOnItemLongClickListener((p, v, position, id)-> {
            BBCSavedArticle selectedContact = list.get(position);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle(R.string.Remove)
                    .setMessage(R.string.RemoveFromSavedArticles)
                    .setPositiveButton(R.string.delete, (click, arg) -> {
                        deleteContact(selectedContact);
                        list.remove(position);
                        myAdapter.notifyDataSetChanged();
                    })
                    .setNeutralButton(R.string.cancel, (click, arg) -> {})
                    .create().show();
            return true;
        });

        savedArticles.setOnItemClickListener((p, v, position, id)-> {
            Bundle dataToPass = new Bundle();
            dataToPass.putString(TITLE, list.get(position).getTitle());
            dataToPass.putString(DESCRIPTION, list.get(position).getDescription());
            dataToPass.putString(LINK, list.get(position).getLink());
            dataToPass.putString(DATE, list.get(position).getDateOfArticle());

            Intent nextActivity = new Intent(BBCSavedArticlesList.this, BBCSavedArticlesDetails.class);
            nextActivity.putExtras(dataToPass);
            startActivity(nextActivity); //make the transition
            Toast.makeText(this, "Saved Article no. "+(position+1), Toast.LENGTH_LONG).show();
        });

        TextView favName = findViewById(R.id.savedArticlesTitle);
        prefs = getSharedPreferences("Name", Context.MODE_PRIVATE);
        String savedString = prefs.getString("name", "Bond") +"'s";
        typeField = findViewById(R.id.enterYourName);
        typeField.setText(savedString.substring(0,savedString.length()-2));
        favName.setText(savedString+ " Saved Article");


        EditText name =findViewById(R.id.enterYourName);
        Button enter =findViewById(R.id.enter);
        enter.setOnClickListener(click -> {
            String userGivenName = name.getText().toString();
            if(userGivenName.length()==0){
                Toast.makeText(this, R.string.pleaseEnterYourName, Toast.LENGTH_SHORT).show();
            }
            if(userGivenName!=null && userGivenName.length()!=0){
                favName.setText(userGivenName + "'s "+ "Saved Article");
            }
            saveSharedPrefs( typeField.getText().toString());
        });
    }

    /**
     * This method is called at the very beginning whenever this class is called.
     * It gets all the articles from the database and add it to the ListView and hence it populates the ListView.
     */
    private void loadDataFromDatabase() {
        BBCMyOpener dbOpener = new BBCMyOpener(this);
        db = dbOpener.getWritableDatabase();

        String [] columns = {BBCMyOpener.COL_ID, BBCMyOpener.COL_TITLE, BBCMyOpener.COL_DESCRIPTION, BBCMyOpener.COL_LINK, BBCMyOpener.COL_DATE};
        results = db.query(false, BBCMyOpener.TABLE_NAME, columns, null, null, null, null, null, null);

        int idColIndex = results.getColumnIndex(BBCMyOpener.COL_ID);
        int TitleColIndex = results.getColumnIndex(BBCMyOpener.COL_TITLE);
        int DescriptionColIndex = results.getColumnIndex(BBCMyOpener.COL_DESCRIPTION);
        int LinkColIndex = results.getColumnIndex(BBCMyOpener.COL_LINK);
        int DateColIndex = results.getColumnIndex(BBCMyOpener.COL_DATE);


        while(results.moveToNext())
        {
            long id = results.getLong(idColIndex);
            String title = results.getString(TitleColIndex);
            String description = results.getString(DescriptionColIndex);
            String link = results.getString(LinkColIndex);
            String date = results.getString(DateColIndex);

            BBCSavedArticle loaded = new BBCSavedArticle(title, description, link, date, id);
            //add the new Contact to the array list:
            list.add(loaded);
        }
    }


    /**
     * This method get called whenever user click on the delete button to remove the
     * article from the saved article list. It also deletes it from the database.
     *
     * @param s
     */
    protected void deleteContact(BBCSavedArticle s)
    {
        db.delete(BBCMyOpener.TABLE_NAME, BBCMyOpener.COL_ID + "= ?", new String[] {Long.toString(s.getId())});
    }

    class SavedHeadlineAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public BBCSavedArticle getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) { return getItem(position).getId(); }

        /**
         *This set the ListView to only show the title of the Article.
         * @param position
         * @param convertView
         * @param parent
         * @return
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();

            BBCSavedArticle thisRow = getItem(position);
            //make a new row:
            View SavedheadlineView = inflater.inflate(R.layout.bbc_headline, parent, false);
            TextView titleView = SavedheadlineView.findViewById(R.id.bbcTitle);
            titleView.setText( thisRow.getTitle());

            return SavedheadlineView;
        }
    }

    /**
     * This method is called to saved the string passed to the edit text to the file in the device
     * so that the string is already present there whenever user restart the app.
     * @param stringToSave
     */
    private void saveSharedPrefs(String stringToSave)
    {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("name", stringToSave);
        editor.commit();
    }
}
