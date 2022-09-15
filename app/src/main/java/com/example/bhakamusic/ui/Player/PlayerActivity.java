package com.example.bhakamusic.ui.Player;

import static com.google.android.exoplayer2.util.RepeatModeUtil.REPEAT_TOGGLE_MODE_ALL;
import static com.google.android.exoplayer2.util.RepeatModeUtil.REPEAT_TOGGLE_MODE_ONE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.bhakamusic.R;
import com.example.bhakamusic.Services.MusicService;
import com.example.bhakamusic.configs.Configs;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.flac.FlacExtractor;
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.HttpDataSource;

public class PlayerActivity extends AppCompatActivity {

    private static final String TAG = "Main Activity";
    private PlayerControlView playerView;
    private ExoPlayer player;
    private String streamURL = Configs.BASE_URL+ Configs.streamApiEndpoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_player);
        String id = getIntent().getStringExtra("id");
        String user = getIntent().getStringExtra("user");
        streamURL = streamURL + id +"/" + user;

        playerView = findViewById(R.id.player_view);
        Log.d(TAG, "onCreate: STREAM URL" + streamURL);
        DefaultExtractorsFactory extractorsFactory =
                new DefaultExtractorsFactory()
                        .setFlacExtractorFlags(FlacExtractor.FLAG_DISABLE_ID3_METADATA)
                        .setConstantBitrateSeekingAlwaysEnabled(true);

        DataSource.Factory dataSourceFactory = () -> {
            DefaultHttpDataSource.Factory httpDataSourceFactory = new DefaultHttpDataSource.Factory();
            HttpDataSource dataSource = httpDataSourceFactory.createDataSource();
            // Set a custom authentication request header.
            dataSource.setRequestProperty("Range", "bytes=" +0+ "-");
            return dataSource;
        };

        ProgressiveMediaSource mediaSource = new ProgressiveMediaSource.Factory(
                dataSourceFactory,extractorsFactory)
                .setContinueLoadingCheckIntervalBytes(1)
                .createMediaSource(MediaItem.fromUri(streamURL));

        player = new ExoPlayer.Builder(this)
                .setMediaSourceFactory(
                        new DefaultMediaSourceFactory(this, extractorsFactory))
                .build();
        playerView.setPlayer(player);
        playerView.setShowTimeoutMs(-1);
        Log.d(TAG, "onCreate: " + playerView.getShowTimeoutMs());
        // Set the media source to be a source
        player.setMediaSource(mediaSource);

        //Add to playlist of mediaSource
        player.addMediaSource(new ProgressiveMediaSource.Factory(
                dataSourceFactory,extractorsFactory)
                .setContinueLoadingCheckIntervalBytes(1)
                .createMediaSource(MediaItem.fromUri(Configs.BASE_URL+Configs.streamApiEndpoint+ "51fa4c30-22eb-4749-8ba5-ddcec8ef6c16" +
                        "/" + user)
        ));
        // Prepare the player.
        player.prepare();
        player.play();

    }

    @Override
    protected void onStop() {
        super.onStop();
        player.release();
    }
}