package com.alaminkarno.sqlitedatbase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelp extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "User.db";
    private static String TABLE_NAME = "User";
    public static String COL_ID = "Id";
    public static String COL_NAME = "Name";
    public static String COL_AGE = "Age";
    private static int VERSION = 1;

    private String CREATE_TABLE = "create table "+TABLE_NAME+" ("+COL_ID+" Integer primary key autoincrement, "+COL_NAME+" TEXT, "+COL_AGE+" TEXT)";

    public DatabaseHelp(@Nullable Context context) {

        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public long insertData(String name,String age){

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME,name);
        contentValues.put(COL_AGE,age);
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        long id = sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        sqLiteDatabase.close();

        return id;
    }

    public Cursor showData(){

        String ALL_DATA_QUERY = "select * From "+TABLE_NAME;

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(ALL_DATA_QUERY,null);

        return cursor;
    }

    public Cursor searchData(int ID){
        String SEARCH_QUERY = "select * From "+TABLE_NAME+" where "+COL_ID+" = "+ID;

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(SEARCH_QUERY,null);

        return  cursor;
    }

    public boolean updateData(int id,String name,String age){

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_ID,id);
        contentValues.put(COL_NAME,name);
        contentValues.put(COL_AGE,age);
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.update(TABLE_NAME,contentValues,COL_ID+" = ? ",new String[]{String.valueOf(id)});

        return true;

    }
}
