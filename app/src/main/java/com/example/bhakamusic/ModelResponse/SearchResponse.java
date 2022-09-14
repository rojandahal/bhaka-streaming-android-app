package com.example.bhakamusic.ModelResponse;

public class SearchResponse {
    private String id;
    private String title;
    private String artist;
    private String coverArt;

    public SearchResponse(String id, String title, String artist, String coverArt) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.coverArt = coverArt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getCoverArt() {
        return coverArt;
    }

    public void setCoverArt(String coverArt) {
        this.coverArt = coverArt;
    }
}
