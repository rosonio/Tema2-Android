package com.example.tema2.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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
import com.example.tema2.adapters.UserAdapter;
import com.example.tema2.interfaces.ActivityFragmentCommunication;
import com.example.tema2.interfaces.OnItemClickListener;
import com.example.tema2.models.Album;
import com.example.tema2.models.Post;
import com.example.tema2.models.User;
import com.example.tema2.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment2 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private User user;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArrayList<Album> albums = new ArrayList<Album>();

    AlbumAdapter albumAdapter = new AlbumAdapter(albums, new OnItemClickListener() {
        @Override
        public void onUserClick(User user) {

        }

        @Override
        public void onAlbumCLick(Album album) {
            if (activityFragmentCommunication != null) {
                activityFragmentCommunication.addFragment3(album);
            }
        }

        @Override
        public void onArrowClick(User user) {

        }
    });

    ActivityFragmentCommunication activityFragmentCommunication;

    public Fragment2(User user) {
        this.user = user;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment2.
     */
    // TODO: Rename and change types and number of parameters

    /*public static Fragment2 newInstance(User user) {
        Fragment2 fragment = new Fragment2(user);
        return fragment;
    }*/

    public static Fragment2 newInstance(String param1, String param2, User newUser) {
        Fragment2 fragment = new Fragment2(newUser);
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
        fetchAlbums();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_2, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.albums_list);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(albumAdapter);

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ActivityFragmentCommunication) {
            activityFragmentCommunication = (ActivityFragmentCommunication) context;
        }
    }

    public void fetchAlbums() {
        VolleySingleton volleyConfigSingleton = VolleySingleton.getInstance(getContext());
        RequestQueue queue = volleyConfigSingleton.getRequestQueue();
        String url = "https://jsonplaceholder.typicode.com/users/";
        url += user.getId() + "/albums";

        StringRequest getAlbumsRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        handleAlbumsResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Error!", Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(getAlbumsRequest);
    }

    public void handleAlbumsResponse(String response){
        try {
            user.getAlbums().clear();

            JSONArray albumsJsonArray = new JSONArray(response);

            for (int index = 0; index < albumsJsonArray.length(); index++) {

                JSONObject albumObject = albumsJsonArray.getJSONObject(index);

                if (albumObject != null) {
                    long userId = albumObject.getLong("userId");
                    long id = albumObject.getLong("id");
                    String title = albumObject.getString("title");

                    Album album = new Album(userId, id, title);

                    if (!user.getAlbums().contains(album)) {
                        user.getAlbums().add(album);
                        albums.add(album);
                    }
                }
            }

            albumAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}