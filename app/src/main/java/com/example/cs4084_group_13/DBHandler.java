package com.example.cs4084_group_13;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    private Context context;

    private static final String DB_Name =   "Collections.db";
    private static final int DB_VERSION= 1;
    private static final String ID_COL = "Collection_ID";
    private static final String COL_1 = "Name";
    private static final String TABLE_NAME = "Collections";

    private static final String ID_COL2 = "Collection_ID";
    private static final String COL_1_2 = "Front";
    private static final String COL_2 = "Back";
    private static final String FK_Col = "Collection_ID";
    private static final String TABLE_NAME_2 = "Flashcards";



    public DBHandler (Context context) {
    super(context,DB_Name,null,DB_VERSION);
    this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "Create table " + TABLE_NAME + " ("
        +ID_COL + " Integer Primary Key AUTOINCREMENT, "
        + COL_1 + " Text)";
        db.execSQL(query);
        //table 2

        String query2 = "Create table Flashcards(" +
                "Flashcard_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Front Text," +
                "Back Text," +
                "Collection_ID INTEGER," +
                "FOREIGN KEY(Collection_ID) REFERENCES Collections(Collection_ID))";
        db.execSQL(query2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_2);
        onCreate(sqLiteDatabase);
    }


    public void addCollection(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COL_1,name);
        long result = db.insert(TABLE_NAME,null,cv);
        if (result == -1) {
            Toast.makeText(context,"Failed to add",Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(context,"Added Succesfully",Toast.LENGTH_SHORT).show();
        }
    }

    public void addFlashcard(String front,String back,int colID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("Front",front);
        cv.put("Back",back);
        cv.put("Collection_ID",colID);

        long result = db.insert(TABLE_NAME_2,null,cv);
        //put logic later to maybe handle redirect
        if (result == -1) {
            Toast.makeText(context, "Failed to add", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context,"Added Succesfully",Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllData() {
        String query = "Select * from " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db!=null) {
            cursor = db.rawQuery(query,null);

        }
        return cursor;
    }

    Cursor readAllDataFlashcards(int id) {
        SQLiteDatabase db = null;
                Cursor cursor = null;
        try {
            String query = "Select  * from Flashcards Where Collection_ID = ?";
            db = this.getReadableDatabase();
            if (db != null) {
                cursor = db.rawQuery(query, new String[]{String.valueOf(id)});
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("DatabaseError", "Error reading data: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return cursor;
    }



}
