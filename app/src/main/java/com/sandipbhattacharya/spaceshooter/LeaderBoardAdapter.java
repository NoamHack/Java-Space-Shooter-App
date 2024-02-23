package com.sandipbhattacharya.spaceshooter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LeaderBoardAdapter extends RecyclerView.Adapter<LeaderBoardAdapter.ViewHolder> {
    private List<Integer> highScores;

    public LeaderBoardAdapter(List<Integer> highScores) {
        this.highScores = highScores;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.leaderboard_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.highScore.setText("" + highScores.get(position));
    }

    @Override
    public int getItemCount() {
        return highScores.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView highScore;

        public ViewHolder(View itemView) {
            super(itemView);
            highScore = itemView.findViewById(R.id.highScore);
        }
    }
}
