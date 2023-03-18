package com.example.tobias.werwolf_v1.pregame

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tobias.werwolf_v1.R
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


        getNextPlayer()
        characterCardAdapter = PlayerCardMatchingAdapter(requireContext(),preGameViewModel)
        characterCardAdapter.updateCardsList(preGameViewModel.getCharactersForMatching())       // todo everytime the rest was reloaded
        binding.rollen.layoutManager = LinearLayoutManager(context)
        binding.rollen.adapter = characterCardAdapter


        return binding.root
    }

    private fun getNextPlayer() {
        preGameViewModel.prepareNextPlayerMatching()
        binding.persTxt.text=preGameViewModel.getNextPlayerName()
    }
}

/*
todo nach dem clicklistener -> erster spieler wird fertig gemacht -> über ein livedata construct den nächsten durchlauf triggeren -> next player als livedata?

 playername in mutable live data*/