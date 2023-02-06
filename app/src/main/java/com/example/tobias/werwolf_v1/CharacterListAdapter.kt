package com.example.tobias.werwolf_v1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.tobias.werwolf_v1.databinding.ItemCharacterSelectionBinding


class CharacterListAdapter(private var characters: List<Character>) :
    RecyclerView.Adapter<CharacterListAdapter.CharacterHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterHolder {
        val binding =
            ItemCharacterSelectionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return CharacterHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterHolder, position: Int) {
        val currentItem = characters[position]


        //  observeThumbnail(holder, characters[position].)
    }

    /*   private fun observeThumbnail(holder: PlantHolder, elementId: Int) {
           plantViewModel.getThumbnailsForList().observe(
               viewLifecycleOwner
           ) { pathList: List<PathElement> ->
               val firstThumbnailIndex = pathList.indexOfFirst { it.parentEntry == elementId }

               if (firstThumbnailIndex >= 0) {
                   holder.binding.listThumbnailImageView.visibility = View.VISIBLE
                   holder.binding.listThumbnailImageView.setImageBitmap(
                       pathList[firstThumbnailIndex].loadThumbnail(
                           thumbnailSize
                       )
                   )
                   val alpha = 1.toFloat()
                   holder.binding.listThumbnailImageView.alpha = alpha

               } else {
                   holder.binding.listThumbnailImageView.visibility = View.GONE
               }
           }
       }*/

    override fun getItemCount(): Int {
        return characters.size
    }


    class CharacterHolder(
        val binding: ItemCharacterSelectionBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                binding.nummerWerwolf.text = "5"
            }

            itemView.setOnLongClickListener(View.OnLongClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    if (binding.infoTextWerwolf.visibility == View.GONE) {
                        binding.infoTextWerwolf.visibility = View.VISIBLE
                    } else {
                        binding.infoTextWerwolf.visibility = View.GONE
                    }
                    return@OnLongClickListener true
                }
                false
            })
        }
    }
}