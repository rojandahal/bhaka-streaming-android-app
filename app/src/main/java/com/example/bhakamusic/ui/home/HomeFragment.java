package com.example.bhakamusic.ui.home;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.SearchView;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bhakamusic.Apis.RetrofitClient;
import com.example.bhakamusic.Interface.RecyclerViewInterface;
import com.example.bhakamusic.ModelResponse.SearchRequest;
import com.example.bhakamusic.ModelResponse.SearchResponse;

import com.example.bhakamusic.ui.Player.PlayerActivity;
import com.example.bhakamusic.R;
import com.example.bhakamusic.databinding.FragmentHomeBinding;
;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements RecyclerViewInterface {

    private FragmentHomeBinding binding;
    private static final String TAG = "HOME FRAGMENT";
    private ArrayList<SearchResponse> songList;
    private RecyclerView recyclerView;
    private SongRecyclerAdapter recyclerAdapter;
    private SearchView searchView;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        songList = new ArrayList<>();

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = root.findViewById(R.id.recyclerView);
        searchView = root.findViewById(R.id.search);

        recyclerAdapter = new SongRecyclerAdapter(songList,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));

        searchItem();

        return root;
    }



    private void searchItem() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                songList.clear();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(!s.isEmpty()){
                    songList.clear();
                    SearchRequest searchRequest = new SearchRequest(s);
                    Call<List<SearchResponse>> call = RetrofitClient
                            .getInstance()
                            .getApi()
                            .searchSong(searchRequest);
                    call.enqueue(new Callback<List<SearchResponse>>() {
                        @Override
                        public void onResponse(Call<List<SearchResponse>> call, Response<List<SearchResponse>> response) {
                            if(response.isSuccessful()){
                                if(response.body().isEmpty()){
                                    Log.d(TAG, "onResponse: ERROR" + "EMPTY");
                                }else {
                                    for (SearchResponse rs : response.body()) {
                                        Log.d(TAG, "onResponse: SONG" + rs.getTitle());
                                        songList.add(rs);
                                    }
                                }
                            }
                            recyclerView.setAdapter(recyclerAdapter);
                        }

                        @Override
                        public void onFailure(Call<List<SearchResponse>> call, Throwable t) {
                            Log.d(TAG, "onFailure: ERROR" + t.getMessage());
                        }
                    });
                }else
                    songList.clear();

                return true;
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemClick(int position) {
        SearchResponse response = songList.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("calling-activity","HOME_FRAGMENT");
        bundle.putString("id",response.getId());
        bundle.putString("user","d72b5f0a-bdf0-4bfb-8079-1f3a464e3a95");
        bundle.putString("title", response.getTitle());
        bundle.putString("cover",response.getCoverArt());
        bundle.putString("artist",response.getArtist());
        searchView.clearFocus();
        Intent intent = new Intent(getActivity(), PlayerActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }
}