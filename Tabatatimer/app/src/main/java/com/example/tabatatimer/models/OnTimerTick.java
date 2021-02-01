package com.example.tabatatimer.models;

public interface OnTimerTick {
    void broadcastTimerNumSeconds(Training currentTraining);

    void playMelody();
}
