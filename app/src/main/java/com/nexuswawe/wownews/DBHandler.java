package com.nexuswawe.wownews;

import static android.icu.lang.UCharacter.JoiningGroup.PE;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.inputmethodservice.Keyboard;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {
    private Context context;
    public static final String DATABASE_NAME = "MySqlite.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "saved_posts";

    //Columns
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "titledb";
    public static final String COLUMN_IMAGE_URL = "image_urldb";
    public static final String COLUMN_DATE = "datedb";

    public static final String COLUMN_POST_URL = "post_urldb";


    public DBHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        // on below line we are creating
        // an sqlite query and we are
        // setting our column names
        // along with their data types.
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_IMAGE_URL + " TEXT,"
                + COLUMN_POST_URL + " TEXT,"
                + COLUMN_DATE + " TEXT)";

        // at last we are calling a exec sql
        // method to execute above sql query
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addNewPost(String titledb, String datedb, String post_urldb, String image_urldb) {

        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(COLUMN_TITLE, titledb);
        values.put(COLUMN_DATE, datedb);
        values.put(COLUMN_POST_URL, post_urldb);
        values.put(COLUMN_IMAGE_URL, image_urldb);


        // after adding all values we are passing
        // content values to our table.
        db.insert(TABLE_NAME, null, values);
        // at last we are closing our
        // database after adding database.
        db.close();

    }
    Cursor readNewPost() {
        String query = " SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
           cursor = db.rawQuery(query,null);
        }
        return cursor;


    }
    public void delete(String titledb, String datedb, String post_urldb, String image_urldb) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = COLUMN_TITLE + "=? AND " +
                COLUMN_DATE + "=? AND " +
                COLUMN_POST_URL + "=? AND " +
                COLUMN_IMAGE_URL + "=?";
        // Define the values to substitute the placeholders in the where clause
        String[] whereArgs = {titledb, datedb, post_urldb, image_urldb};
        // Execute the delete query on the database
        db.delete(TABLE_NAME, whereClause, whereArgs);
        // Close the database
        db.close();

    }
}
