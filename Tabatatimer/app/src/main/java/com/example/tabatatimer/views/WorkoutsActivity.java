package com.example.tabatatimer.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tabatatimer.R;
import com.example.tabatatimer.models.OnWorkoutOptionClick;
import com.example.tabatatimer.models.SettingsRestorer;
import com.example.tabatatimer.viewModels.WorkoutViewModel;
import com.example.tabatatimer.views.adapters.WorkoutAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static com.example.tabatatimer.models.constants.workoutIdKey;

public class WorkoutsActivity extends AppCompatActivity implements OnWorkoutOptionClick {
    private RecyclerView workoutRV;
    private WorkoutViewModel workoutVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_TabataTimer);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workouts);
        SettingsRestorer.restoreSettings(PreferenceManager.getDefaultSharedPreferences(this), this);

        workoutVM = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).
                get(WorkoutViewModel.class);

        workoutRV = findViewById(R.id.workoutRecyclerView);
        FloatingActionButton addWorkoutBtn = findViewById(R.id.addWorkoutButton);

        addWorkoutBtn.setOnClickListener(view -> workoutVM.insertNewWorkout());
        initAdapter();
    }

    private void initAdapter() {
        WorkoutAdapter workoutAdapter = new WorkoutAdapter(this);
        workoutVM.getAllWorkouts().observe(this, workoutAdapter::submitList);
        workoutRV.setAdapter(workoutAdapter);
    }

    @Override
    public void deleteWorkout(long workoutId) {
        workoutVM.deleteWorkout(workoutId);
    }

    @Override
    public void editWorkout(long workoutId) {
        Intent intent = new Intent(this, WorkoutSettingsActivity.class);
        intent.putExtra(workoutIdKey, workoutId);
        startActivity(intent);
    }

    @Override
    public void startWorkout(long workoutId) {
        Intent intent = new Intent(this, TimerActivity.class);
        intent.putExtra(workoutIdKey, workoutId);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_settings_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.app_settings_item) {
            Intent intent = new Intent(this, AppSettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}