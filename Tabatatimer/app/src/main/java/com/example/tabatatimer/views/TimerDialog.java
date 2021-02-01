package com.example.tabatatimer.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.example.tabatatimer.R;
import com.example.tabatatimer.models.OnFinishWorkout;

public class TimerDialog extends DialogFragment {
    private OnFinishWorkout onFinishWorkout;

    public TimerDialog() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle(R.string.timer_dialog_title).setMessage(R.string.timer_dialog_message)
                .setPositiveButton(R.string.positive_action, (dialog, id) -> {
                    if (onFinishWorkout != null) {
                        onFinishWorkout.finishWorkout();
                    }
                })
                .setNegativeButton(R.string.negative_action, (dialog, id) -> {
                });
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        FragmentActivity activity = getActivity();
        if (activity instanceof OnFinishWorkout) {
            onFinishWorkout = (OnFinishWorkout) activity;
        }
    }
}
