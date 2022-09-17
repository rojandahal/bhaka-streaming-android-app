package com.example.bhakamusic.RoomDatabase.CurrentlyPlaying;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import java.util.List;

@Dao
public interface CurrentDao {
    //Insert to fav
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(CurrentlyPlaying currentlyPlaying);

    //Delete query
    @Delete
    void delete(CurrentlyPlaying currentlyPlaying);

    //Delete all data
    @Delete
    void reset(List<CurrentlyPlaying> dataList);

    //Get all data
    @Query("SELECT * FROM currently_playing")
    List<CurrentlyPlaying> getAll();
}
