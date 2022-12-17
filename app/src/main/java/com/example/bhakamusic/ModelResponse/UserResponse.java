package com.example.bhakamusic.ModelResponse;

import java.util.ArrayList;

public class UserResponse {
    String id;
    String username;
    String email;
    String password;
    String profilePicture;
    String[] liked;
    ArrayList<String> createdPlaylists = new ArrayList<String>();
    String[] followedPlaylists;
    String preference;
    String createdAt;
    String updatedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String[] getLiked() {
        return liked;
    }

    public void setLiked(String[] liked) {
        this.liked = liked;
    }

    public ArrayList getCreatedPlaylists() {
        return createdPlaylists;
    }

    public void setCreatedPlaylists(ArrayList createdPlaylists) {
        this.createdPlaylists = createdPlaylists;
    }

    public String[] getFollowedPlaylists() {
        return followedPlaylists;
    }

    public void setFollowedPlaylists(String[] followedPlaylists) {
        this.followedPlaylists = followedPlaylists;
    }

    public String getPreference() {
        return preference;
    }

    public void setPreference(String preference) {
        this.preference = preference;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
