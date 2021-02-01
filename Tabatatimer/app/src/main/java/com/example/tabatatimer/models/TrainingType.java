package com.example.tabatatimer.models;

public enum TrainingType {
    PREPARE("Prepare"),
    WORK("Work"),
    REST("Rest"),
    REST_BTW_SETS("Rest between sets"),
    COOL_DOWN("Cool down"),
    FINISHED("Finished");

    public final String value;

    TrainingType(String value) {
        this.value = value;
    }
}
