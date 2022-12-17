package com.example.bhakamusic.RoomDatabase.UserDB;

import java.util.ArrayList;

public abstract class UserCredentials {
    public static String token = "token";
    public static String username;
    public static String id;
    public static String email;
    public static String preference;
    public static ArrayList<String> playlist = new ArrayList<>();
    public static ArrayList<String> names = new ArrayList<>();
    public static void resetArrayList() {
        names = new ArrayList<>();
    }
    public static void setToList(String s){
        names.add(s);
    }

    public static ArrayList<String> getNames() {
        return names;
    }

    public static ArrayList<String> getPlaylist() {
        return playlist;
    }

    public static void setPlaylist(ArrayList<String> playlist) {
        UserCredentials.playlist = playlist;
    }

    public static void setToken(String token) {
        UserCredentials.token = token;
    }

    public static String getId() {
        return id;
    }

    public static void setId(String id) {
        UserCredentials.id = id;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        UserCredentials.email = email;
    }

    public static String getPreference() {
        return preference;
    }

    public static void setPreference(String preference) {
        UserCredentials.preference = preference;
    }

    public static String getToken() {
        return token;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        UserCredentials.username = username;
    }
}
