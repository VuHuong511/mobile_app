package com.example.mobileapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private SQLiteDatabase database;
    private static final String DATABASE_NAME = "Expense.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "expense_management";
    private static final String ID_COLUMN = "id";
    private static final String NAME_COLUMN = "expense_name";
    private static final String DESTINATION_COLUMN = "expense_destination";
    private static final String DATE_COLUMN = "expense_date";
    private static final String RISK_COLUMN = "expense_risk";
    private static final String DESCRIPTION_COLUMN = "expense_description";


    MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.database = getWritableDatabase();
    }

    private static String CREATE_TABLE = String.format(
            "CREATE TABLE expense_management (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "expense_name TEXT, " +
                    "expense_destination TEXT, " +
                    "expense_date TEXT, " +
                    "expense_risk TEXT, " +
                    "expense_description TEXT" +
                    ");"
    );

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }



    public long AddExpense(String name, String destination, String date,String risk, String description) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(NAME_COLUMN, name);
        contentValues.put(DESTINATION_COLUMN, destination);
        contentValues.put(DATE_COLUMN, date);
        contentValues.put(RISK_COLUMN, risk);
        contentValues.put(DESCRIPTION_COLUMN, description);


        long result = database.insert(TABLE_NAME, null, contentValues);
        return result;

    }

    Cursor getAllData(){
       SQLiteDatabase db = this.getWritableDatabase();
       Cursor cursor = db.rawQuery("Select * from expense_management", null);
       return cursor;
    }

    public void updateData(String id, String name, String destination, String date, String risk, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(NAME_COLUMN, name);
        contentValues.put(DESTINATION_COLUMN, destination);
        contentValues.put(DATE_COLUMN, date);
        contentValues.put(RISK_COLUMN, risk);
        contentValues.put(DESCRIPTION_COLUMN, description);

        db.update(TABLE_NAME, contentValues, "_id=?", new String[]{id});



    }
    public void deleteRow(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "_id=?", new String[]{id});


    }
    void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }




}






