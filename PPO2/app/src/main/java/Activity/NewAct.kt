package Activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.ppo2.DatabaseAdapter
import com.example.ppo2.Params
import com.example.ppo2.R
import com.jaredrummler.android.colorpicker.ColorPickerDialog
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener
import com.jaredrummler.android.colorpicker.ColorShape

class NewAct : AppCompatActivity(), ColorPickerDialogListener {
    private var etTitle: EditText? = null
    private var etPrepare: EditText? = null
    private var etWork: EditText? = null
    private var etChill: EditText? = null
    private var etCycles: EditText? = null
    private var etSets: EditText? = null
    private var etSetChill: EditText? = null
    private var btnColor: Button? = null
    private var btnSave: Button? = null
    private var adapter: DatabaseAdapter? = null
    private var color = 0
    private var id = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        etTitle = findViewById<View>(R.id.etTitle) as EditText
        etPrepare = findViewById<View>(R.id.etPrepare) as EditText
        etWork = findViewById<View>(R.id.etWork) as EditText
        etChill = findViewById<View>(R.id.etChill) as EditText
        etCycles = findViewById<View>(R.id.etCycles) as EditText
        etSets = findViewById<View>(R.id.etSets) as EditText
        etSetChill = findViewById<View>(R.id.etSetChill) as EditText
        btnColor = findViewById<View>(R.id.btnColor) as Button
        btnSave = findViewById<View>(R.id.btnColor) as Button
        adapter = DatabaseAdapter(this)
        val extras = intent.extras
        if (extras != null) {
            id = extras.getInt("id")
        }
        if (id > 0) {
            adapter!!.open()
            val sequence = adapter!!.getSequence(id)
            etTitle!!.setText(sequence.title)
            etPrepare!!.setText(sequence.prepare.toString())
            etWork!!.setText(sequence.work.toString())
            etChill!!.setText(sequence.chill.toString())
            etCycles!!.setText(sequence.cycle.toString())
            etSets!!.setText(sequence.sets.toString())
            etSetChill!!.setText(sequence.setChill.toString())
            color = sequence.color
            adapter!!.close()
        } else {
        }
    }

    override fun onColorSelected(dialogId: Int, color: Int) {
        this.color = color
    }

    override fun onDialogDismissed(dialogId: Int) {}
    private fun createColorPickerDialog() {
        ColorPickerDialog.newBuilder()
                .setColor(Color.RED)
                .setDialogType(ColorPickerDialog.TYPE_PRESETS)
                .setAllowCustom(false)
                .setAllowPresets(true)
                .setColorShape(ColorShape.CIRCLE)
                .setShowAlphaSlider(false)
                .setShowColorShades(false)
                .show(this)
    }

    fun onBtnColorClick(view: View?) {
        createColorPickerDialog()
    }

    fun onBtnSaveClick(view: View?) {
        val title = etTitle!!.text.toString()
        val prepare = etPrepare!!.text.toString().toInt()
        val work = etWork!!.text.toString().toInt()
        val chill = etChill!!.text.toString().toInt()
        val cycles = etCycles!!.text.toString().toInt()
        val sets = etSets!!.text.toString().toInt()
        val setChill = etSetChill!!.text.toString().toInt()
        val sequence = Params(id, color, title, prepare, work, chill, cycles, sets, setChill)
        adapter!!.open()
        if (id > 0) {
            adapter!!.update(sequence)
        } else {
            adapter!!.insert(sequence)
        }
        adapter!!.close()
        goHome()
    }

    private fun goHome() {
        // переход к главной activity
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
    }
}