package com.karimapps.mliving.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class AppDataBase {
    private Context context;
    private SQLiteDatabase sqLiteDatabase;
    private Db Db;
    int total;

    public AppDataBase(Context context) {
        this.context = context;
    }

    /*DATA BASE NAME AND VERSION , CHANGE VERSION WHILE CHNAGE IN TABLE*/
    private static final String DATA_BASE_NAME = "awais.db";
    private static final int DATA_BASE_VERSION = 1;

    /*  1 table name */
    private static final String TABLE_NAME_REGISTER = "myreminders";


    private static final String DB_COLUMN_ID = "_id";
    private static final String DB_COLUMN_TYPE = "type";
    private static final String DB_COLUMN_TITLE = "title";
    private static final String DB_COLUMN_CONTENT = "content";
    private static final String DB_COLUMN_TIME = "time";
    private static final String DB_COLUMN_FREQUENCY = "frequency";


    /* 3to drop a table*/
    /**/
    private static final String DROP_TABLE_REGISTER = "DROP TABLE IF EXISTS " + TABLE_NAME_REGISTER;

    /* 4 to creta a table*/


    private static final String CREATE_TABLE_REGISTER =
            "CREATE TABLE " + TABLE_NAME_REGISTER + " (" +
                    DB_COLUMN_ID + " INTEGER PRIMARY KEY," +
                    DB_COLUMN_TYPE + " TEXT," +
                    DB_COLUMN_TITLE + " TEXT," +
                    DB_COLUMN_CONTENT + " TEXT," +
                    DB_COLUMN_TIME + " TEXT," +
                    DB_COLUMN_FREQUENCY + " TEXT)";


    /*5 th*/
    public long insertReminder(String type, String title, String content, String time, String freq) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DB_COLUMN_TYPE, type);
        contentValues.put(DB_COLUMN_TITLE, title);
        contentValues.put(DB_COLUMN_CONTENT, content);
        contentValues.put(DB_COLUMN_TIME, time);
        contentValues.put(DB_COLUMN_FREQUENCY, freq);


        long insertedId = sqLiteDatabase.insert(TABLE_NAME_REGISTER, null, contentValues);
        return insertedId;
    }


    public ArrayList<MyReminders> deleteItemById(String ticket_id) {

        ArrayList<MyReminders> myList = new ArrayList<>();
        try {

            Cursor cursor = sqLiteDatabase.rawQuery("DELETE  FROM " + TABLE_NAME_REGISTER + " WHERE    DB_COLUMN_ID =? ", new String[]{ticket_id});


        } catch (Exception e) {
            e.printStackTrace();
        }
        return myList;
    }

    public void delete(String value) {
        sqLiteDatabase.delete(TABLE_NAME_REGISTER, DB_COLUMN_ID + "=?", new String[]{String.valueOf(value)});
    }


    public ArrayList<MyReminders> getReminderByTitle(String ticket_id) {

        ArrayList<MyReminders> myList = new ArrayList<>();
        try {


            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME_REGISTER + " WHERE    title =? ", new String[]{ticket_id});


//            Cursor cursor = sqLiteDatabase.query(TABLE_NAME_REGISTER, null, COLUMN_TICKET_ID, new String[]{ticket_id}, null, null, null);
            while (cursor.moveToNext()) {

                String id = cursor.getString(cursor.getColumnIndex(DB_COLUMN_ID));
                String type = cursor.getString(cursor.getColumnIndex(DB_COLUMN_TYPE));
                String title = cursor.getString(cursor.getColumnIndex(DB_COLUMN_TITLE));
                String content = cursor.getString(cursor.getColumnIndex(DB_COLUMN_CONTENT));
                String time = cursor.getString(cursor.getColumnIndex(DB_COLUMN_TIME));
                String freq = cursor.getString(cursor.getColumnIndex(DB_COLUMN_FREQUENCY));


                MyReminders users = new MyReminders(id, type, title, content, time, freq);
                myList.add(users);
            }
        } catch (Exception e) {
        }
        return myList;
    }

    public ArrayList<MyReminders> getAllReminders() {

        ArrayList<MyReminders> myList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME_REGISTER, null, null, null, null, null, null);
        while (cursor.moveToNext()) {

            String id = cursor.getString(cursor.getColumnIndex(DB_COLUMN_ID));
            String type = cursor.getString(cursor.getColumnIndex(DB_COLUMN_TYPE));
            String title = cursor.getString(cursor.getColumnIndex(DB_COLUMN_TITLE));
            String content = cursor.getString(cursor.getColumnIndex(DB_COLUMN_CONTENT));
            String time = cursor.getString(cursor.getColumnIndex(DB_COLUMN_TIME));
            String freq = cursor.getString(cursor.getColumnIndex(DB_COLUMN_FREQUENCY));


            MyReminders users = new MyReminders(id, type, title, content, time, freq);
            myList.add(users);
        }

        return myList;
    }


//    public ArrayList<MyReminders> getAllUsers() {
//
//        ArrayList<MyReminders> myList = new ArrayList<>();
//        Cursor cursor = sqLiteDatabase.query(TABLE_NAME_REGISTER, null, null, null, null, null, null);
//        while (cursor.moveToNext()) {
//
//            String id = cursor.getString(cursor.getColumnIndex(COLUMN_TICKET_ID));
//            String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
//            String sku = cursor.getString(cursor.getColumnIndex(COLUMN_SKU));
//            String slug = cursor.getString(cursor.getColumnIndex(COLUMN_SLUG));
//            String descr = cursor.getString(cursor.getColumnIndex(COLUMN_DESCR));
//            String cover = cursor.getString(cursor.getColumnIndex(COLUMN_COVER));
//            String qty = cursor.getString(cursor.getColumnIndex(COLUMN_IS_QTY));
//            String price = cursor.getString(cursor.getColumnIndex(COLUMN_PRICE));
//            String status = cursor.getString(cursor.getColumnIndex(COLUMN_STATUS));
//            String weight = cursor.getString(cursor.getColumnIndex(COLUMN_WEIGHT));
//            String mass_unit = cursor.getString(cursor.getColumnIndex(COLUMN_MASS_UNIT));
//            String sale_price = cursor.getString(cursor.getColumnIndex(COLUMN_SALE_PRICE));
//            String brand_id = cursor.getString(cursor.getColumnIndex(COLUMN_BRAND_ID));
//
//
//            TicketsLocal users = new TicketsLocal(id, name, sku, slug, descr, cover, qty, price, status, weight, mass_unit, sale_price, brand_id);
//            myList.add(users);
//        }
//
//        return myList;
//    }


    public AppDataBase open() throws android.database.SQLException {
        Db = new Db(context);
        sqLiteDatabase = Db.getWritableDatabase();
        return AppDataBase.this;
    }

    public void close() {
        Db.close();
    }


    private class Db extends SQLiteOpenHelper {

        public Db(Context context) {
            super(context, DATA_BASE_NAME, null, DATA_BASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(CREATE_TABLE_REGISTER);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL(DROP_TABLE_REGISTER);
        }
    }

}
