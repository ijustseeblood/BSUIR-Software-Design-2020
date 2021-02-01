package com.example.tabatatimer.views.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.tabatatimer.R;
import com.example.tabatatimer.data.Workout;
import com.example.tabatatimer.models.OnWorkoutOptionClick;
import com.example.tabatatimer.views.WorkoutViewHolder;

public class WorkoutAdapter extends ListAdapter<Workout, WorkoutViewHolder> {
    private final OnWorkoutOptionClick onWorkoutOptionClick;


    public WorkoutAdapter(OnWorkoutOptionClick onWorkoutOptionClick) {
        super(new WorkoutDiffCallBack());
        this.onWorkoutOptionClick = onWorkoutOptionClick;
    }

    @NonNull
    @Override
    public WorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_layout, parent, false);
        return new WorkoutViewHolder(view, onWorkoutOptionClick);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutViewHolder holder, int position) {
        Workout workout = getItem(position);
        holder.bindData(workout.getId(), workout.getTitle(), workout.getColor());
    }

    public static class WorkoutDiffCallBack extends DiffUtil.ItemCallback<Workout> {

        @Override
        public boolean areItemsTheSame(@NonNull Workout oldItem, @NonNull Workout newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Workout oldItem, @NonNull Workout newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) && oldItem.getColor() == newItem.getColor();
        }
    }
}
