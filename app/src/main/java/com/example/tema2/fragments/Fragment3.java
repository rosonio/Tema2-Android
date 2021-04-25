package com.example.tema2.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.homework2.R;
import com.example.tema2.adapters.AlbumAdapter;
import com.example.tema2.adapters.ImageAdapter;
import com.example.tema2.interfaces.ActivityFragmentCommunication;
import com.example.tema2.interfaces.OnItemClickListener;
import com.example.tema2.models.Album;
import com.example.tema2.models.Photo;
import com.example.tema2.models.User;
import com.example.tema2.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment3 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Album album;
    private ArrayList<Photo> photos = new ArrayList<Photo>();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ImageAdapter imageAdapter = new ImageAdapter(photos);

    ActivityFragmentCommunication activityFragmentCommunication;

    public Fragment3(Album album) {
        this.album = album;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment3.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment3 newInstance(String param1, String param2, Album album) {
        Fragment3 fragment = new Fragment3(album);
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        fetchPhotos();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_3, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.photos);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2, RecyclerView.VERTICAL, false);

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(imageAdapter);
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ActivityFragmentCommunication) {
            activityFragmentCommunication = (ActivityFragmentCommunication) context;
        }
    }

    public void fetchPhotos() {
        VolleySingleton volleyConfigSingleton = VolleySingleton.getInstance(getContext());
        RequestQueue queue = volleyConfigSingleton.getRequestQueue();
        String url = "https://jsonplaceholder.typicode.com/albums/";
        url += album.getId() + "/photos";

        StringRequest getPhotosRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        handlePhotosResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Error!", Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(getPhotosRequest);
    }

    public void handlePhotosResponse(String response){
        try {
            JSONArray photosJsonArray = new JSONArray(response);

            for (int index = 0; index < photosJsonArray.length(); index++) {

                JSONObject photoObject = photosJsonArray.getJSONObject(index);

                if (photoObject != null) {
                    long userId = photoObject.getLong("albumId");
                    long id = photoObject.getLong("id");
                    String title = photoObject.getString("title");
                    String url = photoObject.getString("url")+".png";
                    String thumbnailUri = photoObject.getString("thumbnailUrl");

                    Photo photo = new Photo(userId, id, title, url, thumbnailUri);

                    if (!album.getPhotos().contains(album)) {
                        album.getPhotos().add(photo);
                        photos.add(photo);
                    }
                }
            }

            imageAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}