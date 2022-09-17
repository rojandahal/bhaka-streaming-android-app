package com.example.bhakamusic.RoomDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavDao {
    //Insert to fav
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(FavouriteData favouriteData);

    //Delete query
    @Delete
    void delete(FavouriteData favouriteData);

    //Delete all data
    @Delete
    void reset(List<FavouriteData> dataList);

    //Get all data
    @Query("SELECT * FROM favourite_table")
    List<FavouriteData> getAll();
}
