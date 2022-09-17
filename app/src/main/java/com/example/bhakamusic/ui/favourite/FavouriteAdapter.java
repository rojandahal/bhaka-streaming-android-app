package com.example.bhakamusic.ui.favourite;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.bhakamusic.configs.Configs;
import com.example.bhakamusic.ui.Player.PlayerActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.ViewHolder> {
    private static final String TAG = "FAVOURITE_ADAPTER";
    private static List<FavouriteData> dataList;
    private Context context;
    private FavouriteDB favouriteDB;

    public FavouriteAdapter(Context context, List<FavouriteData> dataList) {
        this.dataList = dataList;
        this.context = context;
        favouriteDB = FavouriteDB.getInstance(context);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favourite_list_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Initialize data
        Log.d(TAG, "onBindViewHolder: " + getItemCount());
        FavouriteData favouriteData= favouriteDB.favDao().getAll().get(position);
        holder.songTitle.setText(favouriteData.getSongTitle());
        holder.artistName.setText(favouriteData.getArtistName());
        holder.songDuration.setText(favouriteData.getSongDuration());
        Picasso.get().load(Configs.BASE_URL + favouriteData.getCoverArt()).into(holder.songImage);
        holder.favouriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FavouriteData fv = dataList.get(holder.getAbsoluteAdapterPosition());
                favouriteDB.favDao().delete(fv);
                int pos = holder.getAbsoluteAdapterPosition();
                dataList.remove(pos);
                notifyItemRangeChanged(pos,dataList.size());
                notifyItemRemoved(pos);
            }
        });

        holder.songImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FavouriteData fv = dataList.get(holder.getAbsoluteAdapterPosition());
                Bundle bundle = new Bundle();
                bundle.putString("id",fv.getSongId());
                bundle.putString("user","d72b5f0a-bdf0-4bfb-8079-1f3a464e3a95");
                bundle.putString("title", fv.getSongTitle());
                bundle.putString("cover",fv.getCoverArt());
                bundle.putString("artist",fv.getArtistName());
                Intent intent = new Intent(view.getContext(), PlayerActivity.class);
                intent.putExtras(bundle);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView songImage, favouriteBtn;
        TextView songTitle, artistName, songDuration;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            songImage = itemView.findViewById(R.id.fav_songImage);
            songTitle = itemView.findViewById(R.id.fav_songTitle);
            artistName = itemView.findViewById(R.id.fav_artistName);
            songDuration = itemView.findViewById(R.id.fav_songDuration);
            favouriteBtn = itemView.findViewById(R.id.fav_favourite);
        }
    }
}
