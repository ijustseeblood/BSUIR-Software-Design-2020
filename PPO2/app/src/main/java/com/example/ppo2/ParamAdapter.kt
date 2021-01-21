package com.example.ppo2

import Activity.NewAct
import Activity.MainActivity
import Activity.TimeAct
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.TextView
import androidx.preference.PreferenceManager

internal class ParamAdapter(context: Context, private val layout: Int, private val sequenceList: List<Params>) : ArrayAdapter<Params?>(context, layout, sequenceList) {
    private val inflater: LayoutInflater
    private val mCtx: Context
    private val adapter: DatabaseAdapter
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val viewHolder: ViewHolder
        if (convertView == null) {
            convertView = inflater.inflate(layout, parent, false)
            viewHolder = ViewHolder(convertView)
            convertView.tag = viewHolder
        } else {
            viewHolder = convertView.tag as ViewHolder
        }
        val sequence = sequenceList[position]
        viewHolder.tvTitleItem.text = sequence.title
        val prefs = PreferenceManager
                .getDefaultSharedPreferences(mCtx)
        try {
            val fSize = prefs.getString(
                    mCtx.getString(R.string.pref_size), "18")!!.toFloat()
            viewHolder.tvTitleItem.textSize = fSize
        } catch (ex: Exception) {
            viewHolder.tvTitleItem.textSize = 18f
        }
        viewHolder.item.setBackgroundColor(sequence.color)
        viewHolder.item.setOnClickListener {
            if (sequence != null) {
                val intent = Intent(mCtx, TimeAct::class.java)
                intent.putExtra("id", sequence.id)
                intent.putExtra("click", 25)
                mCtx.startActivity(intent)
            }
        }
        try {
            viewHolder.tvOptions.setOnClickListener { v ->
                when (v.id) {
                    R.id.tvOptions -> {
                        val popup = PopupMenu(mCtx, v)
                        popup.menuInflater.inflate(R.menu.item_menu,
                                popup.menu)
                        popup.show()
                        popup.setOnMenuItemClickListener { item ->
                            when (item.itemId) {
                                R.id.edit -> {
                                    val intent = Intent(mCtx, NewAct::class.java)
                                    intent.putExtra("id", sequence.id)
                                    intent.putExtra("click", 25)
                                    mCtx.startActivity(intent)
                                }
                                R.id.delete -> {
                                    adapter.open()
                                    adapter.delete(sequence.id)
                                    adapter.close()
                                    mCtx.startActivity(Intent(mCtx, MainActivity::class.java))
                                }
                                else -> {
                                }
                            }
                            true
                        }
                    }
                    else -> {
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return convertView!!
    }

    private inner class ViewHolder internal constructor(view: View) {
        val tvTitleItem: TextView
        val tvOptions: TextView
        val item: LinearLayout

        init {
            tvTitleItem = view.findViewById<View>(R.id.tvTitleItem) as TextView
            item = view.findViewById<View>(R.id.item) as LinearLayout
            tvOptions = view.findViewById<View>(R.id.tvOptions) as TextView
        }
    }

    init {
        inflater = LayoutInflater.from(context)
        mCtx = context
        adapter = DatabaseAdapter(context)
    }
}