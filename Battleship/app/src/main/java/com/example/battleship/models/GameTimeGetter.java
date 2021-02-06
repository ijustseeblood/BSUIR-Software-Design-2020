package com.example.battleship.models;

import java.util.Date;

public class GameTimeGetter {
    private static final String timeDelimiter = ":";
    public static long getCurrentTime() {
        return new Date().getTime();
    }

    public static String getGameDuration(long gameStartTime, long gameEndTime) {
        StringBuilder gameDuration = new StringBuilder();
        long gameDurationMils = gameEndTime - gameStartTime;
        long gameSeconds = gameDurationMils / 1000;
        long gameMinutes = gameDurationMils / (60 * 1000);
        long gameHours = gameDurationMils / (60 * 60 * 1000);
        if (gameHours > 0) {
            gameDuration.append(gameHours).append(timeDelimiter).append(gameMinutes).append(timeDelimiter).append(gameSeconds);
        } else if (gameMinutes > 0) {
            gameDuration.append(gameMinutes).append(timeDelimiter).append(gameSeconds);
        } else if (gameSeconds > 0) {
            gameDuration.append(gameSeconds).append(" sec");
        }
        return gameDuration.toString();
    }
}
