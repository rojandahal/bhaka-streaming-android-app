package com.example.bhakamusic.ui.library;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bhakamusic.R;
import com.example.bhakamusic.RoomDatabase.RecentlyPlayedDB.RecentlyPlayedDB;
import com.example.bhakamusic.databinding.FragmentLibraryBinding;

public class LibraryFragment extends Fragment {
    private FragmentLibraryBinding binding;
    private RecyclerView recyclerView;
    RecentlyPlayedDB recentlyPlayedDB;
    LibraryAdapter libraryAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLibraryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recyclerView = root.findViewById(R.id.recently_played_recyclerView);
        //Initialize db
        recentlyPlayedDB = RecentlyPlayedDB.getInstance(root.getContext());
        TextView nodata = root.findViewById(R.id.no_data);
        if(recentlyPlayedDB.recentlyDao().getAll().isEmpty()){
            nodata.setVisibility(View.VISIBLE);
            nodata.setTextSize(14);
            nodata.setText("No data Available");
        }else{
            //Linear layout manager
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext());
            recyclerView.setLayoutManager(linearLayoutManager);
            libraryAdapter= new LibraryAdapter(root.getContext(),recentlyPlayedDB.recentlyDao().getAll());
            recyclerView.setAdapter(libraryAdapter);
        }
        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }
}