package com.example.tobias.werwolf_v1.pregame

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.tobias.werwolf_v1.database.models.CharacterClass
import com.example.tobias.werwolf_v1.database.models.Player
import com.example.tobias.werwolf_v1.databinding.MylistitemBinding

class PlayerCardMatchingAdapter(private val context: Context,
private val preGameViewModel: PreGameViewModel) :
    RecyclerView.Adapter<PlayerCardMatchingAdapter.CardHolder>() {

    private var characters: List<CharacterClass> = ArrayList()


    fun updateCardsList(characterList: List<CharacterClass>) {
        this.characters = characterList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        val binding =
            MylistitemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardHolder(binding,preGameViewModel)
    }

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        holder.binding.charakter.text = characters[position].name
        holder.binding.layoutcharakterRolle.background.setTint(
            ContextCompat.getColor(
                context,
                characters[position].color
            )
        )
    }

    override fun getItemCount(): Int {
        return characters.size
    }

    class CardHolder(
        val binding: MylistitemBinding,
        val preGameViewModel: PreGameViewModel
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.layoutcharakterRolle.setOnClickListener {
                preGameViewModel.clickedCharacter(bindingAdapterPosition)
            }
        }
    }
}