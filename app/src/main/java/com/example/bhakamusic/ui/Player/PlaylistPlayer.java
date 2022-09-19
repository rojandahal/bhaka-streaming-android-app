package com.example.bhakamusic.ui.Player;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.bhakamusic.MainActivity2;
import com.example.bhakamusic.RoomDatabase.FavouriteData;
import com.example.bhakamusic.configs.Configs;
import com.example.bhakamusic.ui.SplashScreen;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.flac.FlacExtractor;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.HttpDataSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlaylistPlayer {
    private static ExoPlayer player;
    private boolean first = true;
    private Activity activity;
    private List<MediaSource> mediaSourceList;

    public PlaylistPlayer(Activity activity) {
        mediaSourceList = new ArrayList<>();
        this.activity = activity;
        player = Player.getExoPlayer(activity);
    }

    public void setSongs(List<FavouriteData> dataList, String user, Bundle bundle, int position) {

//        DefaultExtractorsFactory extractorsFactory =
//                new DefaultExtractorsFactory()
//                        .setFlacExtractorFlags(FlacExtractor.FLAG_DISABLE_ID3_METADATA)
//                        .setConstantBitrateSeekingAlwaysEnabled(true);
//
//        DataSource.Factory dataSourceFactory = () -> {
//            DefaultHttpDataSource.Factory httpDataSourceFactory = new DefaultHttpDataSource.Factory();
//            HttpDataSource dataSource = httpDataSourceFactory.createDataSource();
//            // Set a custom authentication request header.
//            dataSource.setRequestProperty("Range", "bytes=" + 0 + "-");
//            return dataSource;
//        };
//
//        String url = Configs.BASE_URL + Configs.streamApiEndpoint + dataList.get(position).getSongId() + "/" + user;
//        ProgressiveMediaSource mS = new ProgressiveMediaSource.Factory(
//                dataSourceFactory, extractorsFactory)
//                .setContinueLoadingCheckIntervalBytes(1)
//                .createMediaSource(MediaItem.fromUri(url));
//
//        player.setMediaSource(mS);

        DefaultExtractorsFactory extractorsFactory = Player.getExtractorsFactory();
        DataSource.Factory dataSourceFactory = Player::getDataSourceFactory;
        //Start Activity when all data is fetched
        for (FavouriteData d : dataList) {
            String streamURL = Configs.BASE_URL + Configs.streamApiEndpoint + d.getSongId() + "/" + user;
            ProgressiveMediaSource mediaSource = new ProgressiveMediaSource.Factory(
                    dataSourceFactory, extractorsFactory)
                    .setContinueLoadingCheckIntervalBytes(1)
                    .createMediaSource(MediaItem.fromUri(streamURL));
            mediaSourceList.add(mediaSource);
        }
        // Set the media source to be a source
        player.setMediaSources(mediaSourceList,position,0);

        bundle.putInt("index",position);
        Intent intent = new Intent(activity, PlayerActivity.class);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }
}
