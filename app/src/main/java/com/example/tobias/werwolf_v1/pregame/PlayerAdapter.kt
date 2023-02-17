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

class PlayerAdapter(
    private val plantViewModel: PreGameViewModel//,
    //private val players: List<Player>
) : RecyclerView.Adapter<PlayerAdapter.PlayerHolder>() {
    //   private var players: List<Player>=ArrayList()
    private var players: List<Player> = ArrayList()


    fun setPlayerList(playerlist: List<Player>) {
        this.players = playerlist
        notifyItemRangeInserted(0, playerlist.size)
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

    class PlayerHolder(
        val binding: MylistitemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
// todo remove on swipe
        }
    }
}