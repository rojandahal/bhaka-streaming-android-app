package com.example.bhakamusic.RoomDatabase.RecentlyPlayedDB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RecentlyDao {
    //Insert to fav
    @Insert()
    void insert(RecentlyPlayed recentlyPlayed);

    //Delete query
    @Delete
    void delete(RecentlyPlayed recentlyPlayed);

    //Delete all data
    @Delete
    void reset(List<RecentlyPlayed> dataList);

    //Get all data
    @Query("SELECT * FROM recently_played")
    List<RecentlyPlayed> getAll();
}
