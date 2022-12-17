package com.example.bhakamusic.ui.library;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bhakamusic.Apis.RetrofitClient;
import com.example.bhakamusic.ModelResponse.PlaylistRequest;
import com.example.bhakamusic.ModelResponse.PlaylistResponse;
import com.example.bhakamusic.R;
import com.example.bhakamusic.RoomDatabase.UserDB.UserCredentials;

import java.util.ArrayList;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaylistActivity extends AppCompatActivity implements ExampleDialog.ExampleDialogListner {

    private RecyclerView recyclerView;
    PlaylistAdapter playlistAdapter;
    ArrayList<String> playlist;
    ImageView back;
    ArrayList<String> playlistName;
    TextView errortext;
    Button newPlaylist;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);
        Objects.requireNonNull(getSupportActionBar()).hide();
        playlistName = new ArrayList<>();
        playlist = UserCredentials.getPlaylist();
        recyclerView = findViewById(R.id.play_list);
        playlistName = UserCredentials.getNames();
        errortext = findViewById(R.id.error_text);
        back = findViewById(R.id.playlist_back);
        newPlaylist = findViewById(R.id.newPlaylist);
        if(playlistName.isEmpty()){
            errortext.setText("No playlist found!");
            errortext.setVisibility(View.VISIBLE);
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        playlistAdapter = new PlaylistAdapter(this, playlistName);
        recyclerView.setAdapter(playlistAdapter);
        newPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void openDialog() {
        ExampleDialog exampleDialog = new ExampleDialog();
        exampleDialog.show(getSupportFragmentManager(),"PlaylistDialog");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        playlistName.clear();
    }

    @Override
    public void applyText(String name, String description) {
        PlaylistRequest pl = new PlaylistRequest();
        pl.setDescription(description);
        pl.setName(name);

        Log.d("Playlist", "applyText: " + UserCredentials.getId());
        Call<PlaylistResponse> call =RetrofitClient
                .getInstance()
                .getApi()
                .setPlayList(pl);
        call.enqueue(new Callback<PlaylistResponse>() {
            @Override
            public void onResponse(Call<PlaylistResponse> call, Response<PlaylistResponse> response) {
                Toast.makeText(getApplicationContext(),"Playlist Added", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<PlaylistResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        onBackPressed();
    }


}