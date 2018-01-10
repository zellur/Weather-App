package org.beginners.saran.weather;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Nizam Uddin Shamrat on 1/8/2018.
 */

public class CurrentWeatherSource {

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase db;

    public CurrentWeatherSource(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public void open(){
        db = databaseHelper.getWritableDatabase();
    }
    public void close(){
        db.close();
    }
    public boolean incertUserData(CurrentWeatherModel currentWeatherModel){

        this.open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(WeatherDatabaseHelper.CURRENT_WEATHER_COL_TEMP,currentWeatherModel.getCurrentTemp());
        contentValues.put(WeatherDatabaseHelper.CURRENT_WEATHER_CITY,currentWeatherModel.getCurrentCity());
        contentValues.put(WeatherDatabaseHelper.CURRENT_WEATHER_COUNTRY,currentWeatherModel.getCurrentCountry());
        contentValues.put(WeatherDatabaseHelper.CURRENT_WEATHER_COL_DES,currentWeatherModel.getCurrentDes());
        contentValues.put(WeatherDatabaseHelper.CURRENT_WEATHER_COL_HUMANITY,currentWeatherModel.getCurrentHumanity());
        contentValues.put(WeatherDatabaseHelper.CURRENT_WEATHER_COL_PRESSER,currentWeatherModel.getCurrentPresser());
        contentValues.put(WeatherDatabaseHelper.CURRENT_WEATHER_COL_MAX_TEMP,currentWeatherModel.getCurrentMaxTexp());
        contentValues.put(WeatherDatabaseHelper.CURRENT_WEATHER_COL_MIN_TEMP,currentWeatherModel.getCurrentMinTemp());
        long incertedRow =  db.insert(WeatherDatabaseHelper.TABLE_CURRENT_WEATHER,null,contentValues);
        if (incertedRow>0)
            return true;
        else return false;
    }
}
