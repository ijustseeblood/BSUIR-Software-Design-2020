package com.example.tabatatimer.views.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tabatatimer.R;
import com.example.tabatatimer.models.OnTrainingSelected;
import com.example.tabatatimer.models.Training;
import com.example.tabatatimer.views.TrainingViewHolder;

import java.util.List;

public class TimerAdapter extends RecyclerView.Adapter<TrainingViewHolder> {
    private List<Training> trainings;
    private OnTrainingSelected onTrainingSelected;

    public void setTrainings(List<Training> trainingTimes) {
        this.trainings = trainingTimes;
    }

    public void setOnTrainingSelected(OnTrainingSelected onTrainingSelected) {
        this.onTrainingSelected = onTrainingSelected;
    }

    @NonNull
    @Override
    public TrainingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.training_layout, parent, false);
        return new TrainingViewHolder(view, onTrainingSelected);
    }

    @Override
    public void onBindViewHolder(@NonNull TrainingViewHolder holder, int position) {
        holder.bindData(trainings.get(position));
    }

    @Override
    public int getItemCount() {
        if (trainings == null) {
            return 0;
        }
        return trainings.size();
    }
}
