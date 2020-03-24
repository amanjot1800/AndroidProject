package com.example.androidgroupproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbOpener extends SQLiteOpenHelper {

    protected final static String DATABASE_NAME = "NasaDB";
    protected final static int VERSION_NUM = 1;
    public final static String TABLE_NAME = "INFORMATION";
    public final static String URL = "URL";
    public final static String DATE = "DATE";
    public final static String HDURL = "HDURL";
    public final static String COL_ID = "_id";


    public DbOpener(Context ctx)
    {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + URL + " text,"
                + DATE + " text,"
                + HDURL  + " text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
