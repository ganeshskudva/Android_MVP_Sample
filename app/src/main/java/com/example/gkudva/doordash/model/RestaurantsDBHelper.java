package com.example.gkudva.doordash.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.List;

/**
 * Created by gkudva on 25/08/17.
 */

public class RestaurantsDBHelper extends SQLiteOpenHelper {
    public static String TAG = "RestaurantsDBHelper";
    private static final String DATABASE_NAME = "restaurantsDatabase.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_RESTAURANTS = "restaurants";

    private static final String KEY_RES_ID= "id";
    private static final String KEY_RES_NAME= "name";
    private static final String KEY_RES_DESC= "desc";
    private static final String KEY_RES_IMG= "image";
    private static final String KEY_RES_STATUS_TYPE= "status_type";
    private static final String KEY_RES_STATUS= "status";
    private static final String KEY_RES_FAV= "fav";

    private static RestaurantsDBHelper sInstance;

    public static synchronized RestaurantsDBHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new RestaurantsDBHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    private RestaurantsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_RES_TABLE = "CREATE TABLE " + TABLE_RESTAURANTS +
                "(" +
                KEY_RES_ID + " INTEGER PRIMARY KEY," + // Define a primary key
                KEY_RES_NAME + " TEXT," +
                KEY_RES_DESC + " TEXT," +
                KEY_RES_IMG + " TEXT," +
                KEY_RES_STATUS_TYPE + " TEXT," +
                KEY_RES_STATUS + " TEXT," +
                KEY_RES_FAV + " INTEGER" +
                ")";


        db.execSQL(CREATE_RES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESTAURANTS);

            onCreate(db);
        }
    }

    public void addRestaurants(List<Restaurant> restaurantList) {
        for (Restaurant restaurant : restaurantList) {
            updateTask(restaurant);
        }
    }

    public void updateTask(Restaurant res) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();

            values.put(KEY_RES_ID, Integer.parseInt(res.getId()));
            values.put(KEY_RES_NAME, res.getName().replaceAll("'", "''"));
            values.put(KEY_RES_DESC, res.getDescription().replaceAll("'", "''"));
            values.put(KEY_RES_IMG, res.getImgUrl().replaceAll("'", "''"));
            values.put(KEY_RES_STATUS_TYPE, res.getStatusType().replaceAll("'", "''"));
            values.put(KEY_RES_STATUS, res.getStatus().replaceAll("'", "''"));
            values.put(KEY_RES_FAV, res.isFavourite());
            db.insertOrThrow(TABLE_RESTAURANTS, null, values);
            db.setTransactionSuccessful();
        }
        catch(Exception ex)
        {
            Log.d(TAG, ex.getMessage());
        }
        finally {
            db.endTransaction();
        }
    }

    public void updateFavChoice(Restaurant res)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try
        {
            ContentValues values = new ContentValues();
            values.put(KEY_RES_ID, Integer.parseInt(res.getId()));
            values.put(KEY_RES_FAV, res.isFavourite());
            db.update(TABLE_RESTAURANTS, values, KEY_RES_NAME + "= ?", new String[]{res.getName()});
            db.setTransactionSuccessful();
        }
        catch (Exception ex)
        {
            Log.d(TAG, ex.getMessage());
        }
        finally {
            db.endTransaction();
        }
    }

    public Cursor selectAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select * from " + TABLE_RESTAURANTS;
        Cursor result = db.rawQuery(query, null);
        return result;
    }

}
