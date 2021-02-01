package com.example.tabatatimer.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.example.tabatatimer.R;
import com.example.tabatatimer.models.OnColorPicked;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

public class ColorPickerDialog extends DialogFragment {
    private OnColorPicked onColorPicked;

    public ColorPickerDialog() {

    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return ColorPickerDialogBuilder.with(this.getContext()).setTitle(getString(R.string.color_picker_title))
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .setPositiveButton(getString(R.string.positive_action), (dialog, selectedColor, allColors) -> {
                    if (onColorPicked != null) {
                        onColorPicked.pickColor(selectedColor);
                    }
                }).build();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        FragmentActivity activity = getActivity();
        if (activity instanceof OnColorPicked) {
            onColorPicked = (OnColorPicked) activity;
        }
    }
}
