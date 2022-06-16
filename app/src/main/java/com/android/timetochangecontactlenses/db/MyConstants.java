package com.android.timetochangecontactlenses.db;

public class MyConstants {
    //SQL
    public static final String TABLE_NAME = "my_table1"; // название таблицы
    public static final String _ID = "_id";// название колонны где будет хвраниться индитификатор
    public static final String COUNTER = "Counter";
    public static final String FIRST_DAY="first_day"; // колонна где мы первый день ношения линз
    public static final String LAST_DAY = "last_day"; // колонна где мы второй день ношения линз
    public static final String DB_NAME = "NoteDB"; // название базы данных
    public static final int DB_VERSION = 1; // версия базы данных
    public static final String TABLE_STRUCTURE =
            " CREATE TABLE " + TABLE_NAME + " (" +
                    COUNTER + " INTEGER PRIMARY KEY," + _ID+ " INT, "+
                    FIRST_DAY + " TEXT," + LAST_DAY+ " TEXT )";
    public  static final String DROP_TABBLE ="DROP TABLE IF EXISTS"+ TABLE_NAME; // что бы сбрасывать таблицу
    // SharePreference
    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_PREFERENCES_Power = "Power";
    public static final String APP_PREFERENCES_BC = "BC";
    public static final String APP_PREFERENCES_DIA = "DIA";
    public static final String APP_PREFERENCES_NameUser = "Name";
    public static final String APP_PREFERENCES_RSCH = "Replacement schedule";
}
