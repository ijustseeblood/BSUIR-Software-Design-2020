package com.example.ppo2;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;

public class DatabaseHelper extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "sequences.db";
    private static final int SCHEMA = 1;
    static final String TABLE = "sequences";
    public static final String ID = "_id";
    public static final String COLOR = "color";
    public static final String TITLE = "title";
    public static final String PREPARATION = "prepare";
    public static final String OPA = "work";
    public static final String CHILL = "chill";
    public static final String CYCLE = "cycles";
    public static final String JOPA = "sets";
    public static final String SETCHILL = "setChill";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {

        db.execSQL("CREATE TABLE " + TABLE + " (" + ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLOR
                + " INTEGER, " + TITLE
                + " TEXT, " + PREPARATION
                + " INTEGER, " + OPA
                + " INTEGER, " + CHILL
                + " INTEGER, " + CYCLE
                + " INTEGER, " + JOPA
                + " INTEGER, " + SETCHILL + " INTEGER);");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        onCreate(db);
    }
}