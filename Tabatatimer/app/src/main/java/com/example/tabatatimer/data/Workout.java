package com.example.tabatatimer.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import static com.example.tabatatimer.models.constants.workoutTableName;

@Entity(tableName = workoutTableName)
public class Workout {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String title;
    private int color;
    private long prepareSec;
    private long workSec;
    private long restSec;
    private long restBtwSetsSec;
    private long coolDownSec;
    private long sets;

    public Workout(String title, int color, long prepareSec, long workSec, long restSec, long restBtwSetsSec, long coolDownSec, long sets) {
        this.title = title;
        this.color = color;
        this.prepareSec = prepareSec;
        this.workSec = workSec;
        this.restSec = restSec;
        this.restBtwSetsSec = restBtwSetsSec;
        this.coolDownSec = coolDownSec;
        this.sets = sets;
    }

    public String getTitle() {
        return title;
    }

    public int getColor() {
        return color;
    }

    public long getId() {
        return id;
    }

    public long getPrepareSec() {
        return prepareSec;
    }

    public long getWorkSec() {
        return workSec;
    }

    public long getRestSec() {
        return restSec;
    }

    public long getRestBtwSetsSec() {
        return restBtwSetsSec;
    }

    public long getCoolDownSec() {
        return coolDownSec;
    }

    public long getSets() {
        return sets;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPrepareSec(long prepareSec) {
        this.prepareSec = prepareSec;
    }

    public void setWorkSec(long workSec) {
        this.workSec = workSec;
    }

    public void setRestSec(long restSec) {
        this.restSec = restSec;
    }

    public void setRestBtwSetsSec(long restBtwSetsSec) {
        this.restBtwSetsSec = restBtwSetsSec;
    }

    public void setCoolDownSec(long coolDownSec) {
        this.coolDownSec = coolDownSec;
    }

    public void setSets(long sets) {
        this.sets = sets;
    }
}
