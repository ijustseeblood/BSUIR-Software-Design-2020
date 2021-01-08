package com.example.converter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class KeyboardFragment extends Fragment {

    ConvertViewModel convertViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        convertViewModel = ViewModelProviders.of(getActivity()).get(ConvertViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_keyboard, container, false);
        view.findViewById(R.id.btn0).setOnClickListener(view1 -> convertViewModel.setItem("0"));
        view.findViewById(R.id.btn1).setOnClickListener(view1 -> convertViewModel.setItem("1"));
        view.findViewById(R.id.btn2).setOnClickListener(view1 -> convertViewModel.setItem("2"));
        view.findViewById(R.id.btn3).setOnClickListener(view1 -> convertViewModel.setItem("3"));
        view.findViewById(R.id.btn4).setOnClickListener(view1 -> convertViewModel.setItem("4"));
        view.findViewById(R.id.btn5).setOnClickListener(view1 -> convertViewModel.setItem("5"));
        view.findViewById(R.id.btn6).setOnClickListener(view1 -> convertViewModel.setItem("6"));
        view.findViewById(R.id.btn7).setOnClickListener(view1 -> convertViewModel.setItem("7"));
        view.findViewById(R.id.btn8).setOnClickListener(view1 -> convertViewModel.setItem("8"));
        view.findViewById(R.id.btn9).setOnClickListener(view1 -> convertViewModel.setItem("9"));
        view.findViewById(R.id.btnDot).setOnClickListener(view1 -> convertViewModel.setItem("."));
        view.findViewById(R.id.btnC).setOnClickListener(view12 -> convertViewModel.delete());
        view.findViewById(R.id.btnConvert).setOnClickListener(view13 -> convertViewModel.convert());
        return view;
    }
}