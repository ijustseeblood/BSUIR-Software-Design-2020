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
import com.example.battleship.models.OnFinishGame;

public class GameFinishedDialog extends DialogFragment {
    private OnFinishGame onFinishGame;
    private String gameFinishedText;

    public GameFinishedDialog() {
    }


    public void setGameFinishedText(String gameFinishedText) {
        this.gameFinishedText = gameFinishedText;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle(R.string.game_is_finished_title).setMessage(gameFinishedText)
                .setPositiveButton(getString(R.string.ok), (dialog, which) -> {
                    onFinishGame.finishGame();
                });
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        FragmentActivity activity = getActivity();
        if (activity instanceof OnFinishGame) {
            onFinishGame = (OnFinishGame) activity;
        }
    }
}
