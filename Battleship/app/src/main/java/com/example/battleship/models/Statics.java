package com.example.battleship.models;

public class Statics {
    private String enemyUid;

    private Boolean amIWinner;

    private String gameDuration;

    public Statics() {
    }

    public Statics(String enemyUid, Boolean amIWinner, String gameDuration) {
        this.enemyUid = enemyUid;
        this.amIWinner = amIWinner;
        this.gameDuration = gameDuration;
    }

    public String getEnemyUid() {
        return enemyUid;
    }

    public Boolean getAmIWinner() {
        return amIWinner;
    }

    public String getGameDuration() {
        return gameDuration;
    }
}
