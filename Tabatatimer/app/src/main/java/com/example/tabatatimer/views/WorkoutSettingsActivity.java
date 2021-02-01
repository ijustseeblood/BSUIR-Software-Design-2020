package com.example.tabatatimer.views;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import com.example.tabatatimer.R;
import com.example.tabatatimer.data.Workout;
import com.example.tabatatimer.models.OnColorPicked;
import com.example.tabatatimer.models.SettingsRestorer;
import com.example.tabatatimer.viewModels.WorkoutViewModel;

import static com.example.tabatatimer.models.constants.workoutIdKey;

public class WorkoutSettingsActivity extends AppCompatActivity implements OnColorPicked {
    private final String pickedColorKey = "pickedColor";
    private int pickedColor;
    private long workoutId;
    private WorkoutViewModel workoutVM;
    private EditText workoutTitle;
    private EditText workoutPrepare;
    private EditText workoutWork;
    private EditText workoutRest;
    private EditText workoutRestBtwSets;
    private EditText workoutCoolDown;
    private EditText workoutSets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_TabataTimer);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_settings);
        SettingsRestorer.restoreSettings(PreferenceManager.getDefaultSharedPreferences(this), this);

        workoutId = getIntent().getLongExtra(workoutIdKey, 1);
        workoutVM = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).
                get(WorkoutViewModel.class);

        workoutTitle = findViewById(R.id.workoutTitleEditText);
        workoutPrepare = findViewById(R.id.workoutPrepareEditText);
        workoutWork = findViewById(R.id.workoutWorkEditText);
        workoutRest = findViewById(R.id.workoutRestEditText);
        workoutRestBtwSets = findViewById(R.id.workoutRestBtwSetsEditText);
        workoutCoolDown = findViewById(R.id.workoutCoolDownEditText);
        workoutSets = findViewById(R.id.workoutSetsEditText);

        restoreState(savedInstanceState);
        observeWorkoutDataOnce();

        findViewById(R.id.saveWorkoutButton).setOnClickListener(view -> {
            Workout workout = new Workout(
                    workoutTitle.getText().toString(),
                    pickedColor,
                    Long.parseLong(workoutPrepare.getText().toString()),
                    Long.parseLong(workoutWork.getText().toString()),
                    Long.parseLong(workoutRest.getText().toString()),
                    Long.parseLong(workoutRestBtwSets.getText().toString()),
                    Long.parseLong(workoutCoolDown.getText().toString()),
                    Long.parseLong(workoutSets.getText().toString()));
            workout.setId(workoutId);

            workoutVM.updateWorkout(workout);
            finish();
        });

    }

    private void observeWorkoutDataOnce() {
        LiveData<Workout> workoutLiveData = workoutVM.getWorkout(workoutId);
        workoutLiveData.observe(this, workout -> {
            pickedColor = workout.getColor();
            workoutTitle.setText(workout.getTitle());
            workoutPrepare.setText(String.valueOf(workout.getPrepareSec()));
            workoutWork.setText(String.valueOf(workout.getWorkSec()));
            workoutRest.setText(String.valueOf(workout.getRestSec()));
            workoutRestBtwSets.setText(String.valueOf(workout.getRestBtwSetsSec()));
            workoutCoolDown.setText(String.valueOf(workout.getCoolDownSec()));
            workoutSets.setText(String.valueOf(workout.getSets()));
        });
    }

    private void restoreState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            pickedColor = savedInstanceState.getInt(pickedColorKey, getResources().getColor(R.color.colorPrimary));
        }
    }

    private void displayColorPicker() {
        FragmentManager fm = getSupportFragmentManager();
        ColorPickerDialog colorPickerDialog = new ColorPickerDialog();
        colorPickerDialog.show(fm, "colorPicker");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(pickedColorKey, pickedColor);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.workouts_settings_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.palette_item) {
            displayColorPicker();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void pickColor(int pickedColor) {
        this.pickedColor = pickedColor;
    }
}