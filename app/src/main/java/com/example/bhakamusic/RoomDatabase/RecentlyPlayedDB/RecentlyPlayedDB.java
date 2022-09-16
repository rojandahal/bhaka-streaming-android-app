package com.example.bhakamusic.RoomDatabase.RecentlyPlayedDB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.bhakamusic.RoomDatabase.FavDao;

@Database(entities = {RecentlyPlayed.class}, version = 1)
public abstract class RecentlyPlayedDB extends RoomDatabase {
    //Create database instance
    public static com.example.bhakamusic.RoomDatabase.FavouriteDB INSTANCE;
    //Database name
    public static final String DATABASE_NAME = "recently_played_db";

    public synchronized static com.example.bhakamusic.RoomDatabase.FavouriteDB getInstance(Context context){
        //Check if db is present
        if (INSTANCE == null) {
            synchronized (com.example.bhakamusic.RoomDatabase.FavouriteDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    com.example.bhakamusic.RoomDatabase.FavouriteDB.class, DATABASE_NAME)
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    //Create Data access object
    public abstract RecentlyDao recentlyDao();
}
