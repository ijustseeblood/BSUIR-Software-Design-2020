package com.example.tabatatimer.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import static com.example.tabatatimer.models.constants.workoutTableName;

@Dao
public interface WorkoutDao {
    @Insert
    void insertWorkout(Workout workout);

    @Update
    void updateWorkout(Workout workout);

    @Query("Delete from " + workoutTableName + " where id= :idWorkout")
    void deleteWorkout(long idWorkout);

    @Query("Delete from " + workoutTableName)
    void deleteAllWorkouts();

    @Transaction
    @Query("Select * from " + workoutTableName + " where id =:idWorkout")
    LiveData<Workout> getWorkout(long idWorkout);

    @Transaction
    @Query("Select * from " + workoutTableName)
    LiveData<List<Workout>> getAllWorkouts();

}
