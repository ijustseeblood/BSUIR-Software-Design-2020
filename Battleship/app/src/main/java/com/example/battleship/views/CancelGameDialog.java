package com.example.battleship.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.example.battleship.R;
import com.example.battleship.models.OnCancelGame;

public class CancelGameDialog extends DialogFragment {
    private OnCancelGame onCancelGame;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle(R.string.cancel_game_title).setMessage(R.string.canceling_game_consequnce)
                .setPositiveButton(getString(R.string.ok), (dialog, which) -> {
                    onCancelGame.cancelGame();
                }).setNegativeButton(getString(R.string.no), (dialog, which) -> {

                });
        return builder.create();
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        FragmentActivity activity = getActivity();
        if (activity instanceof OnCancelGame) {
            onCancelGame = (OnCancelGame) activity;
        }
    }
}
