package com.example.battleship.views;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.battleship.R;
import com.example.battleship.models.OnCellTypeChange;
import com.example.battleship.models.Position;
import com.example.battleship.utils.BoardGetter;
import com.example.battleship.viewModels.RoomViewModel;

import static com.example.battleship.models.Constants.keyIsMyMove;
import static com.example.battleship.models.Constants.keyToGetMyGameBoard;
import static com.example.battleship.models.Constants.keyToGetEnemyUid;
import static com.example.battleship.models.Constants.keyToGetMyUid;

public class CreateRoomActivity extends AppCompatActivity implements OnCellTypeChange {
    private TableLayout gameBoardTL;
    private RoomViewModel roomVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);
        String myUid = getIntent().getStringExtra(keyToGetMyUid);
        roomVM = new ViewModelProvider(this).get(RoomViewModel.class);

        gameBoardTL = findViewById(R.id.myGameBoardTableLayout);
        TextView roomIdTV = findViewById(R.id.roomIdTextView);
        Button createRoomBtn = findViewById(R.id.createNewRoomButton);
        ImageButton copyRoomIdBtn = findViewById(R.id.copyRoomIdBtn);

        roomIdTV.setText(myUid);
        BoardGetter.getBoard(gameBoardTL, this, this);

        createRoomBtn.setOnClickListener(view -> roomVM.createRoom(myUid));

        copyRoomIdBtn.setOnClickListener(view -> {
            saveToClipBoardManager(myUid);
            Toast.makeText(this, getString(R.string.room_id_is_copied_title), Toast.LENGTH_SHORT).show();
        });

        roomVM.getNotifications().observe(this, exception ->
                Toast.makeText(this, exception, Toast.LENGTH_SHORT).show());

        roomVM.getGameData().observe(this, gamePrepareData -> {
            Intent intent = new Intent(this, GameActivity.class);
            intent.putExtra(keyToGetEnemyUid, gamePrepareData.getEnemyUid());
            intent.putExtra(keyToGetMyUid, myUid);
            intent.putExtra(keyToGetMyGameBoard, gamePrepareData.getMyGameBoard());
            intent.putExtra(keyIsMyMove, true);
            startActivity(intent);
        });

        roomVM.getShipToDrawPosition().observe(this, position ->
                BoardGetter.drawChipOnBoard(position, gameBoardTL));
        roomVM.getShipToRemovePosition().observe(this, position ->
                BoardGetter.removeShipOnBoard(position, gameBoardTL));

        roomVM.startListeningEnemyUid(myUid);
    }

    @Override
    public void allocateShip(Position position) {
        roomVM.allocateShip(position);
    }

    @Override
    public void removeShip(Position position) {
        roomVM.removeShip(position);
    }

    private void saveToClipBoardManager(String dataToSave) {
        ClipboardManager clipboardManager = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText(getString(R.string.gameRoomId), dataToSave);
        clipboardManager.setPrimaryClip(clipData);
    }
}