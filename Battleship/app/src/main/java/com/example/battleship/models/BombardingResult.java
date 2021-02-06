package com.example.battleship.models;

public class BombardingResult {
    private int x;
    private int y;
    private Boolean success;
    private Boolean gameFinished;

    public BombardingResult() {
    }

    public BombardingResult(int x, int y, Boolean success, Boolean gameFinished) {
        this.x = x;
        this.y = y;
        this.success = success;
        this.gameFinished = gameFinished;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Boolean isSuccess() {
        return success;
    }

    public Boolean isGameFinished() {
        return gameFinished;
    }
}
