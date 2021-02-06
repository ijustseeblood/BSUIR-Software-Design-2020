package com.example.battleship.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.battleship.R;
import com.example.battleship.viewModels.GameResultsViewModel;

import static com.example.battleship.models.Constants.keyToGetMyUid;

public class GameResultsActivity extends AppCompatActivity {
    GameResultsViewModel gameResultsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_results);
        final String myUid = getIntent().getStringExtra(keyToGetMyUid);

        gameResultsViewModel = new ViewModelProvider(this).get(GameResultsViewModel.class);
        RecyclerView statisticsRV = findViewById(R.id.statisticsRecyclerView);
        TextView noStatisticsTV = findViewById(R.id.noStatisticsTextView);

        StatisticsAdapter statisticsAdapter = new StatisticsAdapter();
        statisticsRV.setAdapter(statisticsAdapter);

        gameResultsViewModel.listenForStatisticsOnce(myUid);
        gameResultsViewModel.getStatisticsList().observe(this, staticsList -> {
            if (staticsList == null || staticsList.size() == 0) {
                noStatisticsTV.setVisibility(View.VISIBLE);
            } else {
                noStatisticsTV.setVisibility(View.GONE);
            }
            statisticsAdapter.setListStatistics(staticsList);
            statisticsAdapter.notifyDataSetChanged();
        });
    }
}