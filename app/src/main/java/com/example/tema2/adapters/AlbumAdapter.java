package com.example.tema2.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homework2.R;
import com.example.tema2.interfaces.OnItemClickListener;
import com.example.tema2.models.Album;

import java.util.ArrayList;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder> {
    private ArrayList<Album> albums;
    private OnItemClickListener onItemClickListener;

    public AlbumAdapter(ArrayList<Album> albums, OnItemClickListener onItemClickListener) {
        this.albums = albums;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.album_cell, parent, false);

        AlbumViewHolder albumViewHolder = new AlbumViewHolder(view);

        return albumViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumViewHolder holder, int position) {
        Album album = albums.get(position);
        holder.bind(album);
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    class AlbumViewHolder extends RecyclerView.ViewHolder {
        private TextView albumTitle;
        View view;

        public AlbumViewHolder(@NonNull View view) {
            super(view);
            this.view=view;
            albumTitle = view.findViewById(R.id.album_title);
        }

        public void bind(Album album) {
            albumTitle.setText(album.getTitle());

            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    onItemClickListener.onAlbumCLick(album);
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }
}

