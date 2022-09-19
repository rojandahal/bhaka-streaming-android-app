package com.example.bhakamusic.ui.favourite;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.bhakamusic.MainActivity2;
import com.example.bhakamusic.R;
import com.example.bhakamusic.RoomDatabase.FavouriteDB;
import com.example.bhakamusic.RoomDatabase.FavouriteData;
import com.example.bhakamusic.databinding.FragmentFavouriteBinding;

import java.util.ArrayList;
import java.util.List;

public class FavouriteFragment extends Fragment {

    private FragmentFavouriteBinding binding;
    RecyclerView recyclerView;
    FavouriteDB favDB;
    FavouriteAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentFavouriteBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recyclerView = root.findViewById(R.id.fav_list);

        //Initialize db
        favDB = FavouriteDB.getInstance(root.getContext());
        //Linear layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new FavouriteAdapter(this.getActivity(),favDB.favDao().getAll());
        recyclerView.setAdapter(adapter);

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}