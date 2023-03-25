package com.example.tobias.werwolf_v1

import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class NightListAdapter(private val data: Cursor) : BaseAdapter() {
    private var pers: TextView? = null
    private var icon: ImageView? = null
    override fun getCount(): Int {
        return data.count
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View, parent: ViewGroup): View {
        var convertView = convertView
        convertView = layoutInflater.inflate(R.layout.mylistitemicon, null)
        pers = convertView.findViewById<View>(R.id.textPer) as TextView
        icon = convertView.findViewById<View>(R.id.iconListe) as ImageView
        data!!.moveToPosition(position)
        when (charakterPosition) {
            0 -> {
                if (data!!.getInt(0) == liebenderEinsID) {
                    icon!!.setImageResource(R.drawable.icon_heart)
                }
                if (data!!.getInt(0) == liebenderZweiID) {
                    icon!!.setImageResource(R.drawable.icon_heart)
                }
            }
            2 -> if (data!!.getInt(0) == schlafplatzDiebID) {
                icon!!.setImageResource(R.drawable.icon_thief)
            }
            3 -> if (data!!.getInt(0) == schlafplatzWaechterID) {
                icon!!.setImageResource(R.drawable.icon_shield)
            }
            4 -> if (data!!.getInt(0) == vorbildID) {
                icon!!.setImageResource(R.drawable.icon_wolf)
            }
            6 -> {
                if (data!!.getString(4).compareTo("ja") == 0) {
                    icon!!.setImageResource(R.drawable.icon_magic)
                }
                if (data!!.getInt(0) == verzaubertAktuell) {
                    icon!!.setImageResource(R.drawable.icon_magic)
                }
            }
            7 -> {
                if (data!!.getInt(0) == werwolfOpferID) {
                    icon!!.setImageResource(R.drawable.icon_dead)
                }
                if (data!!.getInt(0) == werwolfDurchUrwolfID && werwolfDurchUrwolfID != -1) {
                    icon!!.setImageResource(R.drawable.icon_wolf)
                }
                if (data!!.getString(2).compareTo("werwolf") == 0) {
                    icon!!.setImageResource(R.drawable.icon_wolf)
                }
                if (data!!.getString(2).compareTo("weisserwerwolf") == 0) {
                    icon!!.setImageResource(R.drawable.icon_wolf)
                }
                if (data!!.getString(2).compareTo("urwolf") == 0) {
                    icon!!.setImageResource(R.drawable.icon_wolf)
                }
            }
            8 -> {
                if (data!!.getInt(0) == werwolfDurchUrwolfID && werwolfDurchUrwolfID != -1) {
                    icon!!.setImageResource(R.drawable.icon_wolf)
                }
                if (data!!.getString(2).compareTo("werwolf") == 0) {
                    icon!!.setImageResource(R.drawable.icon_wolf)
                }
                if (data!!.getString(2).compareTo("weisserwerwolf") == 0) {
                    icon!!.setImageResource(R.drawable.icon_wolf)
                }
                if (data!!.getString(2).compareTo("urwolf") == 0) {
                    icon!!.setImageResource(R.drawable.icon_wolf)
                }
                if (data!!.getInt(0) == weisserWerwolfOpferID) {
                    icon!!.setImageResource(R.drawable.icon_dead)
                }
            }
            10 -> if (data!!.getInt(0) == hexeOpferID) {
                icon!!.setImageResource(R.drawable.icon_dead)
            }
            12 -> if (data!!.getInt(0) == buergerOpfer) {
                icon!!.setImageResource(R.drawable.icon_dead)
            }
            20 -> if (data!!.getInt(0) == jaegerOpfer) {
                icon!!.setImageResource(R.drawable.icon_dead)
            }
            21 -> {
                if (data!!.getInt(0) == ritterOpfer) {
                    icon!!.setImageResource(R.drawable.icon_dead)
                }
                if (data!!.getInt(0) == werwolfDurchUrwolfID && werwolfDurchUrwolfID != -1) {
                    icon!!.setImageResource(R.drawable.icon_wolf)
                }
                if (data!!.getString(2).compareTo("werwolf") == 0) {
                    icon!!.setImageResource(R.drawable.icon_wolf)
                }
                if (data!!.getString(2).compareTo("weisserwerwolf") == 0) {
                    icon!!.setImageResource(R.drawable.icon_wolf)
                }
                if (data!!.getString(2).compareTo("urwolf") == 0) {
                    icon!!.setImageResource(R.drawable.icon_wolf)
                }
            }
        }
        pers!!.text = "" + data!!.getString(1)
        return convertView
    }
}