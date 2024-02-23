package com.sandipbhattacharya.spaceshooter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder> {

    private final ArrayList<Player> mPlayerList;

    public static class PlayerViewHolder extends RecyclerView.ViewHolder {
        public TextView mNameTextView;
        public TextView mEmailTextView;
        public TextView mAgeTextView;
        public TextView mPasswordTextView;
        public TextView mHighScoreTextView;

        public PlayerViewHolder(View itemView) {
            super(itemView);
            mNameTextView = itemView.findViewById(R.id.nameTextView);
            mEmailTextView = itemView.findViewById(R.id.emailTextView);
            mAgeTextView = itemView.findViewById(R.id.ageTextView);
            mPasswordTextView = itemView.findViewById(R.id.passwordTextView);
            mHighScoreTextView = itemView.findViewById(R.id.highScoreTextView);
        }
    }

    public PlayerAdapter(ArrayList<Player> playerList) {
        if (playerList != null) {
            mPlayerList = playerList;
        } else {
            mPlayerList = new ArrayList<>();
        }
    }

    @Override
    public PlayerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_item, parent, false);
        PlayerViewHolder evh = new PlayerViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(PlayerViewHolder holder, int position) {
        Player currentPlayer = mPlayerList.get(position);
        holder.mNameTextView.setText(currentPlayer.getName());
        holder.mEmailTextView.setText(currentPlayer.getEmail());
        holder.mAgeTextView.setText(String.valueOf(currentPlayer.getAge()));
        holder.mPasswordTextView.setText(currentPlayer.getPassword());
        holder.mHighScoreTextView.setText(String.valueOf(currentPlayer.getHighScore()));
    }

    @Override
    public int getItemCount() {
        return mPlayerList.size();
    }
}