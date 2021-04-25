package com.example.tema2.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.homework2.R;
import com.example.tema2.models.Photo;
import com.example.tema2.VolleySingleton;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder>{
    private ArrayList<Photo> photos = new ArrayList<Photo>();

    public ImageAdapter(ArrayList<Photo> photos) {
        this.photos = photos;
    }

    @NonNull
    @Override
    public ImageAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.photo_cell, parent, false);

        ImageAdapter.ImageViewHolder imageViewHolder = new ImageAdapter.ImageViewHolder(view);

        return imageViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapter.ImageViewHolder holder, int position) {
        Photo photo = photos.get(position);
        holder.bind(photo);
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    class ImageViewHolder extends RecyclerView.ViewHolder{
        View view;
        ImageView imageView;

        public ImageViewHolder(@NonNull View view) {
            super(view);
            this.view=view;
            imageView = view.findViewById(R.id.image_view);
        }

        public void bind(Photo photo) {
            //Picasso.with(imageView.getContext()).load(photo.getUrl()).into(imageView);

            ImageLoader imageLoader = VolleySingleton.getInstance(imageView.getContext()).getImageLoader();
            imageLoader.get(photo.getUrl(), new ImageLoader.ImageListener(){
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    imageView.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }
    }
}
