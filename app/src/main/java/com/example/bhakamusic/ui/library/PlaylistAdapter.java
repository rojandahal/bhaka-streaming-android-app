package com.example.bhakamusic.ui.library;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bhakamusic.R;
import com.example.bhakamusic.RoomDatabase.FavouriteDB;
import com.example.bhakamusic.RoomDatabase.FavouriteData;
import com.example.bhakamusic.configs.Configs;
import com.example.bhakamusic.ui.Player.PlaylistPlayer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.ViewHolder> {
    private static final String TAG = "PLAYLIST_ADAPTER";
    private static ArrayList<String> playlist;
    private Context context;

    public PlaylistAdapter(Activity context, ArrayList<String> dataList) {
        this.playlist = dataList;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PlaylistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.playlist_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistAdapter.ViewHolder holder, int position) {
        //Initialize data
        if(!playlist.isEmpty()){
            for (String s: playlist) {
                    holder.playlistTitle.setText(s);
            }
        }
        holder.playlistCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return playlist.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView playlistTitle;
        CardView playlistCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            playlistTitle = itemView.findViewById(R.id.play_song);
            playlistCard = itemView.findViewById(R.id.playlist_card);
        }
    }

}