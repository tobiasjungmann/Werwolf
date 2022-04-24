package com.example.tobias.werwolf_v1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

/**
 * Created by Tobias on 19.01.2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "Werwolf.db";
    public static final String TABLE_NAME="werwolf_tabelle";
    public static final String COL_1="ID";
    public static final String COL_2="NAME";
    public static final String COL_3="CHARAKTER";
    public static final String COL_4="LEBENDIG";
    public static final String COL_5="VERZAUBERT";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        Log.e("D","DatabseHelper vor 1. Datenbank");
        SQLiteDatabase db = this.getWritableDatabase();
        Log.e("D","DatabseHelper nach 1. Datenbank");
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String tabelle= "CREATE TABLE " + TABLE_NAME + "("+COL_1+" INTEGER PRIMARY KEY AUTOINCREMENT," + COL_2 + " TEXT, " + COL_3+ " TEXT, " + COL_4 + " TEXT, "+ COL_5 + " TEXT)";
        db.execSQL(tabelle);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String item)
    {
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, item);
        contentValues.put(COL_5, "nein");

        Log.d(TAG, "Eingefügt: "+ item+"   in: "+TABLE_NAME);

        long result=db.insert(TABLE_NAME, null, contentValues);

        if(result==-1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public boolean addCharakter(String name, String charakter)
    {
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, charakter);
        contentValues.put(COL_5, "nein");
        //contentValues.put(COL_4, "nein");
        //contentValues.put(COL_4, "1");

        Log.d(TAG, "Eingefügt: "+ name+"   in: "+TABLE_NAME);

        long result=db.insert(TABLE_NAME, null, contentValues);

        if(result==-1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public boolean addVerzaubert(String item, String charakter)
    {
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, item);
        contentValues.put(COL_3, charakter);
        contentValues.put(COL_5, "ja");
        //contentValues.put(COL_4, "1");

        Log.d(TAG, "Eingefügt: "+ item+"   in: "+TABLE_NAME);

        long result=db.insert(TABLE_NAME, null, contentValues);

        if(result==-1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public boolean addjungesExtra(String item,String verzaubert)
    {
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, item);
        contentValues.put(COL_3, "werwolf");
        contentValues.put(COL_5, verzaubert);
        //contentValues.put(COL_4, "1");

        Log.d(TAG, "Eingefügt: "+ item+"   in: "+TABLE_NAME);

        long result=db.insert(TABLE_NAME, null, contentValues);

        if(result==-1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public Cursor getData()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        String query="SELECT * FROM "+TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getItemID(String name)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        String query="SELECT " + COL_1 + " FROM " + TABLE_NAME + " WHERE " + COL_2 + " = '" +name+"'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Integer deleteName(String id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        //String query="DELETE FROM " + TABLE_NAME + " WHERE " + COL_1 + " = " +id+"'"+" AND "+ COL_2 + " = " +name+"'";
        //db.execSQL(query);
         return db.delete(TABLE_NAME, "ID = ?", new String[] {id});

    }

    public Integer deleteNurName(String name)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        //String query="DELETE FROM " + TABLE_NAME + " WHERE " + COL_1 + " = " +id+"'"+" AND "+ COL_2 + " = " +name+"'";
        //db.execSQL(query);
        return db.delete(TABLE_NAME, "NAME = ?", new String[] {name});

    }


    public void clearDatabase() {
        SQLiteDatabase db=this.getWritableDatabase();
        String clearDBQuery = "DELETE FROM "+TABLE_NAME;
        db.execSQL(clearDBQuery);
    }
}
