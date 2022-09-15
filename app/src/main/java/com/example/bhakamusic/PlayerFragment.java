package com.example.bhakamusic;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bhakamusic.configs.Configs;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.flac.FlacExtractor;
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.squareup.picasso.Picasso;

public class PlayerFragment extends Fragment {

    private static final String SONG_ID = "id";
    private static final String USER = "user";
    private static final String SONG_TITLE = "title";
    private static final String SONG_ARTIST = "artist";
    private static final String SONG_COVER = "cover";

    private static final String TAG = "Main Activity";
    private PlayerControlView playerView;
    private ExoPlayer player;
    private String streamURL = Configs.BASE_URL+ Configs.streamApiEndpoint;
    String id,user,cover,artist,title;
    ImageView backBtn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_player, container, false);
        playerView = view.findViewById(R.id.player_view);
        backBtn = view.findViewById(R.id.player_back);
        ImageView coverPic = view.findViewById(R.id.coverPhoto);
        TextView songName = view.findViewById(R.id.artistPlayerName);
        TextView artistName = view.findViewById(R.id.albumName);
        Log.d(TAG, "onCreateView: " + Configs.BASE_URL+cover);

        if( getArguments()!=null){
            id = getArguments().getString(SONG_ID);
            user= getArguments().getString(USER);
            title = getArguments().getString(SONG_TITLE);
            artist = getArguments().getString(SONG_ARTIST);
            cover = getArguments().getString(SONG_COVER);
        }

        streamURL = streamURL + id +"/" + user;

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

        player = new ExoPlayer.Builder(requireContext())
                .setMediaSourceFactory(
                        new DefaultMediaSourceFactory(requireContext(), extractorsFactory))
                .build();

        playerView.setPlayer(player);
        //Set Song Details
        Picasso.get().load(Configs.BASE_URL+cover).into(coverPic);
        songName.setText(title);
        artistName.setText(artist);
        playerView.setShowTimeoutMs(-1);
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

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        player.release();
    }
}