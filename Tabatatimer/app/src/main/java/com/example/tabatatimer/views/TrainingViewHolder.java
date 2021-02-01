package com.example.tabatatimer.views;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tabatatimer.R;
import com.example.tabatatimer.models.OnTrainingSelected;
import com.example.tabatatimer.models.Training;

public class TrainingViewHolder extends RecyclerView.ViewHolder {
    private final TextView trainingTimeTV;
    private final OnTrainingSelected onTrainingSelected;

    public TrainingViewHolder(@NonNull View itemView, OnTrainingSelected onTrainingSelected) {
        super(itemView);
        this.onTrainingSelected = onTrainingSelected;
        trainingTimeTV = itemView.findViewById(R.id.trainingInfoTextView);
    }

    public void bindData(Training training) {
        itemView.setOnClickListener(view -> {
            onTrainingSelected.startTraining(getAdapterPosition());
        });
        String trainingInfo = getAdapterPosition() + 1 + ". " + training.getTrainingType().value + ": " + training.getSec();
        trainingTimeTV.setText(trainingInfo);
    }
}
