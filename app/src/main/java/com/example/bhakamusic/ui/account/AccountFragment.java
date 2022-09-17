package com.example.bhakamusic.ui.account;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.bhakamusic.R;
import com.example.bhakamusic.RoomDatabase.FavouriteDB;
import com.example.bhakamusic.RoomDatabase.FavouriteData;
import com.example.bhakamusic.RoomDatabase.RecentlyPlayedDB.RecentlyPlayed;
import com.example.bhakamusic.RoomDatabase.RecentlyPlayedDB.RecentlyPlayedDB;
import com.example.bhakamusic.configs.Configs;
import com.example.bhakamusic.databinding.FragmentAccountBinding;
import com.example.bhakamusic.ui.Player.Player;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
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


public class AccountFragment extends Fragment implements View.OnClickListener{

    private FragmentAccountBinding binding;
    private static final String SONG_ID = "id";
    private static final String USER = "user";
    private static final String SONG_TITLE = "title";
    private static final String SONG_ARTIST = "artist";
    private static final String SONG_COVER = "cover";

    private static final String TAG = "Main Activity";
    private PlayerControlView playerView;
    private ExoPlayer player;
    private String streamURL = Configs.BASE_URL+ Configs.streamApiEndpoint;
    protected String id,user,cover,artist,title;
    protected ImageView backBtn,favBtn;
    List<FavouriteData> favouriteList = new ArrayList<>();
    FavouriteDB favDB;
    protected RecentlyPlayedDB recentlyPlayedDB;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AccountViewModel accountViewModel =
                new ViewModelProvider(this).get(AccountViewModel.class);

        binding = FragmentAccountBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        playerView = view.findViewById(R.id.player_view);
        backBtn = view.findViewById(R.id.player_back);
        ImageView coverPic = view.findViewById(R.id.coverPhoto);
        TextView songName = view.findViewById(R.id.artistPlayerName);
        TextView artistName = view.findViewById(R.id.albumName);
        favBtn = view.findViewById(R.id.player_fav);
        player = Player.getExoPlayer(getActivity());
        //Get Database context
        recentlyPlayedDB = RecentlyPlayedDB.getInstance(getContext());
        List<RecentlyPlayed> recentlyPlayedData = recentlyPlayedDB.recentlyDao().getAll();
        RecentlyPlayed rp = recentlyPlayedData.get(0);
        id = rp.getSongId();
        title = rp.getSongTitle();
        artist = rp.getArtistName();
        cover = rp.getCoverArt();
//        if( getArguments()!=null){
//            id = getArguments().getString(SONG_ID);
//            user= getArguments().getString(USER);
//            title = getArguments().getString(SONG_TITLE);
//            artist = getArguments().getString(SONG_ARTIST);
//            cover = getArguments().getString(SONG_COVER);
//        }
//
//        streamURL = streamURL + id +"/" + user;

        //Set Song Details
        Picasso.get().load(Configs.BASE_URL+cover).into(coverPic);
        songName.setText(title);
        artistName.setText(artist);
        playerView.setShowTimeoutMs(-1);
        if(player.isPlaying()){
            playerView.setPlayer(player);
            playerView.onAttachedToWindow();
        }


        backBtn.setOnClickListener(this);
        favBtn.setOnClickListener(this);

        for(FavouriteData data: favouriteList){
            if(Objects.equals(id, data.getSongId())){
                favBtn.setImageResource(R.drawable.ic_baseline_favorite_24);
            }
        }

        return view;
    }


    @Override
    public void onStop() {
        super.onStop();
        recentlyPlayedDB.recentlyDao().reset(recentlyPlayedDB.recentlyDao().getAll());
        RecentlyPlayed recentlyPlayed = new RecentlyPlayed();
        recentlyPlayed.setArtistName(artist);
        recentlyPlayed.setSongTitle(title);
        recentlyPlayed.setCoverArt(cover);
        recentlyPlayed.setSongId(id);
        recentlyPlayedDB.recentlyDao().insert(recentlyPlayed);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.player_back:
                requireActivity().getSupportFragmentManager().popBackStack();
                break;
            case R.id.player_fav:
                //Do something
                long millis = Objects.requireNonNull(playerView.getPlayer()).getDuration();
                long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
                long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
                String duration = minutes + ":" + seconds;
                FavouriteData data = new FavouriteData();
                data.setSongId(id);
                data.setArtistName(artist);
                data.setCoverArt(cover);
                data.setSongTitle(title);
                data.setSongDuration(duration);
                Log.d(TAG, "onClick: Name"+ data.getSongTitle());

                for(FavouriteData favDat: favouriteList){
                    if(Objects.equals(id, favDat.getSongId())){
                        favDB.favDao().delete(favDat);
                        favouriteList.remove(favDat);
                        favBtn.setImageResource(R.drawable.ic_favourite_unclick);
                    }else
                    {
                        favDB.favDao().insert(data);
                        favBtn.setImageResource(R.drawable.ic_baseline_favorite_24);
                    }
                }
                break;

        }
    }
}