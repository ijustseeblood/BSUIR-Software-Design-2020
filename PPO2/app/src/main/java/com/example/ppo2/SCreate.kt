package com.example.ppo2

import android.app.Service
import android.content.Intent
import android.media.AudioManager
import android.media.SoundPool
import android.os.Binder
import android.os.CountDownTimer
import android.os.IBinder
import java.util.*

class SCreate : Service() {
    var binder = MyBinder()
    private var countDownTimer: CountDownTimer? = null
    var timeLeft: Long = 0
        private set
    var pos = 0
        private set
    private var maxPos = 0
    private var modes: ArrayList<Int>? = null
    private var sp: SoundPool? = null
    private var soundIdBell = 0
    override fun onCreate() {
        super.onCreate()
        sp = SoundPool(5, AudioManager.STREAM_MUSIC, 0)
        soundIdBell = sp!!.load(this, R.raw.bell, 1)
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        modes = intent.getIntegerArrayListExtra("modes")
        maxPos = modes!!.size
        timeLeft = intent.getLongExtra("timeLeft", 0)
        pos = intent.getIntExtra("pos", 0)
        timer()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(arg0: Intent): IBinder? {
        return binder
    }

    override fun onUnbind(intent: Intent): Boolean {
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer!!.cancel()
    }

    fun startTimer() {
        if (countDownTimer != null) {
            countDownTimer!!.cancel()
        }
        timeLeft = modes!![pos] * 1000.toLong()
        timer()
    }

    operator fun next() {
        if (pos == maxPos - 1) {
            pos++
            if (countDownTimer != null) {
                countDownTimer!!.cancel()
            }
        } else if (pos == maxPos) {
        } else {
            pos++
            startTimer()
        }
    }

    fun timer() {
        countDownTimer = object : CountDownTimer(timeLeft, 1000) {
            override fun onTick(l: Long) {
                timeLeft = l
            }

            override fun onFinish() {
                sp!!.play(soundIdBell, 1f, 1f, 0, 0, 1f)
                next()
            }
        }.start()
    }

    inner class MyBinder : Binder() {
        val service: SCreate
            get() = this@SCreate
    }
}