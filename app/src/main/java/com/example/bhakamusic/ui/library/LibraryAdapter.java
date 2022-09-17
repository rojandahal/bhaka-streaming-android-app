package com.example.bhakamusic.ui.library;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bhakamusic.R;
import com.example.bhakamusic.RoomDatabase.FavouriteDB;
import com.example.bhakamusic.RoomDatabase.FavouriteData;
import com.example.bhakamusic.RoomDatabase.RecentlyPlayedDB.RecentlyPlayed;
import com.example.bhakamusic.RoomDatabase.RecentlyPlayedDB.RecentlyPlayedDB;
import com.example.bhakamusic.configs.Configs;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.ViewHolder> {
    private static final String TAG = "LIBRARY_ADAPTER";
    private static List<RecentlyPlayed> dataList;
    private Context context;
    private RecentlyPlayedDB recentlyPlayedDB;

    public LibraryAdapter(Context context, List<RecentlyPlayed> recentlyPlayedList) {
        this.context = context;
        dataList = recentlyPlayedList;
        recentlyPlayedDB = RecentlyPlayedDB.getInstance(context);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recently_played_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Initialize data
        Log.d(TAG, "onBindViewHolder: " + getItemCount());
        RecentlyPlayed rp= recentlyPlayedDB.recentlyDao().getAll().get(position);
        holder.recentTitle.setText(rp.getSongTitle());
        holder.recentArtist.setText(rp.getArtistName());
        Picasso.get().load(Configs.BASE_URL + rp.getCoverArt()).into(holder.recentImage);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView recentImage;
        TextView recentTitle, recentArtist;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recentImage = itemView.findViewById(R.id.recent_ImageView);
            recentTitle = itemView.findViewById(R.id.recent_songTitle);
            recentArtist = itemView.findViewById(R.id.recent_albumTitle);
        }
    }
}
