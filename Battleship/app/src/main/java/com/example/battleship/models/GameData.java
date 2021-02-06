package com.example.battleship.models;

public class GameData {
    private String enemyUid;
    private Boolean[][] myGameBoard;

    public GameData() {
    }

    public GameData(String enemyUid, Boolean[][] myGameBoard) {
        this.enemyUid = enemyUid;
        this.myGameBoard = myGameBoard;
    }

    public String getEnemyUid() {
        return enemyUid;
    }

    public Boolean[][] getMyGameBoard() {
        return myGameBoard;
    }
}
