package com.example.tabatatimer.models;

import com.example.tabatatimer.data.Workout;

import java.util.ArrayList;
import java.util.List;

public class TrainingsGetter {
    public static List<Training> getTraining(Workout workout) {
        List<Training> trainings = new ArrayList<>();
        trainings.add(new Training(workout.getPrepareSec(), TrainingType.PREPARE));
        for (int i = 0; i < workout.getSets(); i++) {
            trainings.add(new Training(workout.getWorkSec(), TrainingType.WORK));
            trainings.add(new Training(workout.getRestBtwSetsSec(), TrainingType.REST_BTW_SETS));
        }
        trainings.add(new Training(workout.getRestSec(), TrainingType.REST));
        trainings.add(new Training(workout.getCoolDownSec(), TrainingType.COOL_DOWN));
        return trainings;
    }
}
