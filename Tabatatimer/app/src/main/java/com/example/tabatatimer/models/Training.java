package com.example.tabatatimer.models;

import java.io.Serializable;

public class Training implements Serializable {
    private final long sec;
    private final TrainingType trainingType;

    public Training(long sec, TrainingType trainingType) {
        this.sec = sec;
        this.trainingType = trainingType;
    }

    public long getSec() {
        return sec;
    }

    public TrainingType getTrainingType() {
        return trainingType;
    }
}
