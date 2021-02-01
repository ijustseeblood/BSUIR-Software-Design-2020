package com.example.tabatatimer.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

import com.example.tabatatimer.R;
import com.example.tabatatimer.models.CountDownTimer;
import com.example.tabatatimer.models.OnTimerTick;
import com.example.tabatatimer.models.Training;

import java.util.List;

import static com.example.tabatatimer.models.constants.secUntilFinishedKey;
import static com.example.tabatatimer.models.constants.timerAction;
import static com.example.tabatatimer.models.constants.trainingsKey;

public class TimerService extends Service implements OnTimerTick {
    private final String channelId = "ForegroundServiceChannel";
    private final IBinder serviceBinder = new ServiceBinder();
    private CountDownTimer countDownTimer;
    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        mediaPlayer = MediaPlayer.create(this, R.raw.bell);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Notification notification = createNotification();
        startForeground(1, notification);
        List<Training> trainings = (List<Training>) intent.getSerializableExtra(trainingsKey);
        countDownTimer = new CountDownTimer(trainings, this);
        startTimer();
        return START_NOT_STICKY;
    }

    public void startTimer() {
        countDownTimer.startTimer();
    }

    public void pauseTimer() {
        countDownTimer.pauseTimer();
    }

    public void startNextTraining() {
        countDownTimer.startNextTraining();
    }

    public void startPreviousTraining() {
        countDownTimer.startPreviousTraining();
    }

    public void startTraining(int trainingIndex) {
        countDownTimer.startTraining(trainingIndex);
    }

    public class ServiceBinder extends Binder {
        public TimerService getService() {
            return TimerService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return serviceBinder;
    }

    private Notification createNotification() {
        return new NotificationCompat.Builder(this, channelId)
                .setContentTitle(getString(R.string.workout_is_running))
                .setSmallIcon(R.drawable.ic_timer)
                .setVisibility(NotificationCompat.VISIBILITY_PRIVATE)
                .build();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    channelId,
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

    @Override
    public void broadcastTimerNumSeconds(Training currentTraining) {
        Intent intent = new Intent();
        intent.setAction(timerAction);
        intent.putExtra(secUntilFinishedKey, currentTraining);
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        sendBroadcast(intent);
    }

    public void stopMediaPlayer() {
        mediaPlayer.stop();
        mediaPlayer.reset();
        mediaPlayer.release();
    }

    @Override
    public void playMelody() {
        if (mediaPlayer.isPlaying()) {
            stopMediaPlayer();
            mediaPlayer = MediaPlayer.create(this, R.raw.bell);
        }
        mediaPlayer.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        pauseTimer();
        stopMediaPlayer();
    }


}
