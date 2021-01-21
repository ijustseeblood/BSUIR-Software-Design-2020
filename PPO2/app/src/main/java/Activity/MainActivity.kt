package Activity

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.preference.PreferenceManager
import com.example.ppo2.DatabaseAdapter
import com.example.ppo2.R
import com.example.ppo2.ParamAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class MainActivity : AppCompatActivity() {
    private var list: ListView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupSettings()
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        list = findViewById<View>(R.id.list) as ListView
    }

    public override fun onResume() {
        super.onResume()
        val adapter = DatabaseAdapter(this)
        adapter.open()
        val sequences = adapter.sequences
        val arrayAdapter = ParamAdapter(this, R.layout.list_item, sequences)
        list!!.adapter = arrayAdapter
        adapter.close()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_settings) {
            val intent = Intent(applicationContext, SettingsActivity::class.java)
            startActivity(intent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun onBtnAddClick(v: View?) {
        val intent = Intent(this, NewAct::class.java)
        startActivity(intent)
    }

    fun setupSettings() {
        val sharedPreference = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        var theme = sharedPreference.getString("theme", "day")
        var font = sharedPreference.getString("font", "big")
        var lang = sharedPreference.getString("lang", "ru")
        if (theme.equals("night")) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            delegate.applyDayNight()
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            delegate.applyDayNight()
        }
        if (lang.equals("ru")) {
            var locale = Locale("ru")
            val res = resources
            val dm = res.displayMetrics
            val conf = res.configuration
            conf.locale = locale
            res.updateConfiguration(conf, dm)
        } else {
            var locale = Locale("en")
            val res = resources
            val dm = res.displayMetrics
            val conf = res.configuration
            conf.locale = locale
            res.updateConfiguration(conf, dm)
        }
        if (font.equals("big")) {
            val configuration: Configuration = resources.configuration
            configuration.fontScale = 0.8F
            getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());
        } else {
            val configuration: Configuration = resources.configuration
            configuration.fontScale = 1F
            getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());
        }
    }
}