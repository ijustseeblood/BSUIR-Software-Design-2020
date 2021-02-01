package com.example.tabatatimer.views;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tabatatimer.R;
import com.example.tabatatimer.models.OnWorkoutOptionClick;

public class WorkoutViewHolder extends RecyclerView.ViewHolder {
    private final OnWorkoutOptionClick onWorkoutOptionClick;
    private final TextView workoutTitle;
    private final ImageButton deleteWorkoutButton;
    private final ImageButton editWorkoutButton;

    public WorkoutViewHolder(@NonNull View itemView, OnWorkoutOptionClick onWorkoutOptionClick) {
        super(itemView);
        this.onWorkoutOptionClick = onWorkoutOptionClick;
        workoutTitle = itemView.findViewById(R.id.workoutTitle);
        deleteWorkoutButton = itemView.findViewById(R.id.deleteWorkoutButton);
        editWorkoutButton = itemView.findViewById(R.id.editWorkoutButton);
    }

    public void bindData(long workoutId, String workoutTextTitle, int color) {
        workoutTitle.setText(workoutTextTitle);
        itemView.setBackgroundColor(color);

        itemView.setOnClickListener(view -> onWorkoutOptionClick.startWorkout(workoutId));
        deleteWorkoutButton.setOnClickListener(view -> onWorkoutOptionClick.deleteWorkout(workoutId));
        editWorkoutButton.setOnClickListener(view -> onWorkoutOptionClick.editWorkout(workoutId));
    }
}
