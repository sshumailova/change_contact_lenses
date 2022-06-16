package com.android.timetochangecontactlenses.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "userstore.db"; // название бд
    private static final int SCHEMA = 1; // версия базы данных
   public static final String TABLE = "dates"; // название таблицы в бд
    // названия столбцов
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_FIRSt = "first";
    public static final String COLUMN_SECOND = "second";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE users (" + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_FIRSt
                + " TEXT, " + COLUMN_SECOND + " TEXT)");
        // добавление начальных данных
       /// db.execSQL("INSERT INTO "+ TABLE +" (" + COLUMN_FIRSt
              //  + ", " + COLUMN_SECOND + ") VALUES ('22 b.yz 2022', '29 b.yz 2022')");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        onCreate(db);
    }
}
