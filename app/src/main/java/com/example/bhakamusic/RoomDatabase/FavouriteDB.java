package com.example.bhakamusic.RoomDatabase;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {FavouriteData.class}, version = 1)
public abstract class FavouriteDB extends RoomDatabase {
    //Create database instance
    public static FavouriteDB INSTANCE;
    //Database name
    public static final String DATABASE_NAME = "favouriteDatabase";

    public synchronized static FavouriteDB getInstance(Context context){
        //Check if db is present
        if (INSTANCE == null) {
            synchronized (FavouriteDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    FavouriteDB.class, DATABASE_NAME)
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }



    //Create Data access object
    public abstract FavDao favDao();
}
