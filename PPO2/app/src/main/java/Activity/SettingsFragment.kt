package Activity

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.example.ppo2.DatabaseAdapter
import com.example.ppo2.R
import java.util.*


class SettingsFragment : PreferenceFragmentCompat() {
    lateinit var settingsListener : ISettings
    lateinit var sharedPreference: SharedPreferences
    lateinit var editor : SharedPreferences.Editor

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        sharedPreference = PreferenceManager.getDefaultSharedPreferences(activity?.applicationContext)
        editor = sharedPreference?.edit()
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        val button = findPreference<Preference>("clear")
        button!!.onPreferenceClickListener = Preference.OnPreferenceClickListener {

            val prefs = PreferenceManager
                    .getDefaultSharedPreferences(context)
            prefs.edit().clear().commit()
            val adapter = DatabaseAdapter(context!!)
            adapter.open()
            adapter.clear()
            adapter.close()
            true
        }
        val themePreference: ListPreference? = findPreference("theme")
        themePreference?.onPreferenceChangeListener = object : Preference.OnPreferenceChangeListener {
            override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
                if (newValue == "day") {
                    editor?.putString("theme", "day")
                    settingsListener.setDayTheme()
                } else {
                    editor?.putString("theme", "night")
                    settingsListener.setNightTheme()
                }
                editor?.apply()
                activity?.recreate()
                return true
            }
        }

        val fontPref: ListPreference? = findPreference("font")
        fontPref?.onPreferenceChangeListener = object : Preference.OnPreferenceChangeListener {
            override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
                if (newValue == "big") {
                    editor?.putString("font", "big");
                    val configuration: Configuration = resources.configuration
                    configuration.fontScale = 0.8F
                    getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());
                    activity!!.recreate()
                } else {
                    editor?.putString("font", "tiny");
                    val configuration: Configuration = resources.configuration
                    configuration.fontScale = 1F
                    getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());
                    activity!!.recreate()
                }
                editor?.apply();
                return true
            }
        }

        val langPref: ListPreference? = findPreference("lang")
        langPref?.onPreferenceChangeListener = object : Preference.OnPreferenceChangeListener {
            override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
                if (newValue == "eng") {
                    editor?.putString("lang", "en");
                    var locale = Locale("en")
                    val res = resources
                    val dm = res.displayMetrics
                    val conf = res.configuration
                    conf.locale = locale
                    res.updateConfiguration(conf, dm)
                    activity!!.recreate()
                } else {
                    editor?.putString("lang", "ru");
                    var locale = Locale("ru")
                    val res = resources
                    val dm = res.displayMetrics
                    val conf = res.configuration
                    conf.locale = locale
                    res.updateConfiguration(conf, dm)
                    activity!!.recreate()
                }
                editor?.apply();
                return true
            }
        }

    }
    companion object {

        @JvmStatic
        fun newInstance(settingsL: ISettings) =
                SettingsFragment().apply {
                    settingsListener=settingsL
                }

    }

}