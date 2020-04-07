package com.example.androidgroupproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbOpener extends SQLiteOpenHelper {

    /**
     * The database name
     */
    protected final static String DATABASE_NAME = "NasaDB";
    /**
     * The version number for the database
     */
    protected final static int VERSION_NUM = 2;
    /**
     * The table name in the database
     */
    public final static String TABLE_NAME = "INFORMATION";
    /**
     * column for the url
     */
    public final static String URL = "URL";
    /**
     * The column for the date
     */
    public final static String DATE = "DATE";
    /**
     * The column for the hdurl
     */
    public final static String HDURL = "HDURL";
    /**
     * The column for the column_id
     */
    public final static String COL_ID = "_id";


    /**
     * The default constructor
     * @param ctx current contect
     */
    public DbOpener(Context ctx)
    {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    /**
     * Creates the database
     * @param db the database for the operation
     */
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + URL + " text,"
                + DATE + " text,"
                + HDURL  + " text);");
    }

    /**
     * Upgrades the database
     * @param db the database for the operation
     * @param oldVersion oldversion of database
     * @param newVersion new version of database
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    /**
     * Downgrades the database
     * @param db the database for the operation
     * @param oldVersion oldversion of database
     * @param newVersion new version of database
     */
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
