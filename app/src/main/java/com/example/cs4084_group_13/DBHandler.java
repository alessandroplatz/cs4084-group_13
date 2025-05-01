package com.example.cs4084_group_13;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;
import com.opencsv.CSVReader;

import com.google.android.material.tabs.TabLayout;
import com.opencsv.exceptions.CsvValidationException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Array;
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
    private static final String TABLE_NAME_3 = "Test_History";
    private static final String TABLE_NAME_4 = "Test_Mistakes";



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
                "FOREIGN KEY(Collection_ID) REFERENCES Collections(Collection_ID) ON DELETE CASCADE)";
        db.execSQL(query2);

        String query3 = "Create table Test_History(" +
                "Test_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Score INTEGER," +
                "Date_Of_Test DATETIME," +
                "Total_Questions INTEGER," +
                "Is_Pop TINYINT," +
                "Collection_ID INTEGER," +
                "FOREIGN KEY(Collection_ID) REFERENCES Collections(Collection_ID) ON DELETE CASCADE)";
        db.execSQL(query3);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_2);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
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
        db.close();
    }

    public void deleteCollection(int colID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        int result = db.delete(TABLE_NAME,"Collection_ID = ?",new String[]{String.valueOf(colID)});

        if (result == 0) {
            Toast.makeText(context, "Failed to Delete", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context,"Deleted Succesfully",Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    public void editCollection(String name,int colID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COL_1,name);
        int result = db.update(TABLE_NAME,cv,"Collection_ID = ?",new String[] {String.valueOf(colID)});

        if (result == -1) {
            Toast.makeText(context, "Failed to Update", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context,"Updated Succesfully",Toast.LENGTH_SHORT).show();
        }
        db.close();
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
        db.close();
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

    Cursor readAllTests() {
        String query = "Select * from Test_History";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db!=null) {
            cursor = db.rawQuery(query,null);

        }

        return cursor;
    }

    @SuppressLint("Range")
    public String GetCollectionName(int colID) {
        if (colID == 0)
            return "Pop Quiz";
        SQLiteDatabase db = this.getReadableDatabase();
        String collectionName = null;
        String query = "SELECT " + COL_1 + " FROM " + TABLE_NAME + " WHERE " + ID_COL + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(colID)});
        if (cursor != null && cursor.moveToFirst()) {
                collectionName = cursor.getString(cursor.getColumnIndex(COL_1));
        }
        if (cursor != null) {
            cursor.close();
        }
        db.close();


        return collectionName;
    }

    public Cursor readAllDataFlashcards(int id) {
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

    public void updateFlashcard(int flashcardID,String front,String back,int colID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("Front",front);
        cv.put("Back",back);
        cv.put("Collection_ID",colID);

        int result = db.update(TABLE_NAME_2,cv,"Flashcard_ID = ?",new String[]{String.valueOf(flashcardID)});

        if (result == -1) {
            Toast.makeText(context, "Failed to Update", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context,"Updated Succesfully",Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    public void deleteFlashcard(int flashcardID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        int result = db.delete(TABLE_NAME_2,"Flashcard_ID = ?",new String[]{String.valueOf(flashcardID)});

        if (result == 0) {
            Toast.makeText(context, "Failed to Delete", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context,"Deleted Succesfully",Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    public ArrayList<Flashcard> getFlashcardsForTest(int colid) {
        ArrayList<Flashcard> flashcards = new ArrayList<>();
        String query = "Select * " +
                "from Flashcards " +
                "where Collection_id = ?";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db!=null) {
            cursor = db.rawQuery(query,new String[]{String.valueOf(colid)});

        }


        if (cursor.getCount() ==0) {
            cursor.close();
            db.close();
            return flashcards;
        } else {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String front = cursor.getString(1);
                String back = cursor.getString(2);
                int coliddb = cursor.getInt(3);
                flashcards.add(new Flashcard(front,back,id,coliddb));
            }
        }


        cursor.close();
        db.close();
        return flashcards;
    }

    public void addToTestHistory(int score,String datetime,int totalQuestions,int colID,int isPop) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("Score",score);
        cv.put("Date_Of_Test",datetime);
        cv.put("Total_Questions",totalQuestions);
        cv.put("Collection_ID",colID);
        cv.put("Is_Pop",isPop);

        long result = db.insert(TABLE_NAME_3,null,cv);
        //put logic later to maybe handle redirect
        if (result == -1) {
            Toast.makeText(context, "Failed to add", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context,"Added Succesfully",Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    public File exportCollectionToFile(int colID,String colName) {
        File exportdir = new File(context.getExternalFilesDir(null),"exportCSV/");
        if (!exportdir.exists()) {
            exportdir.mkdirs();
        }
        File retFile = new File(exportdir, colName + ".csv");


        String query = "Select * " +
                "from Flashcards " +
                "where Collection_id = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        FileWriter writer = null;

        Cursor cursor = null;
        cursor = db.rawQuery(query, new String[]{String.valueOf(colID)});



        if (cursor.getCount() == 0) {
            return retFile;
            //maybe putLogic here to handle empty collections??
        }

            try {
                 writer = new FileWriter(retFile);
                writer.write("Type,Front,Back\n");
                colName = handleComma(colName);
                writer.write("Collection," + colName+"\n");



                while (cursor.moveToNext()) {
                    int id = cursor.getInt(0);
                    String front = handleComma(cursor.getString(1));
                    String back = handleComma(cursor.getString(2));

                    writer.write("Flashcard," + front + "," + back+"\n");

                }
            } catch (IOException e) {
                e.printStackTrace();
            }  finally {
                if (writer!= null) {
                    try{
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }


            return retFile;

    }


    public void importCSVToDB(File file) {
        try {
            CSVReader reader = new CSVReader(new FileReader(file));
            ContentValues cv = new ContentValues();
            SQLiteDatabase db = this.getWritableDatabase();
            long result = 0;
            long colid2 = 0;


            String[] line = reader.readNext();//handles getting to actual content
            if (line ==null || !line[0].equals("Type")|| !line[1].equals("Front")|| !line[2].equals("Back")) {
                Toast.makeText(context,"please dont upload files not made through the export feature",Toast.LENGTH_SHORT).show();
                return;
            }
            line = reader.readNext();
            try {
                cv.put("Name", line[1]);
            } catch (UnknownError e) {
                e.printStackTrace();
            }
            colid2 = db.insert(TABLE_NAME,null,cv);//adding the collection
            cv.clear();

            while ((line = reader.readNext())!=null) {
                cv.put("Front",line[1]);
                cv.put("Back",line[2]);
                cv.put("Collection_ID",colid2);
                result = db.insert(TABLE_NAME_2,null,cv);
            }

        } catch(IOException e) {
            e.printStackTrace();
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }





    public int getMaxColID() {
        String query = "Select MAX(Collection_ID) " +
                "from Collections "
                ;
        int id = 1;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db!=null) {
            cursor = db.rawQuery(query,null);

        }


        if (cursor.getCount() ==0) {
            cursor.close();
            db.close();
            return 1;
        } else {
            while (cursor.moveToNext()) {
                id = cursor.getInt(0);

            }
            cursor.close();
        }



        db.close();
        return id;
    }

    public String handleComma(String val) {
        if (val == null) {
            return "";
        }

        val = val.replace("\"","\"\"");
        if (val.contains(",") || val.contains("\n")||val.contains("\r")) {
            val = "\"" + val + "\"";
        }
        return val;
    }

    public ArrayList<Flashcard> getFlashcardsForTestMode() {
        ArrayList<Flashcard> flashcards = new ArrayList<>();
        String query = "Select * " +
                "from Flashcards ";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db!=null) {
            cursor = db.rawQuery(query,null);

        }


        if (cursor.getCount() ==0) {
            cursor.close();
            db.close();
            return flashcards;
        } else {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String front = cursor.getString(1);
                String back = cursor.getString(2);
                int coliddb = cursor.getInt(3);
                flashcards.add(new Flashcard(front,back,id,coliddb));
            }
        }


        cursor.close();
        db.close();
        return flashcards;
    }


    public String getColName(int colid) {
        String name = "";
        String query = "Select Name " +
                "from Collections " +
                "where Collection_id = ?";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db!=null) {
            cursor = db.rawQuery(query,new String[]{String.valueOf(colid)});

        }


        if (cursor != null && cursor.moveToFirst()) {
            name = cursor.getString(0);
        } else {
            name = "NameNotFound";
        }


        cursor.close();
        db.close();
        return name;
    }






}
