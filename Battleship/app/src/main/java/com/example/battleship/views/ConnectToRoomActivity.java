package com.example.battleship.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.Toast;

import com.example.battleship.R;
import com.example.battleship.models.OnCellTypeChange;
import com.example.battleship.models.Position;
import com.example.battleship.utils.BoardGetter;
import com.example.battleship.viewModels.RoomViewModel;

import static com.example.battleship.models.Constants.keyIsMyMove;
import static com.example.battleship.models.Constants.keyToGetMyGameBoard;
import static com.example.battleship.models.Constants.keyToGetEnemyUid;
import static com.example.battleship.models.Constants.keyToGetMyUid;

public class ConnectToRoomActivity extends AppCompatActivity implements OnCellTypeChange {
    private TableLayout gameBoardTL;
    private RoomViewModel roomVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_to_room);
        String myUid = getIntent().getStringExtra(keyToGetMyUid);
        roomVM = new ViewModelProvider(this).get(RoomViewModel.class);


        gameBoardTL = findViewById(R.id.myGameBoardTableLayout);
        EditText roomIdET = findViewById(R.id.roomIdEditText);
        Button connectToRoomBtn = findViewById(R.id.connectToRoomButton);

        BoardGetter.getBoard(gameBoardTL, this, this);


        connectToRoomBtn.setOnClickListener(view -> roomVM.connectToRoom(roomIdET.getText().toString(), myUid));


        roomVM.getNotifications().observe(this, exception ->
                Toast.makeText(this, exception, Toast.LENGTH_SHORT).show());

        roomVM.getGameData().observe(this, gamePrepareData -> {
            Intent intent = new Intent(this, GameActivity.class);
            intent.putExtra(keyToGetEnemyUid, gamePrepareData.getEnemyUid());
            intent.putExtra(keyToGetMyUid, myUid);
            intent.putExtra(keyIsMyMove, false);
            intent.putExtra(keyToGetMyGameBoard, gamePrepareData.getMyGameBoard());
            startActivity(intent);
        });

        roomVM.getShipToDrawPosition().observe(this, position ->
                BoardGetter.drawChipOnBoard(position, gameBoardTL));
        roomVM.getShipToRemovePosition().observe(this, position ->
                BoardGetter.removeShipOnBoard(position, gameBoardTL));
    }

    @Override
    public void allocateShip(Position position) {
        roomVM.allocateShip(position);
    }

    @Override
    public void removeShip(Position position) {
        roomVM.removeShip(position);
    }

}