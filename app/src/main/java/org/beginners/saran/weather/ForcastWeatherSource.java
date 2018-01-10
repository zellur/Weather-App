package org.beginners.saran.weather;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Nizam Uddin Shamrat on 1/8/2018.
 */

public class ForcastWeatherSource {

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase db;

    public ForcastWeatherSource(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public void open(){
        db = databaseHelper.getWritableDatabase();
    }
    public void close(){
        db.close();
    }
    public boolean incertUserData(ForcastWeatherModel forcastWeatherModel){

        this.open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(WeatherDatabaseHelper.FOR_WEATHER_DATE,forcastWeatherModel.getForDate());
        contentValues.put(WeatherDatabaseHelper.FOR_WEATHER_COL_DES,forcastWeatherModel.getForDes());
        contentValues.put(WeatherDatabaseHelper.FOR_WEATHER_MIN_TEMP,forcastWeatherModel.getForMinTemp());
        contentValues.put(WeatherDatabaseHelper.FOR_WEATHER_MAX_TEMP,forcastWeatherModel.getForMaxTemp());
        long incertedRow =  db.insert(WeatherDatabaseHelper.TABLE_FOR_WEATHER,null,contentValues);
        if (incertedRow>0)
            return true;
        else return false;
    }

    public ArrayList<ForcastWeatherModel> getForcast(){
        this.open();
        ArrayList<ForcastWeatherModel>forcastWeatherModels = new ArrayList<>();
        //db.rawQuery("select * from "+EmployeeDatabaseHelper.TABLE_EMPLOYEE,null);
        Cursor cursor = db.query(WeatherDatabaseHelper.TABLE_FOR_WEATHER,null,null,null,null,null,null);
        if(cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();
            do{
                String date = cursor.getString(cursor.getColumnIndex(WeatherDatabaseHelper.FOR_WEATHER_DATE));
                String des = cursor.getString(cursor.getColumnIndex(WeatherDatabaseHelper.FOR_WEATHER_COL_DES));
                String min = cursor.getString(cursor.getColumnIndex(WeatherDatabaseHelper.FOR_WEATHER_MIN_TEMP));
                String mx = cursor.getString(cursor.getColumnIndex(WeatherDatabaseHelper.FOR_WEATHER_MAX_TEMP));
                forcastWeatherModels.add(new ForcastWeatherModel(date,des,min,mx));
            }while (cursor.moveToNext());
        }
        cursor.close();
        this.close();
        return forcastWeatherModels;
    }

}
