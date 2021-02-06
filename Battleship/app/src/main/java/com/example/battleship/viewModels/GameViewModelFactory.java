package com.example.battleship.viewModels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class GameViewModelFactory implements ViewModelProvider.Factory {
    private final Boolean isMyMove;
    private final String myUid;
    private final String enemyUid;

    public GameViewModelFactory(Boolean isMyMove, String myUid, String enemyUid) {
        this.isMyMove = isMyMove;
        this.myUid = myUid;
        this.enemyUid = enemyUid;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new GameViewModel(isMyMove, myUid, enemyUid);
    }
}
