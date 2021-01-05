package com.example.converter;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class DataFragment extends Fragment {

    private ConvertViewModel convertViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        convertViewModel = ViewModelProviders.of(getActivity()).get(ConvertViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_data, container, false);
        TextView fromText = view.findViewById(R.id.textFrom);
        TextView toText = view.findViewById(R.id.textTo);
        Spinner toSpinner = view.findViewById(R.id.toSpinner);
        Spinner fromSpinner = view.findViewById(R.id.fromSpinner);
        Button fromCopy = view.findViewById(R.id.fromCopy);
        Button toCopy = view.findViewById(R.id.toCopy);

        fromSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                convertViewModel.setPercent(fromSpinner.getSelectedItem().toString(), toSpinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        toSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                convertViewModel.setPercent(fromSpinner.getSelectedItem().toString(), toSpinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        fromCopy.setOnClickListener(view1 -> {
            convertViewModel.copyInBufferDataFrom((ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE));
            Toast.makeText(getContext(), "Value has been copied successfully!", Toast.LENGTH_SHORT).show();
        });

        toCopy.setOnClickListener(view12 -> {
            convertViewModel.copyInBufferDataTo((ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE));
            Toast.makeText(getContext(), "Value has been copied successfully!", Toast.LENGTH_SHORT).show();
        });

        convertViewModel.getNum().observe(requireActivity(), fromText::setText);
        convertViewModel.getResult().observe(requireActivity(), toText::setText);
        return view;
    }
}