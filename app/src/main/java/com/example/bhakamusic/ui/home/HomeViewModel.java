package com.example.bhakamusic.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bhakamusic.ModelResponse.SearchResponse;
import com.google.android.exoplayer2.ExoPlayer;

import java.util.ArrayList;

public class HomeViewModel extends ViewModel {
    private final MutableLiveData<ExoPlayer> player = new MutableLiveData<>();

    public MutableLiveData<ExoPlayer> getPlayer() {
        return player;
    }
}