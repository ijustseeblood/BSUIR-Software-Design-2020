package com.example.tabatatimer.models;

import java.util.List;

public class CountDownTimer {
    private final OnTimerTick onTimerTick;
    private final List<Training> trainings;
    private long millisInFuture;
    private int trainingIndex;
    private android.os.CountDownTimer countDownTimer;

    public CountDownTimer(List<Training> trainings, OnTimerTick onTimerTick) {
        this.trainings = trainings;
        this.onTimerTick = onTimerTick;
        setData(0);
    }

    private void setData(int trainingIndex) {
        this.trainingIndex = trainingIndex;
        millisInFuture = trainings.get(trainingIndex).getSec() * 1000;
    }

    public void startTimer() {
        countDownTimer = new android.os.CountDownTimer(millisInFuture, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                millisInFuture = millisUntilFinished;
                onTimerTick.broadcastTimerNumSeconds(new Training(
                        Math.round((double) millisUntilFinished / 1000),
                        trainings.get(trainingIndex).getTrainingType()));
            }

            @Override
            public void onFinish() {
                onTimerTick.playMelody();
                if (trainingIndex + 1 < trainings.size()) {
                    setData(trainingIndex + 1);
                    startTimer();
                } else {
                    onTimerTick.broadcastTimerNumSeconds(new Training(
                            0,
                            TrainingType.FINISHED));
                }
            }
        };
        countDownTimer.start();
    }

    public void pauseTimer() {
        countDownTimer.cancel();
    }

    public void startNextTraining() {
        int newTrainingIndex = trainingIndex + 1;
        if (newTrainingIndex < trainings.size()) {
            pauseTimer();
            setData(newTrainingIndex);
            startTimer();
        }
    }

    public void startPreviousTraining() {
        int newTrainingIndex = trainingIndex - 1;
        if (newTrainingIndex >= 0) {
            pauseTimer();
            setData(newTrainingIndex);
            startTimer();
        }
    }

    public void startTraining(int trainingIndex) {
        pauseTimer();
        setData(trainingIndex);
        startTimer();
    }

}
