package com.example.battleship.viewModels;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.battleship.models.BombardingResult;
import com.example.battleship.models.GameFinishedData;
import com.example.battleship.models.GameTimeGetter;
import com.example.battleship.models.Position;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.battleship.models.Constants.ILostTitle;
import static com.example.battleship.models.Constants.bombarded;
import static com.example.battleship.models.Constants.enemyNode;
import static com.example.battleship.models.Constants.gameDataNode;
import static com.example.battleship.models.Constants.gamesNode;
import static com.example.battleship.models.Constants.maxNumChips;
import static com.example.battleship.models.Constants.result;
import static com.example.battleship.models.Constants.IWonTitle;

public class GameViewModel extends ViewModel {
    private Boolean isMyMove;
    private final DatabaseReference gameRef;
    private final String myNodeNameForBombarding;
    private final String enemyNodeNameForBombarding;
    private final String myNodeNameForBombardingResult;
    private final String enemyNodeNameForBombardingResult;
    private final MutableLiveData<BombardingResult> myBombardingResult = new MutableLiveData<>();
    private final MutableLiveData<Position> enemyBombardingPosition = new MutableLiveData<>();
    private final MutableLiveData<GameFinishedData> gameFinishedData = new MutableLiveData<>();
    private final long gameStartTime = GameTimeGetter.getCurrentTime();
    private int numMyBombardedShips = 0;


    public GameViewModel(Boolean isMyMove, String myUid, String enemyUid) {
        this.isMyMove = isMyMove;
        myNodeNameForBombarding = myUid + bombarded;
        enemyNodeNameForBombarding = enemyUid + bombarded;
        myNodeNameForBombardingResult = myUid + bombarded + result;
        enemyNodeNameForBombardingResult = enemyUid + bombarded + result;
        if (isMyMove) { // It means I created this room
            gameRef = FirebaseDatabase.getInstance().getReference(gamesNode).child(myUid);
        } else {
            gameRef = FirebaseDatabase.getInstance().getReference(gamesNode).child(enemyUid);
        }
    }

    public void bombardCell(Position position) {
        if (isMyMove) {
            gameRef.child(gameDataNode).child(myNodeNameForBombarding).setValue(position);
            setIsMyMove(false);
        }
    }

    public void setEnemyBombardingResult(Boolean isChipInjured, Position bombardingPosition) {
        if (isChipInjured) {
            numMyBombardedShips += 1;
        }
        Boolean isGameFinished = numMyBombardedShips == maxNumChips;
        setIsMyMove(!isChipInjured);
        gameRef.child(gameDataNode).child(enemyNodeNameForBombardingResult).setValue(new BombardingResult(bombardingPosition.getX(), bombardingPosition.getY(), isChipInjured, isGameFinished));
        if (isGameFinished) {
            setILostGameData();
        }
    }

    public void startListeningMyBombardingResult() {
        gameRef.child(gameDataNode).child(myNodeNameForBombardingResult).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    BombardingResult bombardingResult = snapshot.getValue(BombardingResult.class);
                    if (bombardingResult != null) {
                        if (bombardingResult.isGameFinished()) {
                            setIWonTitle();
                        } else {
                            myBombardingResult.setValue(bombardingResult);
                            if (bombardingResult.isSuccess()) {
                                setIsMyMove(true);
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void startListeningEnemyBombarding() {
        gameRef.child(gameDataNode).child(enemyNodeNameForBombarding).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Position bombardingPosition = snapshot.getValue(Position.class);
                    if (bombardingPosition != null) {
                        enemyBombardingPosition.setValue(bombardingPosition);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void setIsMyMove(boolean isMyMove) {
        this.isMyMove = isMyMove;
    }

    public LiveData<BombardingResult> getMyBombardingResult() {
        return myBombardingResult;
    }

    public LiveData<Position> getEnemyBombardingPosition() {
        return enemyBombardingPosition;
    }

    public LiveData<GameFinishedData> getGameFinishedData() {
        return gameFinishedData;
    }

    public void sendILostToTheEnemy() {
        gameRef.child(gameDataNode).child(enemyNodeNameForBombardingResult).setValue(
                new BombardingResult(0, 0, true, true));
    }

    public void setILostGameData() {
        gameFinishedData.setValue(new GameFinishedData(ILostTitle, false, gameStartTime));
    }

    public void clearData() {
        gameRef.child(gameDataNode).removeValue();
        gameRef.child(enemyNode).removeValue();
    }

    private void setIWonTitle() {
        gameFinishedData.setValue(new GameFinishedData(IWonTitle, true, gameStartTime));
    }
}
