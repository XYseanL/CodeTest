package com.example.test;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class MyDB extends SQLiteOpenHelper{
    private static final String CREATE_PhoneNumber="create table PhoneNumber(id integer primary key autoincrement, firstN text, " + "lastN text, image text,  Email text, PhoneNBer text, address text)";
    private Context mContext;
    public MyDB (Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
        mContext=context;
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        //create database
        sqLiteDatabase.execSQL(CREATE_PhoneNumber);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int q){
        sqLiteDatabase.execSQL("Drop table if exists PhoneNumber");
        onCreate(sqLiteDatabase);
    }
}
