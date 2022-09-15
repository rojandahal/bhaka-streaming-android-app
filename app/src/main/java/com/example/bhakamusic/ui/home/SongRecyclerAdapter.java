package com.example.bhakamusic.ui.home;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bhakamusic.Interface.RecyclerViewInterface;
import com.example.bhakamusic.ModelResponse.SearchRequest;
import com.example.bhakamusic.ModelResponse.SearchResponse;
import com.example.bhakamusic.ModelResponse.SongsResponse;
import com.example.bhakamusic.R;
import com.example.bhakamusic.configs.Configs;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

public class SongRecyclerAdapter extends RecyclerView.Adapter<SongRecyclerAdapter.ViewHolder> implements RecyclerViewInterface {

    private static final String TAG = "ADAPTER";
    private ArrayList<SearchResponse> songList;
    private final RecyclerViewInterface recyclerViewInterface;

    public SongRecyclerAdapter(ArrayList<SearchResponse> songList, RecyclerViewInterface recyclerViewInterface) {
        this.songList = songList;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View inflate = layoutInflater.inflate(R.layout.card_recyclerview,parent,false);
        ViewHolder viewHolder = new ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SearchResponse song = songList.get(position);
        holder.songTitle.setText(song.getTitle());
        holder.artistName.setText(song.getArtist());
        Picasso.get().load(Configs.BASE_URL+song.getCoverArt()).into(holder.imageView);

        holder.favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Objects.requireNonNull(holder).favourite.setImageResource(R.drawable.ic_baseline_favorite_24);
                Toast.makeText(view.getContext(), "Favourite Added!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    @Override
    public void onItemClick(int position) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView songTitle;
        TextView artistName;
        ImageView imageView;
        ImageView favourite;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            songTitle = itemView.findViewById(R.id.songTitle);
            artistName = itemView.findViewById(R.id.artistName);
            imageView = itemView.findViewById(R.id.albumImage);
            favourite = itemView.findViewById(R.id.favourite);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewInterface!=null){
                        int pos = getBindingAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}
