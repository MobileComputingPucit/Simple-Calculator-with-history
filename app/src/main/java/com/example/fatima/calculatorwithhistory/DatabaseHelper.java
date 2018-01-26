package com.example.fatima.calculatorwithhistory;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.TextView;

public class DatabaseHelper extends SQLiteOpenHelper{

    public static final String dbname = "Calculator.db";
    public static final String table_name = "history_table";
    public static final String id="ID";
    public static final String first_input = "FIRST";
    public static final String sec_input = "SECOND";
    public static final String operation = "OPERATION";
    public static final String result = "RESULT";

    public DatabaseHelper(Context context) {
        super(context, dbname, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + table_name + " (" +
                id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                first_input + " TEXT NOT NULL, " +
                sec_input + " TEXT NOT NULL, " +
                operation + " TEXT NOT NULL, " +
                result + " INTEGER NOT NULL);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXSISTS" + table_name);
        onCreate(db);

    }

    public boolean insertData (double input1, double input2, String op, double ans)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(first_input,input1);
        contentValues.put(sec_input,input2);
        contentValues.put(operation,op);
        contentValues.put(result,ans);
        long inserted = db.insert(table_name,null,contentValues);

        if (inserted == -1)
        {
            return false;
        }
        return true;
    }

    public Cursor retrieveData()
    {
        SQLiteDatabase db = getWritableDatabase();
        Cursor data = db.rawQuery("select * from "+ table_name,null);
        return data;
    }

    public void deleteData()
    {
        SQLiteDatabase db = getWritableDatabase();
        db.rawQuery("delete from "+ table_name,null);

    }


}