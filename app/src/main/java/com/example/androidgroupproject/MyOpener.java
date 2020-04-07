package com.example.androidgroupproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpener extends SQLiteOpenHelper {

    protected final static String DATABASE_NAME = "ArticleDB";
    protected final static int VERSION_NUM = 1;
    public final static String TABLE_NAME = "ARTICLE";
    public final static String TABLE_FAV = "FAVOURITE";
    public final static String COL_TITLE = "TITLE";
    public final static String COL_URL = "URL";
    public final static String COL_SECT = "SECTION_NAME";
    public final static String COL_ID = "_id";

    public MyOpener(Context c) {
        super(c, DATABASE_NAME, null, VERSION_NUM);
    }

    ;

    /**
     * 2 tables are created and initialised using dbQuery
     * @param db The database used
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_TITLE + " text,"
                + COL_URL + " text,"
                + COL_SECT + " text);");

//        db.execSQL("CREATE TABLE " + TABLE_FAV + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
//                + COL_TITLE + " text,"
//                + COL_URL + " text,"
//                + COL_SECT + " text);");
    }

    /**
     * This method gets called when the newVersion is greater than the oldVersion
     * @param db The database used
     * @param oldVersion int oldVersion
     * @param newVersion int NewNumber
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_FAV);
        onCreate(db);
    }

}
