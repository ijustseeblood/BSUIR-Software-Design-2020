package com.example.battleship.viewModels;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.battleship.models.GameData;
import com.example.battleship.models.Position;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.battleship.models.Constants.enemyNode;
import static com.example.battleship.models.Constants.gamesNode;
import static com.example.battleship.models.Constants.maxNumChips;

public class RoomViewModel extends ViewModel {
    private final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private int numChipsAllocated = 0;
    private final Boolean[][] chipPositions = new Boolean[10][10];
    private final MutableLiveData<Position> shipToDrawPosition = new MutableLiveData<>();
    private final MutableLiveData<Position> shipToRemovePosition = new MutableLiveData<>();
    private final MutableLiveData<String> notifications = new MutableLiveData<>();
    private final MutableLiveData<GameData> gameData = new MutableLiveData<>();

    public LiveData<Position> getShipToDrawPosition() {
        return shipToDrawPosition;
    }

    public LiveData<Position> getShipToRemovePosition() {
        return shipToRemovePosition;
    }

    public LiveData<String> getNotifications() {
        return notifications;
    }

    public LiveData<GameData> getGameData() {
        return gameData;
    }

    public void createRoom(String roomId) {
        if (maxNumChips == numChipsAllocated) {
            DatabaseReference roomReference = firebaseDatabase.getReference(gamesNode).child(roomId);
            roomReference.child(enemyNode).setValue("").addOnSuccessListener(aVoid ->
                    notifications.setValue("Your room is created"));
        } else {
            notifications.setValue("You need to also put " + (maxNumChips - numChipsAllocated) + " ships");
        }
    }

    public void startListeningEnemyUid(String roomId) {
        DatabaseReference roomReference = firebaseDatabase.getReference(gamesNode).child(roomId);
        roomReference.child(enemyNode).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String enemyUid = dataSnapshot.getValue(String.class);
                    if (!TextUtils.isEmpty(enemyUid)) {
                        gameData.setValue(new GameData(enemyUid, chipPositions));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void connectToRoom(String gameUid, String myUid) {
        if (maxNumChips == numChipsAllocated) {
            DatabaseReference roomReference = firebaseDatabase.getReference(gamesNode).child(gameUid);
            roomReference.child(enemyNode).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String enemyUid = dataSnapshot.getValue(String.class);
                        if (TextUtils.isEmpty(enemyUid)) {
                            roomReference.child(enemyNode).setValue(myUid);
                            gameData.setValue(new GameData(gameUid, chipPositions));
                        } else {
                            notifications.setValue("Room is busy");
                        }
                    } else {
                        notifications.setValue("No such room");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } else {
            notifications.setValue("You need to also put " + (maxNumChips - numChipsAllocated) + " ships");
        }

    }

    public void allocateShip(Position position) {
        if (numChipsAllocated < maxNumChips) {
            numChipsAllocated += 1;
            chipPositions[position.getY()][position.getX()] = true;
            shipToDrawPosition.setValue(position);
        }
    }

    public void removeShip(Position position) {
        numChipsAllocated -= 1;
        chipPositions[position.getY()][position.getX()] = false;
        shipToRemovePosition.setValue(position);
    }


}
