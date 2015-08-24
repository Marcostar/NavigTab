package com.example.maxx.navigtab.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.maxx.navigtab.model.Quotes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dzeko on 8/22/2015.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_EXTRAS = "extras";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_QUOTE = "QUOTE";

    private static final String DATABASE_NAME = "extras.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE = "create table "
            + TABLE_EXTRAS + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_QUOTE
            + " text not null);";

    public MySQLiteHelper(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXTRAS);
        onCreate(db);
    }

    public void addQuotes(Quotes quotes) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //values.put(COLUMN_ID, quotes.getID()); // Contact Name
        values.put(COLUMN_QUOTE, quotes.getQuote()); // Contact Phone

        // Inserting Row
        db.insert(TABLE_EXTRAS, null, values);
        db.close(); // Closing database connection
    }

    public List<Quotes> getAllQuotes() {
        List<Quotes> QuoteList = new ArrayList<Quotes>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_EXTRAS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Quotes quotes = new Quotes();
                quotes.setID(Integer.parseInt(cursor.getString(0)));
                quotes.setQuote(cursor.getString(1));
                // Adding contact to list
                QuoteList.add(quotes);
            } while (cursor.moveToNext());
        }
        db.close();

        // return contact list
        return QuoteList;
    }

}
