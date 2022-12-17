package com.example.bhakamusic;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bhakamusic.Apis.RetrofitClient;
import com.example.bhakamusic.ModelResponse.UserResponse;
import com.example.bhakamusic.RoomDatabase.RecentlyPlayedDB.RecentlyPlayedDB;
import com.example.bhakamusic.RoomDatabase.UserDB.UserCredentials;
import com.example.bhakamusic.configs.Configs;

import com.example.bhakamusic.databinding.ActivityMain2Binding;
import com.example.bhakamusic.ui.Player.Player;
import com.example.bhakamusic.ui.Player.PlayerActivity;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity2 extends AppCompatActivity {

    private static final String SONG_ID = "id";
    private static final String USER = "user";
    private static final String SONG_TITLE = "title";
    private static final String SONG_ARTIST = "artist";
    private static final String SONG_COVER = "cover";
    public static final String TAG = "MAIN_ACTIVITY_2";
    private ActivityMain2Binding binding;
    private PlayerControlView playerControlView;
    protected String id, cover, title,artist;
    private ImageView artistImage;
    private TextView songName;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
        //Find view by id
        BottomNavigationView navView = findViewById(R.id.nav_view);
        artistImage = findViewById(R.id.mini_artist_image);
        songName = findViewById(R.id.mini_song_name);
        linearLayout = findViewById(R.id.mini_player_layout);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications,R.id.navigation_library)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main2);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        //Shared preference to store token and username and userID
        SharedPreferences sharedPref = getApplication().getSharedPreferences(String.valueOf(R.string.token_sharedpref), Context.MODE_PRIVATE);
        String token = sharedPref.getString(getString(R.string.token),"token");
        UserCredentials.setToken(token);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Call<UserResponse> call = RetrofitClient
                            .getInstance()
                            .getApi()
                            .getUserDetails("Bearer " + token);
                    call.enqueue(new Callback<UserResponse>() {
                        @Override
                        public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                            if(response.isSuccessful()){
                                assert response.body() != null;
                                UserResponse userResponse = response.body();
                                UserCredentials.setId(userResponse.getId());
                                UserCredentials.setUsername(userResponse.getUsername());
                                UserCredentials.setEmail(userResponse.getEmail());
                                UserCredentials.setPreference(userResponse.getPreference());
                                UserCredentials.setPlaylist(userResponse.getCreatedPlaylists());
                                UserCredentials.setToken(token);
                                SharedPreferences sref = getApplication().getSharedPreferences(String.valueOf(getString(R.string.id)),Context.MODE_PRIVATE);
                                SharedPreferences.Editor edit = sref.edit();
                                edit.putString(String.valueOf(R.string.id),userResponse.getId());
                                edit.apply();
                                Log.d(TAG, "onCreate: " + UserCredentials.getPlaylist());
                            }
                        }

                        @Override
                        public void onFailure(Call<UserResponse> call, Throwable t) {

                        }
                    });
                }
            },2000);

        try {
            if (getIntent() != null) {
                id = getIntent().getExtras().getString(SONG_ID);
                title = getIntent().getExtras().getString(SONG_TITLE);
                artist = getIntent().getExtras().getString(SONG_ARTIST);
                cover = getIntent().getExtras().getString(SONG_COVER);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        //Set data to view
        //Set Song Details
        Picasso.get().load(Configs.BASE_URL + cover).into(artistImage);
        songName.setText(title);
        //Setup player view to player
        playerControlView = findViewById(R.id.mini_player_view);
        playerControlView.setShowTimeoutMs(-1);
        ExoPlayer player = Player.getExoPlayer(this);
        playerControlView.setPlayer(player);
        RecentlyPlayedDB.getInstance(this).recentlyDao().reset(RecentlyPlayedDB.getInstance(this).recentlyDao().getAll());

        artistImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("calling-activity",getString(R.string.main_activity));
                bundle.putString("id",id);
                bundle.putString("title",title);
                bundle.putString("cover",cover);
                bundle.putString("artist",artist);
                Intent intent = new Intent(MainActivity2.this,PlayerActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }



}