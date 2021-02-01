package com.example.tabatatimer.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WorkoutRepository {
    private final ExecutorService dbExecutor;
    private final WorkoutDao workoutDao;

    public WorkoutRepository(Application application) {
        workoutDao = Database.getDatabase(application).workoutDao();
        dbExecutor = Executors.newSingleThreadExecutor();

    }

    public void insertWorkout(Workout workout) {
        dbExecutor.execute(() -> workoutDao.insertWorkout(workout));
    }


    public void updateWorkout(Workout workout) {
        dbExecutor.execute(() -> workoutDao.updateWorkout(workout));
    }


    public void deleteWorkout(long idWorkout) {
        dbExecutor.execute(() -> workoutDao.deleteWorkout(idWorkout));
    }

    public void deleteAllWorkouts() {
        dbExecutor.execute(workoutDao::deleteAllWorkouts);
    }


    public LiveData<Workout> getWorkout(long idWorkout) {
        return workoutDao.getWorkout(idWorkout);
    }


    public LiveData<List<Workout>> getAllWorkouts() {
        return workoutDao.getAllWorkouts();
    }
}
