package com.android.timetochangecontactlenses.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

//public class MyDbManager {
//    private Context context;
//    private SQLiteManager myDbHelper;
//    private SQLiteDatabase db;
//
//    public MyDbManager(Context context) {
//        this.context = context;
//        myDbHelper = new SQLiteManager(context);
//
//    }
//
//    public void openDb() {
//        db = myDbHelper.getWritableDatabase();
//
//    }
//
//    public void insertToDb(String first_day, String last_day) {
//        ContentValues values = new ContentValues();
//        values.put(MyConstants.FIRST_DAY, first_day);
//        values.put(MyConstants.LAST_DAY, last_day);
//
//        db.insert(MyConstants.TABLE_NAME, null, values);
//        Cursor cursor = db.query(MyConstants.TABLE_NAME, null, null, null, null, null, null);
//
//        Log.d("MyLog", "QuantityOfLIne " + cursor.getCount());
//    }
//
//    public ArrayList<String> getFromDB() {
//        //String selection =MyConstants.FIRST_DAY;
//        // String[] selectionArgs = { "My Title" };
//
//        ArrayList<String> tempList = new ArrayList<>();
////        Cursor cursor=db.query(MyConstants.TABLE_NAME,null,null,null,null,null,null);
////        cursor.moveToFirst();
////        while (!cursor.isAfterLast()) {
////             String first_day =cursor.getString(cursor.getColumnIndexOrThrow(MyConstants.FIRST_DAY.toString()));
////            tempList.add(first_day);
////            cursor.moveToNext();
////            Log.d("MyLog" , "ЗАШел= "+first_day);
////
////        }
////        if(cursor.moveToLast()){
////            int first_index=cursor.getColumnIndexOrThrow(MyConstants.FIRST_DAY);
////            int last_index=cursor.getColumnIndexOrThrow(MyConstants.LAST_DAY);
////            do{
////                Log.d("MyLog" , "FIstNAMe= "+cursor.getString(first_index) + " SecNamr" +cursor.getString(last_index));
////            }
////            while (cursor.moveToNext());
////
////        }
////        else {
////            Log.d("MyLog", "NOOOOOOOOO");
////        }
////        Log.d("MyLog", "CLOse@");
////
////        cursor.close();
////        return tempList;
//
//
//        SQLiteDatabase sqLiteDatabase = this.myDbHelper.getReadableDatabase();
//        try {
//            Cursor result = sqLiteDatabase.rawQuery(MyConstants.TABLE_STRUCTURE, null);
//            if (result.getCount() != 0) {
//                while (result.moveToNext()) {
//                    int id = result.getInt(1);
//                    String first = result.getString(2);
//                    String last = result.getString(3);
//                    tempList.add(first);
//                    tempList.add(last);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return tempList;
//    }
//
//    public void closeDb() {
//        myDbHelper.close();
//    }
//}
