package com.example.tabatatimer.models;

public interface OnWorkoutOptionClick {
    void deleteWorkout(long workoutId);
    void editWorkout(long workoutId);
    void startWorkout(long workoutId);
}
