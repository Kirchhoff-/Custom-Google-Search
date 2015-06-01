package com.example.kirchhoff.googletest.DB;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

//Class for work with Database
public class DBhelper {
    public final static String TAG = "DBHelper :";
    public static final String EMP_ID = "id";
    public static final String EMP_TITLE = "title";
    public static final String EMP_PHOTO = "photo";

    private static DatabaseHelper mDbHelper;
    private static SQLiteDatabase mDb;

    private static final String DATABASE_NAME = "FavoriteImageDB.db";
    private static final int DATABASE_VERSION = 1;

    private static final String FAVORITE_TABLE = "Favorite";

    private static final String CREATE_FAVORITE_TABLE = "create table "
            + FAVORITE_TABLE + " (" + EMP_ID
            + " integer primary key autoincrement, " + EMP_PHOTO
            + " text, " + EMP_TITLE + " text  ); ";


    private static Context mCtx;

    // create an empty array list with an initial capacity
    static  ArrayList<ImageItem> ImageList = new ArrayList<ImageItem>();

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_FAVORITE_TABLE);
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + FAVORITE_TABLE);
            onCreate(db);
        }
    }

    public void reset() {
        mDbHelper.onUpgrade(this.mDb, 1, 1);
    }

    public  DBhelper(Context ctx) {
        mCtx = ctx;
        mDbHelper = new DatabaseHelper(mCtx);
    }

     public static void open(Context ctx) throws SQLException {
        mCtx = ctx;
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();

    }

    public static void close() {
        mDbHelper.close();
    }

    //Put record to database
    public static void insertFavorite(ImageItem item) {

        ContentValues cv = new ContentValues();
        cv.put(EMP_PHOTO,item.getUrl());
        cv.put(EMP_TITLE,item.getTitle());
        mDb.insert(FAVORITE_TABLE, null, cv);
        Log.d(TAG, "Insert CV ");
    }


    //Get list of favorite search
    public static ArrayList<ImageItem> getAllFavorite() throws SQLException {
        Cursor cur = mDb.query(true, FAVORITE_TABLE, new String[] { EMP_PHOTO,
                EMP_TITLE }, null, null, null, null, null, null);
        if (cur.moveToFirst()) {
            do {
                String url = cur.getString(cur.getColumnIndex(EMP_PHOTO));
                String title = cur.getString(cur.getColumnIndex(EMP_TITLE));
                ImageList
                        .add(new ImageItem(url, title));
            } while (cur.moveToNext());
        }
        return ImageList;
    }

    //Return size of Table
    public static int getSize(){
        Cursor c = mDb.query(true, FAVORITE_TABLE, new String[] { EMP_PHOTO,
                EMP_TITLE }, null, null, null, null, null, null);
       return  c.getCount();
    }
}


