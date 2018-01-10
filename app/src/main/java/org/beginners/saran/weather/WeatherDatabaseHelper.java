package org.beginners.saran.weather;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Nizam Uddin Shamrat on 1/8/2018.
 */

public class WeatherDatabaseHelper extends SQLiteOpenHelper{


    public static final String DATABASE_NAME = "fWeather_db";
    public static final int DATABASE_VERSION = 1;


    //Forcast Table
    public static final String TABLE_FOR_WEATHER = "tbl_fWeather";
    public static final String FOR_WEATHER_COL_ID = "fWeather_id";
    public static final String FOR_WEATHER_DATE = "fWeather_date";
    public static final String FOR_WEATHER_COL_DES = "fWeather_des";
    public static final String FOR_WEATHER_MIN_TEMP = "fWeather_min_temp";
    public static final String FOR_WEATHER_MAX_TEMP = "fWeather_max_temp";

    public static final String CREATE_TABLE_FOR_WEATHER = "CREATE TABLE "+TABLE_FOR_WEATHER+"("+
            FOR_WEATHER_COL_ID+" INTEGER PRIMARY KEY, "+
            FOR_WEATHER_DATE+" TEXT, "+
            FOR_WEATHER_COL_DES+" TEXT, "+
            FOR_WEATHER_MIN_TEMP+" TEXT, "+
            FOR_WEATHER_MAX_TEMP+" TEXT);";
    private int currentId;
    private String currentTemp;
    private String currentCity;
    private String currentCountry;
    private String currentDes;
    private String currentHumanity;
    private String currentPresser;
    private String currentMaxTexp;
    private String currentMinTemp;

    //Current Table
    public static final String TABLE_CURRENT_WEATHER = "tbl_current";
    public static final String CURRENT_WEATHER_COL_ID = "c_id";
    public static final String CURRENT_WEATHER_COL_TEMP = "c_temp";
    public static final String CURRENT_WEATHER_CITY = "c_city";
    public static final String CURRENT_WEATHER_COUNTRY = "c_country";
    public static final String CURRENT_WEATHER_COL_DES = "c_des";
    public static final String CURRENT_WEATHER_COL_HUMANITY = "c_humanity";
    public static final String CURRENT_WEATHER_COL_PRESSER = "c_presser";
    public static final String CURRENT_WEATHER_COL_MAX_TEMP = "c_max_temp";
    public static final String CURRENT_WEATHER_COL_MIN_TEMP = "c_min_temp";

    public static final String CREATE_TABLE_CURRENT_WEATHER = "CREATE TABLE "+TABLE_CURRENT_WEATHER+"("+
            CURRENT_WEATHER_COL_ID+" INTEGER PRIMARY KEY, "+
            CURRENT_WEATHER_COL_TEMP+" TEXT, "+
            CURRENT_WEATHER_CITY+" TEXT, "+
            CURRENT_WEATHER_COUNTRY+" TEXT, "+
            CURRENT_WEATHER_COL_DES+" TEXT, "+
            CURRENT_WEATHER_COL_HUMANITY+" TEXT, "+
            CURRENT_WEATHER_COL_PRESSER+" TEXT, "+
            CURRENT_WEATHER_COL_MAX_TEMP+" TEXT, "+
            CURRENT_WEATHER_COL_MIN_TEMP+" TEXT);";

    public WeatherDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_CURRENT_WEATHER);
        db.execSQL(CREATE_TABLE_FOR_WEATHER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_CURRENT_WEATHER);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_FOR_WEATHER);
        onCreate(db);

    }
}
