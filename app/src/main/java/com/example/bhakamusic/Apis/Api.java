package com.example.bhakamusic.Apis;

import com.example.bhakamusic.ModelResponse.LoginRequest;
import com.example.bhakamusic.ModelResponse.RegisterRequest;
import com.example.bhakamusic.ModelResponse.RegisterResponse;
import com.example.bhakamusic.ModelResponse.SongsResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {

    @POST("api/users/signup")
    Call<RegisterResponse> register(@Body RegisterRequest registerRequest);

    @POST("api/users/signin")
    Call<RegisterResponse> login(@Body LoginRequest loginRequest);

    @GET("api/songs/d96888e4-0c7e-4feb-af75-51d282826c4e")
    Call<SongsResponse> songs();


}
