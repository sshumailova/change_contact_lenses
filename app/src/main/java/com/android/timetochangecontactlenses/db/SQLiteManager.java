package com.android.timetochangecontactlenses.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class SQLiteManager extends SQLiteOpenHelper {
    private static SQLiteManager sqLiteManager;

    public SQLiteManager(@Nullable Context context) {
        super(context, MyConstants.DB_NAME, null, MyConstants.DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
//        Log.d("MyLog", "YYYYY" );
        StringBuilder sql;
        sql = new StringBuilder().
                append(" CREATE TABLE ").
                append(MyConstants.TABLE_NAME).
                append("(").
                append(MyConstants.COUNTER).
                append(" INTEGER PRIMARY KEY AUTOINCREMENT,").
                append(MyConstants._ID).
                append(" INT, ").
                append(MyConstants.FIRST_DAY).
                append(" TEXT,").
                append(MyConstants.LAST_DAY).
                append(" TEXT )");

        db.execSQL(sql.toString());
    }

    public static SQLiteManager getInstance(Context context) {
        if (sqLiteManager == null) {
            sqLiteManager = new SQLiteManager(context.getApplicationContext());
        }
        return sqLiteManager;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//switch (oldVersion){
//    case 1:
//        db.execSQL("ALTER TABLE "+ MyConstants.TABLE_NAME + " ADD COLUMN "+ "NEW_COLMN "+"TEXT");
//    case 2:
//        db.execSQL("ALTER TABLE "+ MyConstants.TABLE_NAME + " ADD COLUMN "+ "NEW_COLMN "+"TEXT");
////}
//        db.execSQL(MyConstants.DROP_TABBLE);
//        onCreate(db);
//    }
    }

    public void addNoteToDatabase(Note note) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MyConstants.FIRST_DAY, note.getFirst());
        values.put(MyConstants.LAST_DAY, note.getLast());
        values.put(MyConstants._ID, note.getId());
        Log.d("MyLog", "CHEEK " + note.getFirst() + " " + note.getLast() + " " + note.getId());
        sqLiteDatabase.insertOrThrow(MyConstants.TABLE_NAME, null, values);
        // Log.d("MyLog", "Error inserting ");
        ArrayList<Note> notes = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase2 = this.getReadableDatabase();
        try (
                Cursor cursor = sqLiteDatabase2.rawQuery("SELECT * FROM " + MyConstants.TABLE_NAME, null)) {
            if (cursor.getCount() != 0) {
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(1);
                    String first = cursor.getString(2);
                    String last = cursor.getString(3);
                    Note note2 = new Note(id, first, last);
                    notes.add(note2);
                    int a = notes.size();

                    Log.d("MyLog", "SIZEARRAySQLS" + a);
                }
            }
        }
    }

    public void deleteNoteFromDatabase(String note) {

        String[] arrSplit = note.split(" - ");
        String first = arrSplit[0];
        String last = arrSplit[1];
        SQLiteDatabase sqLiteDatabase2 = this.getWritableDatabase();
        //sqLiteDatabase2.delete(MyConstants.TABLE_NAME, MyConstants.FIRST_DAY,new String[]{first,last});
        deleteTitle(sqLiteDatabase2, first);


    }

    public boolean deleteTitle(SQLiteDatabase sqLiteDatabase2, String first) {
        return sqLiteDatabase2.delete(MyConstants.TABLE_NAME, MyConstants.FIRST_DAY + "=?", new String[]{first}) > 0;

    }

    public ArrayList<Note> populateNoteListArray() {
        ArrayList<Note> notes = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        try (
                //
                // Cursor cursor = sqLiteDatabase.rawQuery("SELECT " + MyConstants._ID + ", " + MyConstants.FIRST_DAY + ", " + MyConstants.LAST_DAY + " FROM " + MyConstants.TABLE_NAME + " ORDER BY " + MyConstants._ID + " DESC LIMIT 1", null)) {
                Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + MyConstants.TABLE_NAME, null)) {
            if (cursor.getCount() != 0) {
                int a = 0;
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(1);
                    String first = cursor.getString(2);
                    String last = cursor.getString(3);
                    Note note = new Note(id, first, last);
                    notes.add(note);
                    a = notes.size();
                    Log.d("MyLog", "SIZEARRAySQLS  sssssss     " + a);

                }
                Log.d("MyLog", "SIZEARRAySQLS" + a);
            }
        }
        return notes;


    }

    public void updateNoteInDb(Note note) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyConstants._ID, note.getId());
        contentValues.put(MyConstants.FIRST_DAY, note.getFirst());
        contentValues.put(MyConstants.LAST_DAY, note.getLast());
        sqLiteDatabase.update(MyConstants.TABLE_NAME, contentValues, MyConstants._ID + " =? ", new String[]{String.valueOf(note.getId())});

    }
}
