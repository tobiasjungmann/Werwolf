package com.example.tobias.werwolf_v1.pregame

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.tobias.werwolf_v1.R
import com.example.tobias.werwolf_v1.database.models.Player
import com.example.tobias.werwolf_v1.databinding.MylistitemBinding

class PlayerAdapter() : RecyclerView.Adapter<PlayerAdapter.PlayerHolder>() {

    private var players: ArrayList<Player> = ArrayList()


    fun updatePlayerList(playerlist: ArrayList<Player>) {
        this.players.clear()
        this.players = playerlist
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerHolder {
        val binding =
            MylistitemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlayerHolder(binding)
    }

    override fun onBindViewHolder(holder: PlayerHolder, position: Int) {
        holder.binding.charakter.text = players[position].name
    }

    override fun getItemCount(): Int {
        return players.size
    }

    fun getPlayerAt(bindingAdapterPosition: Int): Player {
        return players.get(bindingAdapterPosition)
    }

    class PlayerHolder(
        val binding: MylistitemBinding
    ) : RecyclerView.ViewHolder(binding.root)
}