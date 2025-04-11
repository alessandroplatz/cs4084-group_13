package com.example.cs4084_group_13;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    private Context context;

    private static final String DB_Name =   "Collections.db";
    private static final int DB_VERSION= 1;
    private static final String ID_COL = "Collection_ID";
    private static final String COL_1 = "Name";
    private static final String TABLE_NAME = "Collections";

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
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void add(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values =new ContentValues();

        values.put(COL_1, name);
        db.insert(TABLE_NAME,null,values);
    }

    public ArrayList<Collections> getCollections() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Collections> collecs = new ArrayList<>();
        Cursor cursor =db.rawQuery("Select * from " + TABLE_NAME,null);
        if (cursor.moveToFirst()) {
            do {
                collecs.add(new Collections(cursor.getString(1)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return collecs;
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

    Cursor readAllData() {
        String query = "Select * from " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db!=null) {
            cursor = db.rawQuery(query,null);

        }
        return cursor;
    }



}
