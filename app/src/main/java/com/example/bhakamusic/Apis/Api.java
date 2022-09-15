package com.example.bhakamusic.Apis;

import com.example.bhakamusic.ModelResponse.LoginRequest;
import com.example.bhakamusic.ModelResponse.RegisterRequest;
import com.example.bhakamusic.ModelResponse.RegisterResponse;
import com.example.bhakamusic.ModelResponse.SearchRequest;
import com.example.bhakamusic.ModelResponse.SearchResponse;
import com.example.bhakamusic.ModelResponse.SongsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {

    @POST("api/users/signup")
    Call<RegisterResponse> register(@Body RegisterRequest registerRequest);

    @POST("api/users/signin")
    Call<RegisterResponse> login(@Body LoginRequest loginRequest);

    @GET("api/songs/0452b13e-632c-477a-9638-35d72f289cd2")
    Call<SongsResponse> song();

    @POST("api/songs/search")
    Call<List<SearchResponse>> searchSong(@Body SearchRequest searchRequest);


}
