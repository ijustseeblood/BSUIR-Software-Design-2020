package com.example.tabatatimer.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.tabatatimer.data.WorkoutRepository;
import com.example.tabatatimer.models.Training;
import com.example.tabatatimer.models.TrainingsGetter;

import java.util.List;

public class TimerViewModel extends AndroidViewModel {
    private final WorkoutRepository workoutRepository;
    private MutableLiveData<Training> currentTraining;
    private MutableLiveData<Boolean> isTimerRunning;

    public TimerViewModel(@NonNull Application application) {
        super(application);
        workoutRepository = new WorkoutRepository(application);
        currentTraining = new MutableLiveData<>();
        isTimerRunning = new MutableLiveData<>(true);
    }

    public LiveData<List<Training>> getTrainings(long workoutId) { // once getWorkout returns value TrainingsGetter.getTraining is invoked
        return Transformations.map(workoutRepository.getWorkout(workoutId), workout -> {
            return TrainingsGetter.getTraining(workout);
        });
    }

    public LiveData<Training> getCurrentTraining() {
        return currentTraining;
    }

    public void setCurrentTraining(Training currentTraining) {
        this.currentTraining.setValue(currentTraining);
    }

    public MutableLiveData<Boolean> getIsTimerRunning() {
        return isTimerRunning;
    }

    public void setIsTimerRunning(Boolean isTimerRunning) {
        this.isTimerRunning.setValue(isTimerRunning);
    }
}
