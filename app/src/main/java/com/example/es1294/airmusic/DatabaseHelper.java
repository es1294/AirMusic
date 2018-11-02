package com.example.es1294.airmusic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    //change database version to 3 so that it can be converted to MySQL (hopefully)
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "Users.db";
    private static final String TABLE_NAME = "users";
    private static final String COLUMN_ID = "id";
    //private static final String COLUMN_NAME = "n";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_USERNAME  = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_FULLNAME = "fullName";
    private static final String COLUMN_ABOUT = "about";
    private static final String COLUMN_ARTISTONE = "artistOne";
    private static final String COLUMN_ARTISTTWO = "artistTwo";
    private static final String COLUMN_ARTISTTHREE = "artistThree";
    private static final String COLUMN_ARTISTFOUR = "artistFour";
    private static final String COLUMN_ARTISTFIVE = "artistFive";
    private static final String COLUMN_GENREONE = "genreOne";
    private static final String COLUMN_GENRETWO = "genreTwo";
    private static final String COLUMN_GENRETHREE = "genreThree";

    SQLiteDatabase sqLiteDatabase;

    private static final String TABLE_CREATE = "create table users (id integer primary key AUTOINCREMENT not null , " +
            "email text not null , username text not null , password text not null , " +
            "fullName text not null , about text not null , artistOne text not null , artistTwo text not null , artistThree text not null ," +
            " artistFour text not null , artistFive text not null , genreOne text not null , genreTwo text not null , genreThree text not null);";

    public DatabaseHelper (Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE_CREATE);
        this.sqLiteDatabase = sqLiteDatabase;
    }

    public void insertUser(User u){
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String query = "select * from users";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        int count = cursor.getCount();

        values.put(COLUMN_ID, count);
        values.put(COLUMN_USERNAME , u.getUsername());
        values.put(COLUMN_EMAIL , u.getEmail());
        values.put(COLUMN_PASSWORD , u.getPassword());
        values.put(COLUMN_FULLNAME , u.getFullName());
        values.put(COLUMN_ABOUT , u.getAbout());
        values.put(COLUMN_ARTISTONE , u.getArtistOne());
        values.put(COLUMN_ARTISTTWO , u.getArtistTwo());
        values.put(COLUMN_ARTISTTHREE , u.getArtistThree());
        values.put(COLUMN_ARTISTFOUR , u.getArtistFour());
        values.put(COLUMN_ARTISTFIVE , u.getArtistFive());
        values.put(COLUMN_GENREONE , u.getGenreOne());
        values.put(COLUMN_GENRETWO , u.getGenreTwo());
        values.put(COLUMN_GENRETHREE , u.getGenreThree());

        sqLiteDatabase.insert(TABLE_NAME ,null, values);
        sqLiteDatabase.close();
    }

    public String searchPass(String uname){
        sqLiteDatabase = this.getReadableDatabase();
        String query = "select username, password from "+TABLE_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        String a, b;
        b = "not found";
        if(cursor.moveToFirst()){
            do{
                a = cursor.getString(0);

                if(a.equals(uname)){
                    b = cursor.getString(1);
                    break;
                }
            }
            while(cursor.moveToNext());
        }

        return b;
    }

    public String doesUserExist(String uname){
        sqLiteDatabase = this.getReadableDatabase();
        String query = "select username from "+TABLE_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        String a, b;
        b = "not found";
        if(cursor.moveToFirst()){
            do{
                a = cursor.getString(0);

                if(a.equals(uname)){
                    b = cursor.getString(0);
                    break;
                }
            }
            while(cursor.moveToNext());
        }

        return b;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String query = "DROP TABLE IF EXISTS " + TABLE_NAME;
        sqLiteDatabase.execSQL(query);
        this.onCreate(sqLiteDatabase);
    }
}
