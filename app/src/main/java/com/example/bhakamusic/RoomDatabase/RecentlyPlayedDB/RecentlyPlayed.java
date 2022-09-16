package com.example.bhakamusic.RoomDatabase.RecentlyPlayedDB;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "recently_played")
public class RecentlyPlayed implements Serializable {

    //Create id column
    @PrimaryKey(autoGenerate = true)
    private int id;

    //Data in column
    @ColumnInfo(name = "songId")
    private String songId;

    //Data in column
    @ColumnInfo(name = "artistName")
    private String artistName;

    //Data in column
    @ColumnInfo(name = "coverArt")
    private String coverArt;

    //Data in column
    @ColumnInfo(name = "songDuration")
    private String songDuration;

    //Data in column
    @ColumnInfo(name = "songTitle")
    private String songTitle;

    //Getters and setters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSongId() {
        return songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getCoverArt() {
        return coverArt;
    }

    public void setCoverArt(String coverArt) {
        this.coverArt = coverArt;
    }

    public String getSongDuration() {
        return songDuration;
    }

    public void setSongDuration(String songDuration) {
        this.songDuration = songDuration;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }
}
