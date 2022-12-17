package com.example.bhakamusic.ui.Player;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bhakamusic.MainActivity2;
import com.example.bhakamusic.R;
import com.example.bhakamusic.RoomDatabase.FavouriteDB;
import com.example.bhakamusic.RoomDatabase.FavouriteData;
import com.example.bhakamusic.RoomDatabase.RecentlyPlayedDB.RecentlyPlayed;
import com.example.bhakamusic.RoomDatabase.RecentlyPlayedDB.RecentlyPlayedDB;
import com.example.bhakamusic.RoomDatabase.UserDB.UserCredentials;
import com.example.bhakamusic.configs.Configs;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Tracks;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class PlayerActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String SONG_ID = "id";
    private static final String USER = "user";
    private static final String SONG_TITLE = "title";
    private static final String SONG_ARTIST = "artist";
    private static final String SONG_COVER = "cover";
    protected String user = UserCredentials.getId();
    private static final String TAG = "Main Activity";
    private PlayerControlView playerView;
    private ExoPlayer player;
    private String streamURL = Configs.BASE_URL + Configs.streamApiEndpoint;
    protected String id, cover, artist, title;
    protected ImageView backBtn, favBtn;
    List<FavouriteData> favouriteList = new ArrayList<>();
    FavouriteDB favDB;
    protected RecentlyPlayedDB recentlyPlayedDB = RecentlyPlayedDB.getInstance(getApplication());
    RecentlyPlayed recentlyPlayed;
    int index;
    List<RecentlyPlayed> recentlyPlayedList;
    ImageView coverPic;
    TextView songName;
    TextView artistName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        Objects.requireNonNull(getSupportActionBar()).hide();
        playerView = findViewById(R.id.player_view);
        backBtn = findViewById(R.id.player_back);
        coverPic = findViewById(R.id.coverPhoto);
        songName = findViewById(R.id.artistPlayerName);
        artistName = findViewById(R.id.albumName);
        favBtn = findViewById(R.id.player_fav);
        //Get player instance
        player = Player.getExoPlayer(this);
        //Get Database context
        favDB = FavouriteDB.getInstance(this);
        //Get data list
        favouriteList = favDB.favDao().getAll();

        recentlyPlayed = new RecentlyPlayed();

        if((!Objects.equals(getIntent().getExtras().getString("calling-activity"), getString(R.string.favourite_activity)))){
            getIntentData();
            initializePlayer();
            setPlayerView();
        }else{
            getIntentData();
            setPlayerView();
            favouritePlaying();
        }
        backBtn.setOnClickListener(this);
        favBtn.setOnClickListener(this);

        for (FavouriteData data : favouriteList) {
            if (Objects.equals(id, data.getSongId())) {
                favBtn.setImageResource(R.drawable.ic_baseline_favorite_24);
            } else {
                favBtn.setImageResource(R.drawable.ic_favourite_unclick);
            }
        }
    }

    private void favouritePlaying() {
        player.addListener(new com.google.android.exoplayer2.Player.Listener() {
            @Override
            public void onTracksChanged(@NonNull Tracks tracks) {
                FavouriteData favouriteData = favouriteList.get(player.getCurrentMediaItemIndex());
                title = favouriteData.getSongTitle();
                artist = favouriteData.getArtistName();
                cover = favouriteData.getCoverArt();
                Picasso.get().load(Configs.BASE_URL + cover).into(coverPic);
                songName.setText(title);
                artistName.setText(artist);
            }
        });
    }

    private void getIntentData() {
        if (getIntent() != null) {
            id = getIntent().getExtras().getString(SONG_ID);
            title = getIntent().getExtras().getString(SONG_TITLE);
            artist = getIntent().getExtras().getString(SONG_ARTIST);
            cover = getIntent().getExtras().getString(SONG_COVER);
            index= getIntent().getExtras().getInt("index");
        }
    }


    private void initializePlayer() {
        if ((!Objects.equals(getIntent().getExtras().getString("calling-activity"), getString(R.string.main_activity)))) {
            streamURL = streamURL + id + "/" + user;
            DefaultExtractorsFactory extractorsFactory = Player.getExtractorsFactory();

            DataSource.Factory dataSourceFactory = () -> {
                DefaultHttpDataSource.Factory httpDataSourceFactory = new DefaultHttpDataSource.Factory();
                HttpDataSource dataSource = httpDataSourceFactory.createDataSource();
                // Set a custom authentication request header.
                dataSource.setRequestProperty("Range", "bytes=" + 0 + "-");
                return dataSource;
            };

            ProgressiveMediaSource mediaSource = new ProgressiveMediaSource.Factory(
                    dataSourceFactory, extractorsFactory)
                    .setContinueLoadingCheckIntervalBytes(1)
                    .createMediaSource(MediaItem.fromUri(streamURL));
            // Set the media source to be a source
            player.setMediaSource(mediaSource);
        }
    }

    private void setPlayerView() {
        playerView.setPlayer(player);
        //Set Song Details
        Picasso.get().load(Configs.BASE_URL + cover).into(coverPic);
        songName.setText(title);
        artistName.setText(artist);
        playerView.setShowTimeoutMs(-1);
        player.prepare();
        player.play();
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        recentlyPlayed.setSongTitle(title);
        recentlyPlayed.setCoverArt(cover);
        recentlyPlayed.setSongId(id);
        recentlyPlayed.setArtistName(artist);
        recentlyPlayedDB.recentlyDao().insert(recentlyPlayed);

        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putString("title", title);
        bundle.putString("cover", cover);
        bundle.putString("artist", artist);
        Intent intent = new Intent(this, MainActivity2.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.player_back:
                onBackPressed();
                break;
            case R.id.player_fav:
                //Do something
                long millis = player.getDuration();
                long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
                long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
                String duration = minutes + ":" + seconds;
                FavouriteData data = new FavouriteData();
                data.setSongId(id);
                data.setArtistName(artist);
                data.setCoverArt(cover);
                data.setSongTitle(title);
                data.setSongDuration(duration);
                Log.d(TAG, "onClick: Name" + data.getSongTitle());
                boolean isPresent = false;
                for (FavouriteData d : favDB.favDao().getAll()) {
                    if (Objects.equals(id, d.getSongId())) {
                        favBtn.setImageResource(R.drawable.ic_favourite_unclick);
                        favDB.favDao().delete(d);
                        isPresent = true;
                        break;
                    }
                }
                if (!isPresent) {
                    favDB.favDao().insert(data);
                    favouriteList = favDB.favDao().getAll();
                    favBtn.setImageResource(R.drawable.ic_baseline_favorite_24);
                }
                break;

        }
    }
}