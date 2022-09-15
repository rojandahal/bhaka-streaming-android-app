package com.example.bhakamusic.ui.Player;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.bhakamusic.MainActivity;
import com.example.bhakamusic.R;
import com.example.bhakamusic.Services.MusicService;

public class PlayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        startActivity(new Intent(PlayerActivity.this,MusicService.class));
        Button button = findViewById(R.id.stopButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(new Intent(PlayerActivity.this, MainActivity.class));
            }
        });
    }
}