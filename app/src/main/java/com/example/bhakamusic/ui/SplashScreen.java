package com.example.bhakamusic.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.bhakamusic.MainActivity;
import com.example.bhakamusic.MainActivity2;
import com.example.bhakamusic.R;
import com.example.bhakamusic.ui.LoginSignup.SignIn;

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
                startActivity(new Intent(SplashScreen.this, MainActivity2.class));
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