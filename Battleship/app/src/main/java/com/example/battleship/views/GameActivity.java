package com.example.battleship.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.TableLayout;

import com.example.battleship.R;
import com.example.battleship.models.OnCellBombard;
import com.example.battleship.models.OnFinishGame;
import com.example.battleship.models.OnCancelGame;
import com.example.battleship.models.Position;
import com.example.battleship.utils.BoardGetter;
import com.example.battleship.viewModels.GameResultsViewModel;
import com.example.battleship.viewModels.GameViewModel;
import com.example.battleship.viewModels.GameViewModelFactory;

import static com.example.battleship.models.Constants.keyIsMyMove;
import static com.example.battleship.models.Constants.keyToGetEnemyUid;
import static com.example.battleship.models.Constants.keyToGetMyGameBoard;
import static com.example.battleship.models.Constants.keyToGetMyUid;

public class GameActivity extends AppCompatActivity implements OnCellBombard, OnFinishGame, OnCancelGame {
    private GameViewModel gameVM;
    private GameResultsViewModel gameResultsVM;
    private TableLayout myGameBoardTL;
    private TableLayout enemyGameBoardTL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        final String enemyUid = getIntent().getStringExtra(keyToGetEnemyUid);
        final String myUid = getIntent().getStringExtra(keyToGetMyUid);
        final Boolean isMyMove = getIntent().getBooleanExtra(keyIsMyMove, false);
        final Boolean[][] myGameBoard = (Boolean[][]) getIntent().getSerializableExtra(keyToGetMyGameBoard);

        gameVM = new ViewModelProvider(this, new GameViewModelFactory(isMyMove, myUid, enemyUid)).get(GameViewModel.class);
        gameResultsVM = new ViewModelProvider(this).get(GameResultsViewModel.class);

        myGameBoardTL = findViewById(R.id.myGameBoardTableLayout);
        enemyGameBoardTL = findViewById(R.id.enemyGameBoardTableLayout);

        BoardGetter.restoreBoard(myGameBoardTL, this, myGameBoard);
        BoardGetter.getBoard(enemyGameBoardTL, this, this);

        gameVM.getEnemyBombardingPosition().observe(this, bombardingPosition -> {
            boolean isShipInjured = !BoardGetter.isCellEmpty(myGameBoardTL, bombardingPosition);
            if (isShipInjured) {
                BoardGetter.setCellInjured(myGameBoardTL, new Position(bombardingPosition.getX(), bombardingPosition.getY()));
            } else {
                BoardGetter.setCellMissed(myGameBoardTL, new Position(bombardingPosition.getX(), bombardingPosition.getY()));
            }
            gameVM.setEnemyBombardingResult(isShipInjured, bombardingPosition);
        });

        gameVM.getMyBombardingResult().observe(this, bombardingResult -> {
            if (bombardingResult.isSuccess()) {
                BoardGetter.setCellInjured(enemyGameBoardTL, new Position(bombardingResult.getX(), bombardingResult.getY()));
            } else {
                BoardGetter.setCellMissed(enemyGameBoardTL, new Position(bombardingResult.getX(), bombardingResult.getY()));
            }
        });

        gameVM.getGameFinishedData().observe(this, gameFinishedData -> {
            gameResultsVM.addStatistic(myUid, enemyUid, gameFinishedData.getAmIWinner(), gameFinishedData.getGameStartTime());
            showGameFinishedDialog(gameFinishedData.getGameFinishedText());
        });

        gameVM.startListeningEnemyBombarding();
        gameVM.startListeningMyBombardingResult();
    }

    private void showGameFinishedDialog(final String gameFinishedText) {
        FragmentManager fm = getSupportFragmentManager();
        GameFinishedDialog gameFinishedDialog = new GameFinishedDialog();
        gameFinishedDialog.setCancelable(false);
        gameFinishedDialog.setGameFinishedText(gameFinishedText);
        gameFinishedDialog.show(fm, "gameFinishedDialog");
    }

    private void showCancelGameDialog() {
        FragmentManager fm = getSupportFragmentManager();
        CancelGameDialog cancelGameDialog = new CancelGameDialog();
        cancelGameDialog.show(fm, "cancelGameDialog");
    }


    @Override
    public void bombardCell(Position position) {
        if (BoardGetter.isCellEmpty(enemyGameBoardTL, position)) {
            gameVM.bombardCell(position);
        }
    }

    @Override
    public void finishGame() {
        gameVM.clearData();
        finish();
    }

    @Override
    public void cancelGame() {
        gameVM.sendILostToTheEnemy();
        gameVM.setILostGameData();
    }

    @Override
    public void onBackPressed() {
        showCancelGameDialog();
    }
}