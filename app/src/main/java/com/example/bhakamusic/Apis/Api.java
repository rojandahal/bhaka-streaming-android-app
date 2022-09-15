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

    @GET("api/songs/ecdec6c9-3850-4587-abb3-7aab45242e2b")
    Call<SongsResponse> song();

    @POST("api/songs/search")
    Call<List<SearchResponse>> searchSong(@Body SearchRequest searchRequest);


}
