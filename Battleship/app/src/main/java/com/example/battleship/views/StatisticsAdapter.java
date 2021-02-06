package com.example.battleship.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.battleship.R;
import com.example.battleship.models.Statics;

import java.util.List;

public class StatisticsAdapter extends RecyclerView.Adapter<StatisticsAdapter.StaticsViewHolder> {
    private List<Statics> listStatistics;

    public void setListStatistics(List<Statics> listStatistics) {
        this.listStatistics = listStatistics;
    }

    @NonNull
    @Override
    public StaticsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View statisticsView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_statistics, parent, false);
        return new StaticsViewHolder(statisticsView);
    }

    @Override
    public void onBindViewHolder(@NonNull StaticsViewHolder holder, int position) {
        holder.bindData(listStatistics.get(position));
    }

    @Override
    public int getItemCount() {
        return listStatistics != null ? listStatistics.size() : 0;
    }

    public static class StaticsViewHolder extends RecyclerView.ViewHolder {
        private final TextView enemyUidTV;
        private final TextView amIWinnerTV;
        private final TextView gameDurationTV;

        public StaticsViewHolder(@NonNull View itemView) {
            super(itemView);
            enemyUidTV = itemView.findViewById(R.id.enemyUid);
            amIWinnerTV = itemView.findViewById(R.id.amIWinnerTextView);
            gameDurationTV = itemView.findViewById(R.id.gameDurationTextView);
        }

        public void bindData(Statics statics) {
            enemyUidTV.setText(statics.getEnemyUid());
            amIWinnerTV.setText(String.valueOf(statics.getAmIWinner()));
            gameDurationTV.setText(String.valueOf(statics.getGameDuration()));
        }
    }
}
