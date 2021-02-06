package com.example.battleship.models;

public class GameFinishedData {
    private final String gameFinishedText;
    private final Boolean amIWinner;
    private final long gameStartTime;


    public GameFinishedData(String gameFinishedText, Boolean amIWinner, long gameStartTime) {
        this.gameFinishedText = gameFinishedText;
        this.amIWinner = amIWinner;
        this.gameStartTime = gameStartTime;
    }

    public String getGameFinishedText() {
        return gameFinishedText;
    }

    public Boolean getAmIWinner() {
        return amIWinner;
    }

    public long getGameStartTime() {
        return gameStartTime;
    }
}
