package com.example.superaccountingmaster;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;


public class DBOpenHelper extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "basicCode_tb";
    public DBOpenHelper(Context context,String name, CursorFactory factory,
                        int version){
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        //user table
        db.execSQL("create table if not exists user_tb(_id integer primary key autoincrement," +
                "userID text not null," +
                "pwd text not null)");

        //Configuration table
        db.execSQL("create table if not exists refCode_tb(_id integer primary key autoincrement," +
                "CodeType text not null," +
                "CodeID text not null," +
                "CodeName text not null)");

        //costDetail_tb
        db.execSQL("create table if not exists basicCode_tb(_id integer primary key autoincrement," +
                "userID text not null," +
                "Type integer not null," +
                "incomeWay text not null," +
                "incomeBy text not null," +
                "category text not null," +
                "item text not null," +
                "cost money not null," +
                "note text not null," +
                "makeDate text not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){

    }
    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }
    public void deleteData () {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
    }

    public Integer deleteDataon (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "_ID = ?", new String[] {id});
    }

}