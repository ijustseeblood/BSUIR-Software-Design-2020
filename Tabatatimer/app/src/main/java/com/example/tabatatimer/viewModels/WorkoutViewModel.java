package com.example.tabatatimer.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.example.tabatatimer.data.Workout;
import com.example.tabatatimer.data.WorkoutRepository;

import java.util.List;

public class WorkoutViewModel extends AndroidViewModel {
    private final WorkoutRepository workoutRepository;
    private boolean isWorkoutObserved;

    public WorkoutViewModel(@NonNull Application application) {
        super(application);
        workoutRepository = new WorkoutRepository(application);
        isWorkoutObserved = false;
    }

    public void insertNewWorkout() {
        Workout workout = new Workout("Workout",
                -12627531,
                10,
                20,
                30,
                40,
                5,
                5);
        workoutRepository.insertWorkout(workout);
    }

    public void updateWorkout(Workout workout) {
        workoutRepository.updateWorkout(workout);
    }


    public void deleteWorkout(long idWorkout) {
        workoutRepository.deleteWorkout(idWorkout);
    }

    public void deleteAllWorkouts() {
        workoutRepository.deleteAllWorkouts();
    }


    public LiveData<Workout> getWorkout(long idWorkout) {
        MediatorLiveData<Workout> workoutMediatorLiveData = new MediatorLiveData<>();
        workoutMediatorLiveData.addSource(workoutRepository.getWorkout(idWorkout), workout -> {
            if (!isWorkoutObserved) {
                workoutMediatorLiveData.setValue(workout);
                isWorkoutObserved = true;
            }
        });
        return workoutMediatorLiveData;
    }


    public LiveData<List<Workout>> getAllWorkouts() {
        return workoutRepository.getAllWorkouts();
    }
}
