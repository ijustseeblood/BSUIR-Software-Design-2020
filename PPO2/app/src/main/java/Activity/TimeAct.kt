package Activity

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.media.AudioManager
import android.media.SoundPool
import android.os.Bundle
import android.os.CountDownTimer
import android.os.IBinder
import android.util.Pair
import android.view.View
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.ppo2.DatabaseAdapter
import com.example.ppo2.ModeAdapter
import com.example.ppo2.SCreate
import com.example.ppo2.SCreate.MyBinder
import com.example.ppo2.R
import java.util.*

class TimeAct : AppCompatActivity() {
    lateinit var tvTimer: TextView
    lateinit var lvModes: ListView
    lateinit var adapter: DatabaseAdapter
    lateinit var btnPause: ImageButton
    lateinit var btnPrev: ImageButton
    lateinit var btnNext: ImageButton
    lateinit var tvItemName: TextView
    lateinit var countDownTimer: CountDownTimer
    var id = 0
    var prepare = 0
    var work = 0
    var chill = 0
    var cycles = 0
    var sets = 0
    var setChill = 0
    var isPause = false
    var timeLeft: Long = 0
    var endTime: Long = 0
    var pos = 0
    var maxPos = 0
    lateinit var modes: ArrayList<Pair<String, Int>>
    lateinit var sp: SoundPool
    var soundIdBell = 0
    var bound = false
    var needService = false
    lateinit var sConn: ServiceConnection
    lateinit var myService: SCreate
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)
        tvTimer = findViewById<View>(R.id.tvTimer) as TextView
        lvModes = findViewById<View>(R.id.lvModes) as ListView
        tvItemName = findViewById<View>(R.id.tvItemName) as TextView
        btnPause = findViewById<View>(R.id.btnPause) as ImageButton
        btnPrev = findViewById<View>(R.id.btnPrev) as ImageButton
        btnNext = findViewById<View>(R.id.btnNext) as ImageButton
        sp = SoundPool(5, AudioManager.STREAM_MUSIC, 0)
        soundIdBell = sp.load(this, R.raw.bell, 1)
        isPause = false
        btnPause.setOnClickListener {
            if (isPause) {
                if (pos < maxPos) {
                    btnPause.setImageResource(android.R.drawable.ic_media_pause)
                    isPause = false
                    timer()
                }
            } else {
                btnPause.setImageResource(android.R.drawable.ic_media_play)
                isPause = true
                countDownTimer.cancel()
            }
        }
        btnNext.setOnClickListener { next() }
        btnPrev.setOnClickListener {
            if (pos > 0) {
                pos--
                countDownTimer.cancel()
                startTimer()
            }
        }
        adapter = DatabaseAdapter(this)
        val extras = getIntent().extras
        if (extras != null) {
            id = extras.getInt("id")
        }
        if (id > 0) {
            adapter.open()
            val sequence = adapter.getSequence(id)
            prepare = sequence!!.prepare
            work = sequence.work
            chill = sequence.chill
            cycles = sequence.cycle
            sets = sequence.sets
            setChill = sequence.setChill
            adapter.close()
        }
        modes = ArrayList()
        modes.add(Pair(getString(R.string.prepare), prepare))
        for (i in 0 until sets) {
            for (j in 0 until cycles) {
                modes.add(Pair(getString(R.string.work), work))
                modes.add(Pair(getString(R.string.chill), chill))
            }
            modes.add(Pair(getString(R.string.setChill), setChill))
        }
        maxPos = 1 + sets * (cycles * 2 + 1)
        val modeAdapter = ModeAdapter(this,
                R.layout.mode_item, modes)
        lvModes.adapter = modeAdapter
        timeLeft = modes[pos].second * 1000.toLong()
        needService = true
        intent = Intent(this, SCreate::class.java)
        val temp = ArrayList<Int>()
        for (i in 0 until maxPos) {
            temp.add(modes[i].second)
        }
        intent.putExtra("modes", temp)
        sConn = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName, binder: IBinder) {
                myService = (binder as MyBinder).service
                bound = true
            }

            override fun onServiceDisconnected(name: ComponentName) {
                bound = false
            }
        }
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onResume() {
        super.onResume()
        if (bound) {
            pos = myService.pos
            timeLeft = myService.timeLeft
            unbindService(sConn)
            stopService(intent)
            bound = false
        }
        if (isPause) {
            btnPause.setImageResource(android.R.drawable.ic_media_play)
        } else {
            btnPause.setImageResource(android.R.drawable.ic_media_pause)
        }
        tvTimer.setText((timeLeft / 1000).toString())
        tvItemName.text = modes[pos].first
        if (!isPause) {
            timer()
        }
        lvModes.setSelection(pos)
    }

    override fun onPause() {
        super.onPause()
        if (needService && !bound && !isPause) {
            intent.putExtra("pos", pos)
            intent.putExtra("timeLeft", timeLeft)
            startService(intent)
            bindService(intent, sConn, Context.BIND_AUTO_CREATE)
            bound = true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (bound) {
            unbindService(sConn)
            stopService(intent)
            bound = false
        }
    }

    fun startTimer() {
        countDownTimer.cancel()
        tvItemName.text = modes[pos].first
        timeLeft = modes[pos].second * 1000.toLong()
        tvTimer.setText((timeLeft / 1000).toString())
        if (!isPause) {
            timer()
        }
        lvModes.setSelection(pos)
    }

    operator fun next() {
        if (pos == maxPos - 1) {
            pos++
            countDownTimer.cancel()
            tvTimer.setText(R.string.finish)
            tvItemName.setText(R.string.finish)
        } else if (pos == maxPos) {
        } else {
            pos++
            startTimer()
        }
    }

    fun timer() {
        endTime = timeLeft + System.currentTimeMillis()
        countDownTimer = object : CountDownTimer(timeLeft, 1000) {
            override fun onTick(l: Long) {
                timeLeft = l
                tvTimer.setText((l / 1000).toString())
            }

            override fun onFinish() {
                sp.play(soundIdBell, 1f, 1f, 0, 0, 1f)
                next()
            }
        }.start()
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this)
                .setTitle(R.string.finishTraining)
                .setMessage(R.string.finishTrainingSure)
                .setPositiveButton(R.string.yes) { dialog, whichButton ->
                    countDownTimer.cancel()
                    needService = false
                    finish()
                    dialog.dismiss()
                }.setNegativeButton(R.string.no) { dialog, whichButton ->
                    dialog.dismiss()
                }.show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("isPause", isPause)
        outState.putInt("pos", pos)
        outState.putLong("timeLeft", timeLeft)
        outState.putLong("endTime", endTime)
        countDownTimer.cancel()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        isPause = savedInstanceState.getBoolean("isPause")
        pos = savedInstanceState.getInt("pos")
        timeLeft = savedInstanceState.getLong("timeLeft")
        endTime = savedInstanceState.getLong("endTime")
        if (!isPause) {
            timeLeft = endTime - System.currentTimeMillis()
        }
    }
}