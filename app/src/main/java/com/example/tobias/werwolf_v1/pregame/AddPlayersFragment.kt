package com.example.tobias.werwolf_v1.pregame

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tobias.werwolf_v1.R
import com.example.tobias.werwolf_v1.database.models.Player
import com.example.tobias.werwolf_v1.databinding.FragmentAddPlayersBinding
import com.google.android.material.snackbar.Snackbar

class AddPlayersFragment : Fragment(), View.OnClickListener {

    private var playerAdapter: PlayerAdapter? = null

    private lateinit var binding: FragmentAddPlayersBinding
    private lateinit var preGameViewModel: PreGameViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddPlayersBinding.inflate(layoutInflater)
        preGameViewModel = ViewModelProvider(requireActivity()).get(PreGameViewModel::class.java)

        playerAdapter = PlayerAdapter()
        playerAdapter!!.updatePlayerList(arrayListOf())
        initItemTouchHelper(playerAdapter!!)
        binding.listePers.adapter = playerAdapter
        binding.listePers.layoutManager = LinearLayoutManager(context)

        binding.weiter.setOnClickListener(this)


        preGameViewModel.getPlayerList().observe(requireActivity()) { players ->
            playerAdapter?.updatePlayerList(players as ArrayList<Player>)
            binding.anzahlPersoenenText.text =
                "Personen: " + players.size + " von " + preGameViewModel.getAmountOfPlayers()
            if (players.size == preGameViewModel.getAmountOfPlayers()) {
                prepareNextButton(true, R.string.weiter)
                binding.nameText.isClickable = false
            } else if (players.size > preGameViewModel.getAmountOfPlayers()) {
                prepareNextButton(false, R.string.weiter)
            }else{
                prepareNextButton(true, R.string.person_einf_gen)
            }
        }
        return binding.root
    }

    private fun initItemTouchHelper(adapter: PlayerAdapter) {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val lastDeleted = adapter.getPlayerAt(viewHolder.bindingAdapterPosition)
                preGameViewModel.deletePlayer(lastDeleted)

                val snackbar = Snackbar
                    .make(
                        binding.addplayerlayout,
                        "Player deleted",
                        Snackbar.LENGTH_LONG
                    )
                    .setAction(
                        "Revert"
                    ) {
                        preGameViewModel.insertPlayer(lastDeleted)
                    }
                snackbar.show()
            }
        }).attachToRecyclerView(binding.listePers)
    }

    /*
    Nächster schritt:
    todo remove players
    update recycler adapter successfully
     */
    private fun prepareNextButton(clickable: Boolean, text: Int) {
        binding.weiter.isClickable = clickable
        binding.weiter.setText(text)
        var buttoncolor = R.color.blue_unclickable
        if (clickable) {
            buttoncolor = R.color.blue
        }
        binding.weiter.background.setTint(
            ContextCompat.getColor(
                requireActivity(),
                buttoncolor
            )
        )
    }


    override fun onClick(v: View) {
        val charactersMinusPlayers = preGameViewModel.amountUnmatchedPlayers()
        if (charactersMinusPlayers > 0) {
            val name = binding.nameText.text.toString()
            if (name.isEmpty()) {
                Toast.makeText(requireContext(), "Namen eingegeben", Toast.LENGTH_SHORT)
                    .show()
            } else {
                binding.nameText.setText("")
                if (!preGameViewModel.insertPlayer(name)) {
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