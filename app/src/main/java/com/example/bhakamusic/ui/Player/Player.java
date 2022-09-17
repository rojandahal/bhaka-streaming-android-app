package com.example.bhakamusic.ui.Player;

import android.app.Activity;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.flac.FlacExtractor;
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory;

public abstract class Player {
    public static ExoPlayer exoPlayer;
    public static DefaultExtractorsFactory extractorsFactory;

    public static ExoPlayer getExoPlayer(Activity activity) {
        if(exoPlayer==null){
            extractorsFactory =
                    new DefaultExtractorsFactory()
                            .setFlacExtractorFlags(FlacExtractor.FLAG_DISABLE_ID3_METADATA)
                            .setConstantBitrateSeekingAlwaysEnabled(true);

            exoPlayer = new ExoPlayer.Builder(activity)
                    .setMediaSourceFactory(
                            new DefaultMediaSourceFactory(activity, extractorsFactory))
                    .build();
        }
        return exoPlayer;
    }

    public static DefaultExtractorsFactory getExtractorsFactory() {
        return extractorsFactory;
    }
}
