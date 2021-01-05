package com.example.converter;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @SuppressLint("NonConstantResourceId")
    public void onClickCategory(View view) {

        switch (view.getId()) {
            case R.id.btnCurrency: {
                Intent intent = new Intent(this, ConverterActivity.class);
                intent.putExtra("category", "currency");
                startActivity(intent);
            }
            break;
            case R.id.btnDistance: {
                Intent intent = new Intent(this, ConverterActivity.class);
                intent.putExtra("category", "distance");
                startActivity(intent);
            }
            break;
            case R.id.btnWeight: {
                Intent intent = new Intent(this, ConverterActivity.class);
                intent.putExtra("category", "weight");
                startActivity(intent);
            }
            break;
            default: {
                Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
            break;
        }
    }
}