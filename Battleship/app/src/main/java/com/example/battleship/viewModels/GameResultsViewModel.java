package com.example.battleship.viewModels;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.battleship.models.GameTimeGetter;
import com.example.battleship.models.Statics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.battleship.models.Constants.gameResultsNode;

public class GameResultsViewModel extends ViewModel {
    MutableLiveData<List<Statics>> statisticsList = new MutableLiveData<>();

    public void addStatistic(String myUid, String enemyUid, Boolean amIWinner, long gameStartTime) {
        DatabaseReference newStatisticsNode = FirebaseDatabase.getInstance().getReference(gameResultsNode).child(myUid).push();
        String gameDuration = GameTimeGetter.getGameDuration(gameStartTime, new Date().getTime());
        newStatisticsNode.setValue(new Statics(enemyUid, amIWinner, gameDuration));
    }

    public void listenForStatisticsOnce(String myUid) {
        DatabaseReference newStatisticsNode = FirebaseDatabase.getInstance().getReference(gameResultsNode).child(myUid);
        newStatisticsNode.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    List<Statics> statisticsList = new ArrayList<>();
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        statisticsList.add(postSnapshot.getValue(Statics.class));
                    }
                    GameResultsViewModel.this.statisticsList.setValue(statisticsList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public MutableLiveData<List<Statics>> getStatisticsList() {
        return statisticsList;
    }
}
