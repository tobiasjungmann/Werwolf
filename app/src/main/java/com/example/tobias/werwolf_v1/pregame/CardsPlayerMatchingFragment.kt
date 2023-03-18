package com.example.tobias.werwolf_v1.pregame

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tobias.werwolf_v1.R
import com.example.tobias.werwolf_v1.database.models.Player
import com.example.tobias.werwolf_v1.databinding.FragmentCardsPlayermatchingBinding
import maes.tech.intentanim.CustomIntent


class CardsPlayerMatchingFragment : Fragment() {

    private var nameAkt: String? = null

    //private var mDatabaseHelper: DatabaseHelper? = null
    // private var data: Cursor? = null
    private lateinit var characterCardAdapter: PlayerCardMatchingAdapter
    //private var intent: Intent? = null

    private lateinit var binding: FragmentCardsPlayermatchingBinding
    private lateinit var preGameViewModel: PreGameViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCardsPlayermatchingBinding.inflate(layoutInflater)
        preGameViewModel = ViewModelProvider(requireActivity()).get(PreGameViewModel::class.java)

        characterCardAdapter = PlayerCardMatchingAdapter(requireContext(), preGameViewModel)
     //   characterCardAdapter.updateCardsList(preGameViewModel.getCharactersForMatching())
        binding.rollen.layoutManager = LinearLayoutManager(context)
        binding.rollen.adapter = characterCardAdapter


        preGameViewModel.prepareNextPlayerMatching()
        preGameViewModel.currentPlayerIndex.observe(requireActivity()) { index ->
            if (index == 0) {
                // todo change to next actifity
                binding.persTxt.text = "fertig"
            } else {
                characterCardAdapter.updateCardsList(preGameViewModel.getCharactersForMatching())
                binding.persTxt.text = preGameViewModel.getNextPlayerName()
            }
        }


        return binding.root
    }
}

/*
todo nach dem clicklistener -> erster spieler wird fertig gemacht -> über ein livedata construct den nächsten durchlauf triggeren -> next player als livedata?

 playername in mutable live data*/