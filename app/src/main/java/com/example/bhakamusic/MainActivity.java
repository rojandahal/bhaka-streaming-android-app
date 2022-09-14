package com.example.bhakamusic;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.bhakamusic.Apis.RetrofitClient;
import com.example.bhakamusic.ModelResponse.SongsResponse;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.flac.FlacExtractor;
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.HttpDataSource;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity  {

    private static final String TAG = "Main Activity";
    private StyledPlayerView playerView;
    private ExoPlayer player;
    private WebView webView;
    private int songSize,end,start,range=0;
    private String streamURL = "http:// 192.168.1.83/api/songs/stream/d96888e4-0c7e-4feb-af75-51d282826c4e";

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(MainActivity.this, "Logged In!", Toast.LENGTH_SHORT).show();
        playerView = findViewById(R.id.player_view);

        Call<SongsResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .song();
        call.enqueue(new Callback<SongsResponse>() {
            @Override
            public void onResponse(Call<SongsResponse> call, Response<SongsResponse> response) {
                SongsResponse rs = response.body();
                assert rs != null;
                Log.d(TAG, "onResponse: " + rs.getSongSize());
                songSize = Integer.parseInt(rs.getSongSize());
            }

            @Override
            public void onFailure(Call<SongsResponse> call, Throwable t) {

            }
        });

        DefaultExtractorsFactory extractorsFactory =
                new DefaultExtractorsFactory()
                        .setFlacExtractorFlags(FlacExtractor.FLAG_DISABLE_ID3_METADATA)
                        .setConstantBitrateSeekingAlwaysEnabled(true);


        start=0;
        DataSource.Factory dataSourceFactory = () -> {
            DefaultHttpDataSource.Factory httpDataSourceFactory = new DefaultHttpDataSource.Factory();
            HttpDataSource dataSource = httpDataSourceFactory.createDataSource();
            // Set a custom authentication request header.
            dataSource.setRequestProperty("Range", "bytes=" + 0 + "-");
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
        // Set the media source to be played.
        player.setMediaSource(mediaSource);
        // Prepare the player.
        player.prepare();
        player.play();
        Log.d(TAG, "onCreate: " + mediaSource.getMediaItem());

    }

    @Override
    protected void onStop() {
        super.onStop();
        player.release();
    }

}