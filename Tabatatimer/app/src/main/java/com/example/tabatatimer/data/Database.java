package com.example.tabatatimer.data;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

@androidx.room.Database(entities = {Workout.class}, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {
    public abstract WorkoutDao workoutDao();
    private static final String dbName = "tabataTimerDatabase";

    private static volatile Database dbInstance;

    static Database getDatabase(final Context context) {
        if (dbInstance == null) {
            synchronized (Database.class) {
                if (dbInstance == null) {
                    dbInstance = Room.databaseBuilder(context.getApplicationContext(),
                            Database.class, dbName)
                            .build();
                }
            }
        }
        return dbInstance;
    }
}
