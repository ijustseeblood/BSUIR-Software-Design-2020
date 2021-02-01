package com.example.tabatatimer.views;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tabatatimer.R;
import com.example.tabatatimer.models.OnFinishWorkout;
import com.example.tabatatimer.models.OnTrainingSelected;
import com.example.tabatatimer.models.SettingsRestorer;
import com.example.tabatatimer.models.Training;
import com.example.tabatatimer.services.TimerService;
import com.example.tabatatimer.viewModels.TimerViewModel;
import com.example.tabatatimer.views.adapters.TimerAdapter;

import java.io.Serializable;
import java.util.List;

import static com.example.tabatatimer.models.constants.secUntilFinishedKey;
import static com.example.tabatatimer.models.constants.timerAction;
import static com.example.tabatatimer.models.constants.trainingsKey;
import static com.example.tabatatimer.models.constants.workoutIdKey;

public class TimerActivity extends AppCompatActivity implements OnFinishWorkout, OnTrainingSelected {
    private TimerViewModel timerVM;
    private RecyclerView trainingsRV;
    private TimerAdapter timerAdapter;
    private TextView timeUntilFinishedTV;
    private boolean isServiceInitialized = false;
    private final String isServiceInitializedKey = "isServiceInitialized";
    private TimerService timerService;
    private long workoutId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_TabataTimer);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        SettingsRestorer.restoreSettings(PreferenceManager.getDefaultSharedPreferences(this), this);
        restoreState(savedInstanceState);
        bindService();
        workoutId = getIntent().getLongExtra(workoutIdKey, 1);
        timerVM = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).
                get(TimerViewModel.class);

        trainingsRV = findViewById(R.id.trainingsRecyclerView);
        timeUntilFinishedTV = findViewById(R.id.timeUntilFinishedTextView);
        TextView trainingTypeTV = findViewById(R.id.trainingTypeTextView);
        ImageButton startTimerBtn = findViewById(R.id.startTimerButton);
        ImageButton pauseTimerBtn = findViewById(R.id.pauseTimerButton);
        ImageButton startNextTrainingBtn = findViewById(R.id.startNextTrainingButton);
        ImageButton startPrevTrainingBtn = findViewById(R.id.startPrevTrainingButton);

        startTimerBtn.setOnClickListener(view -> {
            timerVM.setIsTimerRunning(true);
            startTimer();
        });

        pauseTimerBtn.setOnClickListener(view -> {
            timerVM.setIsTimerRunning(false);
            pauseTimer();
        });

        startNextTrainingBtn.setOnClickListener(view -> startNextTraining());

        startPrevTrainingBtn.setOnClickListener(view -> startPrevTraining());

        timerVM.getCurrentTraining().observe(this, training -> {
            trainingTypeTV.setText(training.getTrainingType().value);
            timeUntilFinishedTV.setText(String.valueOf(training.getSec()));
        });

        timerVM.getIsTimerRunning().observe(this, isTimerRunning -> {
            if (isTimerRunning) {
                startTimerBtn.setVisibility(View.GONE);
                pauseTimerBtn.setVisibility(View.VISIBLE);
            } else {
                startTimerBtn.setVisibility(View.VISIBLE);
                pauseTimerBtn.setVisibility(View.GONE);
            }
        });

        registerBroadcastReceiver();
        initAdapter();
        setListeningTrainings();
    }

    private void startNextTraining() {
        if (timerService != null) {
            timerService.startNextTraining();
        }
    }

    private void startPrevTraining() {
        if (timerService != null) {
            timerService.startPreviousTraining();
        }

    }

    private void startTimer() {
        if (timerService != null) {
            timerService.startTimer();
        }
    }

    private void pauseTimer() {
        if (timerService != null) {
            timerService.pauseTimer();
        }
    }

    private void initAdapter() {
        timerAdapter = new TimerAdapter();
        trainingsRV.setAdapter(timerAdapter);
        timerAdapter.setOnTrainingSelected(this);
    }

    private void setListeningTrainings() {
        timerVM.getTrainings(workoutId).observe(this, trainings -> {
            timerAdapter.setTrainings(trainings);
            timerAdapter.notifyDataSetChanged();
            if (!isServiceInitialized) {
                startService(trainings);
                isServiceInitialized = true;
            }
        });
    }

    private void startService(List<Training> trainings) {
        Intent serviceIntent = new Intent(this, TimerService.class);
        serviceIntent.putExtra(trainingsKey, (Serializable) trainings);
        ContextCompat.startForegroundService(this, serviceIntent);
    }

    private void stopService() {
        Intent serviceIntent = new Intent(this, TimerService.class);
        stopService(serviceIntent);
    }

    private void registerBroadcastReceiver() {
        BroadcastReceiver broadcastMessage = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                timerVM.setCurrentTraining((Training) intent.getSerializableExtra(secUntilFinishedKey));
            }
        };
        this.registerReceiver(broadcastMessage, new IntentFilter(timerAction));
    }

    private void restoreState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            isServiceInitialized = savedInstanceState.getBoolean(isServiceInitializedKey);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(isServiceInitializedKey, isServiceInitialized);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unBindService();
    }

    private void bindService() {
        Intent intent = new Intent(this, TimerService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private void unBindService() {
        unbindService(serviceConnection);
    }

    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder iBinder) {
            TimerService.ServiceBinder serviceBinder = (TimerService.ServiceBinder) iBinder;
            timerService = serviceBinder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
        }
    };

    @Override
    public void finishWorkout() {
        stopService();
        finish();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        TimerDialog timerDialog = new TimerDialog();
        timerDialog.show(fm, "timerDialog");
    }

    @Override
    public void startTraining(int trainingIndex) {
        if (timerService != null) {
            timerService.startTraining(trainingIndex);
        }
    }
}