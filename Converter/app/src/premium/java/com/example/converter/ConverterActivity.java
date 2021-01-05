package com.example.converter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class ConverterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);
        Spinner fromSpinner = findViewById(R.id.fromSpinner);
        Spinner toSpinner = findViewById(R.id.toSpinner);

        Intent intent = getIntent();
        String category = intent.getStringExtra("category");

        switch (category) {
            case "weight": {
                ArrayAdapter<?> adapter =
                        ArrayAdapter.createFromResource(this, R.array.weight, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                fromSpinner.setAdapter(adapter);
                toSpinner.setAdapter(adapter);
            }
            break;
            case "distance": {
                ArrayAdapter<?> adapter =
                        ArrayAdapter.createFromResource(this, R.array.distance, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                fromSpinner.setAdapter(adapter);
                toSpinner.setAdapter(adapter);
            }
            break;
            case "currency": {
                ArrayAdapter<?> adapter =
                        ArrayAdapter.createFromResource(this, R.array.currency, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                fromSpinner.setAdapter(adapter);
                toSpinner.setAdapter(adapter);
            }
        }
        toSpinner.setSelection(2);
    }
}