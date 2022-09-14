package com.example.bhakamusic.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bhakamusic.ModelResponse.SearchResponse;

import java.util.ArrayList;

public class HomeViewModel extends ViewModel {

    private final ArrayList<SearchResponse> arrayList;
    private final MutableLiveData<ArrayList<SearchResponse>> songList;


    public HomeViewModel() {
        songList = new MutableLiveData<>();
        arrayList = new ArrayList<>();
        createData();
        songList.setValue(arrayList);
    }

    private void createData() {
        arrayList.add(new SearchResponse("1d7da5bf-1e39-4d26-80ad-c25fa8995567",
                "Bad Habits",
                "BLACKPINK",
                "uploads/images/69cd0030-337b-11ed-9441-49e069f9941e.jpg"));
        arrayList.add(new SearchResponse("cae07979-0e4c-4c31-a5d8-2cfc435291e0",
                "First Times",
                "BLACKPINK",
                "uploads/images/69cd0030-337b-11ed-9441-49e069f9941e.jpg"));
    }

    public LiveData<ArrayList<SearchResponse>> getList() {
        return songList;
    }
}