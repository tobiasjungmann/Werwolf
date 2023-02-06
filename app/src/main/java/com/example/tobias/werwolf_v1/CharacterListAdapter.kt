package com.example.tobias.werwolf_v1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tobias.werwolf_v1.databinding.ItemCharacterSelectionBinding


class CharacterListAdapter(
    private val characters: List<Character>,
    private val preGameViewModel: PreGameViewModel
) :
    RecyclerView.Adapter<CharacterListAdapter.CharacterHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterHolder {
        val binding =
            ItemCharacterSelectionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return CharacterHolder(binding, preGameViewModel)
    }

    override fun onBindViewHolder(holder: CharacterHolder, position: Int) {
        val currentItem = characters[position]

    }

    override fun getItemCount(): Int {
        return characters.size
    }


    class CharacterHolder(
        private val binding: ItemCharacterSelectionBinding,
        private val preGameViewModel: PreGameViewModel
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.imageAddCharacter.setOnClickListener {
                binding.textviewAmountCharacter.text =
                    preGameViewModel.addToCharacterByPosition(bindingAdapterPosition).toString()
            }

            binding.imageReduceCharacter.setOnClickListener {
                binding.textviewAmountCharacter.text =
                    preGameViewModel.removeCharacterByPosition(bindingAdapterPosition).toString()
            }

            itemView.setOnLongClickListener(View.OnLongClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    if (binding.textviewInfo.visibility == View.GONE) {
                        binding.textviewInfo.visibility = View.VISIBLE
                    } else {
                        binding.textviewInfo.visibility = View.GONE
                    }
                    return@OnLongClickListener true
                }
                false
            })
        }
    }
}