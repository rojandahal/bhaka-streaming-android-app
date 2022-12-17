package com.example.bhakamusic.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.bhakamusic.Apis.RetrofitClient;
import com.example.bhakamusic.MainActivity;
import com.example.bhakamusic.MainActivity2;
import com.example.bhakamusic.ModelResponse.UserResponse;
import com.example.bhakamusic.R;
import com.example.bhakamusic.RoomDatabase.UserDB.UserCredentials;
import com.example.bhakamusic.ui.LoginSignup.SignIn;
import com.example.bhakamusic.ui.LoginSignup.SignUp;
import com.example.bhakamusic.ui.Player.Player;
import com.google.android.exoplayer2.ExoPlayer;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Start Activity when all data is fetched
                //Shared preference to store token and username and userID
                SharedPreferences sharedPref = getApplication().getSharedPreferences(String.valueOf(R.string.token_sharedpref), Context.MODE_PRIVATE);
                String token = sharedPref.getString(String.valueOf(R.string.token),"token");
                Log.d("LD", "onClick: " + token);
                UserCredentials.setToken(token);
                Call<UserResponse> call = RetrofitClient
                        .getInstance()
                        .getApi()
                        .getUserDetails("Bearer " + token);
                call.enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        if(response.isSuccessful()){
                            assert response.body() != null;
                            Log.d("TAG", "onResponse: " + response.body().getCreatedPlaylists());
                            UserResponse userResponse = response.body();
                            UserCredentials.setId(userResponse.getId());
                            UserCredentials.setUsername(userResponse.getUsername());
                            UserCredentials.setEmail(userResponse.getEmail());
                            UserCredentials.setPreference(userResponse.getPreference());
                            UserCredentials.setPlaylist(userResponse.getCreatedPlaylists());
                            UserCredentials.setToken(token);
                            SharedPreferences sref = getApplication().getSharedPreferences(String.valueOf(getString(R.string.id)),Context.MODE_PRIVATE);
                            SharedPreferences.Editor edit = sref.edit();
                            edit.putString(String.valueOf(R.string.id),userResponse.getId());
                            edit.apply();
                            Log.d("TAG", "onResponse: " + UserCredentials.getPlaylist());
                        }
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {

                    }
                });

                if(Objects.equals(token, "token")){
                    startActivity(new Intent(SplashScreen.this, LandingPage.class));
                }else{
                    startActivity(new Intent(SplashScreen.this, MainActivity2.class));
                    UserCredentials.setToken(token);
                }
                finish();
            }
        }, 2000);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}