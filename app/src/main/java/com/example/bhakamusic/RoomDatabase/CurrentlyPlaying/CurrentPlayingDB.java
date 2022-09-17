package com.example.bhakamusic.RoomDatabase.CurrentlyPlaying;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.bhakamusic.RoomDatabase.RecentlyPlayedDB.RecentlyPlayedDB;

@Database(entities = {CurrentlyPlaying.class}, version = 1)
public abstract class CurrentPlayingDB extends RoomDatabase {
    //Database instance
    public static CurrentPlayingDB INSTANCE;
    public static final String DATABASE_NAME = "currently_played_db";

    public synchronized static CurrentPlayingDB getInstance(Context context) {
        //Check if db is present
        if (INSTANCE == null) {
            synchronized (CurrentPlayingDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    CurrentPlayingDB.class, DATABASE_NAME)
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract CurrentDao currentDao();
}
