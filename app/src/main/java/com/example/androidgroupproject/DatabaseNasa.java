package com.example.androidgroupproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

    public class DatabaseNasa extends SQLiteOpenHelper {
     protected final static String DATABASE_NASA ="ImageryDataba";
        protected final static int VERSION_NUM = 5;
      public final static String IMAGERY_TABLE = "Imagery";
  public final static String COL_LAT ="laitude";
        public final static String COL_ID = "_id";
   public final static String COL_LONG ="Longitude";
   //public final static String COL_DATE ="date";
        public final static String COL_URL ="rr";

    public DatabaseNasa(Context ctx) {
        super(ctx, DATABASE_NASA, null, VERSION_NUM);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + IMAGERY_TABLE + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_LAT  + " text,"  + COL_LONG + " text," + COL_URL+  " text);");  // add or remove columns
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL( "DROP TABLE IF EXISTS " + IMAGERY_TABLE);
        onCreate(db);

    }
        //this function gets called if the database version on your device is higher than VERSION_NUM
        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {   //Drop the old table:
            db.execSQL( "DROP TABLE IF EXISTS " + IMAGERY_TABLE);

            //Create the new table:
            onCreate(db);
        }
}
