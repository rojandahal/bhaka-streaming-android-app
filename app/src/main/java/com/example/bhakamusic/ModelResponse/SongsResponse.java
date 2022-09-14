package com.example.bhakamusic.ModelResponse;

public class SongsResponse {
    String title;
    String duration;
    String artist;
    String[] featuredArtist;
    String [] genre;
    String album;
    String release;
    String playCount;
    String likes;
    String coverArt;
    String songSize;
    String songSizeLossy;

    public SongsResponse(String title, String duration, String artist, String[] featuredArtist, String[] genre, String album, String release, String playCount, String likes, String coverArt, String songSize, String songSizeLossy) {
        this.title = title;
        this.duration = duration;
        this.artist = artist;
        this.featuredArtist = featuredArtist;
        this.genre = genre;
        this.album = album;
        this.release = release;
        this.playCount = playCount;
        this.likes = likes;
        this.coverArt = coverArt;
        this.songSize = songSize;
        this.songSizeLossy = songSizeLossy;
    }

    public SongsResponse(String title, String artist) {
        this.title = title;
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String[] getFeaturedArtist() {
        return featuredArtist;
    }

    public void setFeaturedArtist(String[] featuredArtist) {
        this.featuredArtist = featuredArtist;
    }

    public String[] getGenre() {
        return genre;
    }

    public void setGenre(String[] genre) {
        this.genre = genre;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getPlayCount() {
        return playCount;
    }

    public void setPlayCount(String playCount) {
        this.playCount = playCount;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getCoverArt() {
        return coverArt;
    }

    public void setCoverArt(String coverArt) {
        this.coverArt = coverArt;
    }

    public String getSongSize() {
        return songSize;
    }

    public void setSongSize(String songSize) {
        this.songSize = songSize;
    }

    public String getSongSizeLossy() {
        return songSizeLossy;
    }

    public void setSongSizeLossy(String songSizeLossy) {
        this.songSizeLossy = songSizeLossy;
    }
}
