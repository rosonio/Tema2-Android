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
 * Use the {@link Fragment1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment1 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArrayList<User> userArrayList = new ArrayList<User>();

    UserAdapter userAdapter = new UserAdapter(userArrayList, new OnItemClickListener() {
        @Override
        public void onUserClick(User user) {
            if (activityFragmentCommunication != null) {
                activityFragmentCommunication.addFragment2(user);
            }
        }

        @Override
        public void onAlbumCLick(Album album) {

        }

        @Override
        public void onArrowClick(User user) {
            fetchPosts(user);
        }
    });

    ActivityFragmentCommunication activityFragmentCommunication;

    public Fragment1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment1.
     */
    // TODO: Rename and change types and number of parameters

    public static Fragment1 newInstance(String param1, String param2) {
        Fragment1 fragment = new Fragment1();
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
        fetchUsers();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_1, container, false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        RecyclerView usersRecyclerView = (RecyclerView) view.findViewById(R.id.users_list);
        usersRecyclerView.setLayoutManager(linearLayoutManager);
        usersRecyclerView.setAdapter(userAdapter);

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ActivityFragmentCommunication) {
            activityFragmentCommunication = (ActivityFragmentCommunication) context;
        }
    }

    public void fetchUsers() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "https://jsonplaceholder.typicode.com/users";

        StringRequest getUserRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        handleUserResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(getUserRequest);
    }

    public void handleUserResponse(String response) {
        try {
            JSONArray userJsonArray = new JSONArray(response);

            for (int index = 0; index < userJsonArray.length(); index++) {

                JSONObject userObject = userJsonArray.getJSONObject(index);
                long id = userObject.getLong("id");
                String name = userObject.getString("name");
                String username = userObject.getString("username");
                String email = userObject.getString("email");
                String phone = userObject.getString("phone");
                String website = userObject.getString("website");

                JSONObject address = userObject.getJSONObject("address");
                String street = address.getString("street");
                String suite = address.getString("suite");
                String city = address.getString("city");
                String zipcode = address.getString("zipcode");

                JSONObject geo = address.getJSONObject("geo");
                String lat = geo.getString("lat");
                String lng = geo.getString("lng");

                JSONObject company = userObject.getJSONObject("company");
                String companyName = company.getString("name");
                String catchPhrase = company.getString("catchPhrase");
                String bs = company.getString("bs");


                User user = new User(id, name, username, email);
                userArrayList.add(user);
            }

            userAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void fetchPosts(User user) {
        VolleySingleton volleyConfigSingleton = VolleySingleton.getInstance(getContext());
        RequestQueue queue = volleyConfigSingleton.getRequestQueue();
        String url = "https://jsonplaceholder.typicode.com/users";
        url += "/" + user.getId() + "/posts";

        StringRequest getPostsRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        handlePostResponse(response, user);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Error!", Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(getPostsRequest);
    }

    public void handlePostResponse(String response, User user) {
        try {
            user.getPosts().clear();

            JSONArray postsJsonArray = new JSONArray(response);

            for (int index = 0; index < postsJsonArray.length(); index++) {

                JSONObject postObject = postsJsonArray.getJSONObject(index);

                if (postObject != null) {
                    long userId = postObject.getLong("userId");
                    long id = postObject.getLong("id");
                    String title = postObject.getString("title");
                    String body = postObject.getString("body");

                    Post post = new Post(userId, id, title, body);

                    if (!user.getPosts().contains(post)) {
                        user.getPosts().add(post);
                    }
                }
            }

            userAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}