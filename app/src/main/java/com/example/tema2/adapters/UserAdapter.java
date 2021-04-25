package com.example.tema2.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homework2.R;
import com.example.tema2.interfaces.OnItemClickListener;
import com.example.tema2.models.User;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private ArrayList<User> users;
    private OnItemClickListener onItemClickListener;

    public UserAdapter(ArrayList<User> users, OnItemClickListener onItemClickListener) {
        this.users = users;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.user_cell, parent, false);

        UserViewHolder userViewHolder = new UserViewHolder(view);

        return userViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = users.get(position);
        holder.bind(user);

        boolean postsVisible = users.get(position).arePostsVisible();
        holder.postsConstraintLayout.setVisibility(postsVisible ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {
        private TextView userName;
        private TextView info;
        private TextView posts;
        private ImageView arrowButton;
        private View view;
        private ConstraintLayout postsConstraintLayout;

        public UserViewHolder(View view) {
            super(view);
            this.view = view;
            userName = view.findViewById(R.id.username);
            info = view.findViewById(R.id.user_info);
            posts = view.findViewById(R.id.postsTextView);
            postsConstraintLayout = view.findViewById(R.id.posts);
            arrowButton = view.findViewById(R.id.imv_user_cell);
        }

        public void bind(User user) {
            userName.setText(user.getName());
            info.setText("Username: " + user.getUsername() + "\n" + "Email: " + user.getEmailAddress());

            String userPosts = new String("Posts:\n");
            for(int index=0; index< user.getPosts().size();index++) {
                userPosts+=(index+1)+") "+user.getPosts().get(index).getTitle()+"\n";
            }

            posts.setText(userPosts);

            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    onItemClickListener.onUserClick(user);
                }
            });

            arrowButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    user.setPostsVisible(!user.arePostsVisible());
                    onItemClickListener.onArrowClick(user);
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }
}