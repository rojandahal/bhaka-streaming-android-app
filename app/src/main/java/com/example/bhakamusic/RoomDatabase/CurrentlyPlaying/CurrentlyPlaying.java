package com.example.bhakamusic.RoomDatabase.CurrentlyPlaying;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "currently_playing")
public class CurrentlyPlaying implements Serializable {

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
    @ColumnInfo(name = "songTitle")
    private String songTitle;

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

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }
}
