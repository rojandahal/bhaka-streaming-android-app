package com.example.bhakamusic.ui.library;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bhakamusic.Apis.RetrofitClient;
import com.example.bhakamusic.MainActivity2;
import com.example.bhakamusic.ModelResponse.PlaylistResponse;
import com.example.bhakamusic.R;
import com.example.bhakamusic.RoomDatabase.RecentlyPlayedDB.RecentlyPlayedDB;
import com.example.bhakamusic.RoomDatabase.UserDB.UserCredentials;
import com.example.bhakamusic.databinding.FragmentLibraryBinding;
import com.example.bhakamusic.ui.Player.PlayerActivity;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LibraryFragment extends Fragment {
    private FragmentLibraryBinding binding;
    private RecyclerView recyclerView;
    RecentlyPlayedDB recentlyPlayedDB;
    ArrayList<String> playlist_list = UserCredentials.getPlaylist();;
    LibraryAdapter libraryAdapter;
    ImageView playlist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLibraryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recyclerView = root.findViewById(R.id.recently_played_recyclerView);
        playlist = root.findViewById(R.id.playlist_click);
        //Initialize db
        recentlyPlayedDB = RecentlyPlayedDB.getInstance(requireActivity().getApplication());
        Log.d("TAG", "onCreateView: " + recentlyPlayedDB.recentlyDao().getAll());
            //Linear layout manager
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext());
            recyclerView.setLayoutManager(linearLayoutManager);
            libraryAdapter= new LibraryAdapter(getActivity(),recentlyPlayedDB.recentlyDao().getAll());
            recyclerView.setAdapter(libraryAdapter);

        playlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Handler().postAtFrontOfQueue(new Runnable() {
                    @Override
                    public void run() {
                        for (String s : playlist_list) {
                            Call<PlaylistResponse> responseBodyCall = RetrofitClient
                                    .getInstance()
                                    .getApi()
                                    .getPlaylistName(s);
                            responseBodyCall.enqueue(new Callback<PlaylistResponse>() {
                                @Override
                                public void onResponse(Call<PlaylistResponse> call, Response<PlaylistResponse> response) {
                                    assert response.body() != null;
                                    UserCredentials.setToList(response.body().getTitle());
                                }

                                @Override
                                public void onFailure(Call<PlaylistResponse> call, Throwable t) {

                                }
                            });
                        }
                        startActivity(new Intent(getActivity(), PlaylistActivity.class));
                    }
                });
            }
        });

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }
}