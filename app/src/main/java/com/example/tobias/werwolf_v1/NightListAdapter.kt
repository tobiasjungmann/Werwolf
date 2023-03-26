package com.example.tobias.werwolf_v1

import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tobias.werwolf_v1.database.models.Player
import com.example.tobias.werwolf_v1.databinding.MylistitemBinding

class NightListAdapter(private val listener: (View, Int, Int) -> Unit) : RecyclerView.Adapter<NightListAdapter.IconHolder>() {
    private var players: List<Player> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconHolder {
        val binding =
            MylistitemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IconHolder(listener, binding)
    }

    override fun onBindViewHolder(holder: IconHolder, position: Int) {
        holder.binding.charakter.text=players[position].name
        // holder.binding.layoutcharakterRolle todo set icon and toggle it off again
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    class IconHolder(
        listener: (View, Int, Int) -> Unit,
        val binding: MylistitemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                listener.invoke(
                    it,
                    bindingAdapterPosition,
                    itemViewType
                )
            }
        }
    }
}