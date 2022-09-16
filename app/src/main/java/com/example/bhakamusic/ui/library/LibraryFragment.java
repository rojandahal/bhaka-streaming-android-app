package com.example.bhakamusic.ui.library;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }
}