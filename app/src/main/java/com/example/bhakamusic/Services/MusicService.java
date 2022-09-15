package com.example.bhakamusic.Services;

import android.app.Service;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.bhakamusic.configs.Configs;

import java.io.IOException;

public class MusicService extends Service implements MediaPlayer.OnPreparedListener {
    private static final String TAG = "SERVICE";
    MediaPlayer mediaPlayer;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String url = Configs.BASE_URL+ "api/songs/stream/android/1e32d4aa-1e01-4072-92c5-3868b0225583/d72b5f0a-bdf0-4bfb-8079-1f3a464e3a95";

        if (mediaPlayer==null)
            mediaPlayer = new MediaPlayer();

        try {
            mediaPlayer.setAudioAttributes(
                    new AudioAttributes.Builder()
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .build()
            );
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(url);
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.prepareAsync();
            mediaPlayer.setLooping(false);

        }catch (IOException e) {
            Log.d(TAG, "onStartCommand: Error in playing sound" + e.toString());
            e.printStackTrace();
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (mediaPlayer != null) mediaPlayer.release();
        super.onDestroy();
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
    }
}
