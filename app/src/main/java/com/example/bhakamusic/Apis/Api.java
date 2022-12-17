package com.example.bhakamusic.Apis;

import com.example.bhakamusic.ModelResponse.LoginRequest;
import com.example.bhakamusic.ModelResponse.PlaylistRequest;
import com.example.bhakamusic.ModelResponse.PlaylistResponse;
import com.example.bhakamusic.ModelResponse.RegisterRequest;
import com.example.bhakamusic.ModelResponse.RegisterResponse;
import com.example.bhakamusic.ModelResponse.SearchRequest;
import com.example.bhakamusic.ModelResponse.SearchResponse;
import com.example.bhakamusic.ModelResponse.SongsResponse;
import com.example.bhakamusic.ModelResponse.UserResponse;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Api {

    @POST("api/users/signup")
    Call<RegisterResponse> register(@Body RegisterRequest registerRequest);

    @POST("api/users/signin")
    Call<RegisterResponse> login(@Body LoginRequest loginRequest);

    @GET("api/songs/0452b13e-632c-477a-9638-35d72f289cd2")
    Call<SongsResponse> song();

    @POST("api/songs/search")
    Call<List<SearchResponse>> searchSong(@Body SearchRequest searchRequest);

    @GET("api/users")
    Call<UserResponse> getUserDetails(@Header("Authorization") String authToken);

    @PATCH("api/users/change-preference")
    Call<ResponseBody> getPreferenceChange(@Header("Authorization") String authToken);

    @GET("api/playlists/{id}")
    Call<PlaylistResponse> getPlaylistName(@Path("id") String s);

    @POST("api/playlists/create")
    Call<PlaylistResponse> setPlayList(@Body PlaylistRequest pl);
}
