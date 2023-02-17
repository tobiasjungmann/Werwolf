package com.example.tobias.werwolf_v1.pregame

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.example.tobias.werwolf_v1.R
import com.example.tobias.werwolf_v1.databinding.FragmentAddPlayersBinding

class AddPlayersFragment : Fragment(), View.OnClickListener {

    private var playerAdapter: PlayerAdapter? = null

    private lateinit var binding: FragmentAddPlayersBinding
    private lateinit var preGameViewModel: PreGameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentAddPlayersBinding.inflate(layoutInflater)
        preGameViewModel = ViewModelProvider(requireActivity()).get(PreGameViewModel::class.java)

        playerAdapter = PlayerAdapter(preGameViewModel)
        binding.listePers.adapter = playerAdapter

        binding.weiter.setOnClickListener(this)

        preGameViewModel.amountPlayers.observe(this) { players ->
            binding.anzahlPersoenenText.text =
                "Personen: " + players + " von " + preGameViewModel.getAmountOfPlayers()
            if (preGameViewModel.differenceCharactersCurrentPlayers() == 0) {
                binding.weiter.setText(R.string.weiter)
                binding.nameText.isClickable = false
            }
        }
    }


    override fun onClick(v: View) {
        val charactersMinusPlayers = preGameViewModel.differenceCharactersCurrentPlayers()
        if (charactersMinusPlayers > 0) {
            val name = binding.nameText.text.toString()
            if (name.isEmpty()) {
                Toast.makeText(requireContext(), "Namen eingegeben", Toast.LENGTH_SHORT)
                    .show()
            } else {
                binding.nameText.setText("")
                if (!preGameViewModel.insertPlayer(binding.nameText.text.toString())) {
                    Toast.makeText(
                        requireContext(),
                        "Dieser Name ist schon vergeben",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        } else if (charactersMinusPlayers < 0) {
            Toast.makeText(
                requireContext(),
                "Mehr Charakterkarten als Personen",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            val nextFrag = CardsPlayerMatchingFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view_pregame, nextFrag, "findThisFragment")
                .addToBackStack(null)
                .commit()

        }
    }
}