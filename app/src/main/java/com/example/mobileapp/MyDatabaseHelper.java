package com.example.mobileapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {


    private SQLiteDatabase database;
    private static final String DATABASE_NAME = "Expense.db";
    private static final String TABLE_NAME_TRIP = "trip";
    private static final String NAME_COLUMN = "expense_name";
    private static final String DESTINATION_COLUMN = "expense_destination";
    private static final String DATE_COLUMN = "expense_date";
    private static final String RISK_COLUMN = "expense_risk";
    private static final String DESCRIPTION_COLUMN = "expense_description";


    MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.database = getWritableDatabase();
    }

    private static String CREATE_TABLE_TRIP = String.format(
            "CREATE TABLE trip (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "expense_name TEXT, " +
                    "expense_destination TEXT, " +
                    "expense_date TEXT, " +
                    "expense_risk TEXT, " +
                    "expense_description TEXT" +
                    ");"
    );

    private static String CREATE_TABLE_EXPENSE = String.format(
            "CREATE TABLE expense (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "trip_id TEXT, " +
                    "Type TEXT, " +
                    "Amount TEXT, " +
                    "Time TEXT, " +
                    "FOREIGN KEY (trip_id) REFERENCES trip(_id)" +
                    ");"
    );



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TRIP);
        db.execSQL(CREATE_TABLE_EXPENSE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_TRIP);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_EXPENSE);
        onCreate(db);
    }



    public long AddExpense(String name, String destination, String date,String risk, String description) {
        ContentValues cv = new ContentValues();

        cv.put(NAME_COLUMN, name);
        cv.put(DESTINATION_COLUMN, destination);
        cv.put(DATE_COLUMN, date);
        cv.put(RISK_COLUMN, risk);
        cv.put(DESCRIPTION_COLUMN, description);

        long result = database.insert(TABLE_NAME_TRIP, null, cv);
        return result;

    }

    public long AddDetail(String type, String amount, String time){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("Type", type);
        cv.put("Amount", amount);
        cv.put("Time", time);

        long result = database.insert(CREATE_TABLE_EXPENSE, null, cv);
        return result;
    };


    Cursor getAllDetail(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from expense", null);
        return cursor;
    }




    Cursor getAllData(){
       SQLiteDatabase db = this.getWritableDatabase();
       Cursor cursor = db.rawQuery("Select * from trip", null);
       return cursor;
    }

    public void updateData(String id, String name, String destination, String date, String risk, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(NAME_COLUMN, name);
        cv.put(DESTINATION_COLUMN, destination);
        cv.put(DATE_COLUMN, date);
        cv.put(RISK_COLUMN, risk);
        cv.put(DESCRIPTION_COLUMN, description);

        db.update(TABLE_NAME_TRIP, cv, "_id=?", new String[]{id});



    }
    public void deleteRow(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME_TRIP, "_id=?", new String[]{id});


    }
    void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME_TRIP);
    }




}






