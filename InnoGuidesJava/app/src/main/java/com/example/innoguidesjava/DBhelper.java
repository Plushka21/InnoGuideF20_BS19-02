package com.example.innoguidesjava;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DBhelper extends SQLiteOpenHelper {


    public DBhelper(@Nullable Context context) {
        super(context, "Login.db", null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table InnoGuides(email text primary key,password text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists InnoGuides");
    }

    public boolean insert(String email, String password) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("email",email);
        contentValues.put("password",password);
        long ins=db.insert("InnoGuides",null,contentValues);
        if (ins==-1) return false;
        else return true;
    }

    public boolean checkEmail(String email)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM InnoGuides WHERE email=?",new String[]{email});
        if (cursor.getCount()>0) return false;
        else return true;

    }
    public boolean authorization(String Uemail, String Upassword)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM InnoGuides WHERE email=? and password=?", new String[]{Uemail, Upassword});
        return cursor.getCount() <= 0;
    }
}