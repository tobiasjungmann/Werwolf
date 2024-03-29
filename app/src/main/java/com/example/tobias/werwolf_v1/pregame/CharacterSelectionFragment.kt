package com.example.tobias.werwolf_v1.pregame

import android.content.Intent
import android.os.Bundle
import android.os.Vibrator
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tobias.werwolf_v1.R
import com.example.tobias.werwolf_v1.databinding.FragmentCharacterSelectionBinding
import com.example.tobias.werwolf_v1.multipleDevices.HostConnectWithPlayers
import maes.tech.intentanim.CustomIntent

class CharacterSelectionFragment : Fragment(), View.OnClickListener {
    private var einGeraet = false
    private var vibrator: Vibrator? = null
    private var vibrationsdauer = 8

    private lateinit var binding: FragmentCharacterSelectionBinding
    private lateinit var preGameViewModel: PreGameViewModel

    private fun initHeader() {
        einGeraet = requireActivity().intent.extras!!.getBoolean("EinGeraet", true)
        binding.textModus.setText(R.string.spielleiter)
        var color = R.color.orange
        if (einGeraet) {
            color = R.color.green
        }
        binding.layoutModus.background.setTint(
            ContextCompat.getColor(
                requireActivity(),
                color
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCharacterSelectionBinding.inflate(layoutInflater)
        initHeader()
        preGameViewModel = ViewModelProvider(requireActivity())[PreGameViewModel::class.java]
        vibrator =
            requireActivity().getSystemService(AppCompatActivity.VIBRATOR_SERVICE) as Vibrator
        binding.spielStarten.setOnClickListener(this)
        val adapter = CharacterListAdapter(preGameViewModel.generateCharacters(), preGameViewModel)
        binding.recyclerviewCharacterSelection.adapter = adapter
        binding.recyclerviewCharacterSelection.layoutManager = LinearLayoutManager(requireContext())

        preGameViewModel.amountCharacters.observe(requireActivity()) { res ->
            binding.gesamtTxt.text = "Gesamt: "+res.toString()
        }
        return binding.root
    }

    override fun onClick(v: View) {
        if (v.id == R.id.spiel_starten) {
            next()
        }
    }

    private fun next() {
        if (preGameViewModel.invalidConfiguration()) {
            Toast.makeText(requireContext(), "ungültige Eingabe", Toast.LENGTH_LONG).show()
            vibrator!!.vibrate(vibrationsdauer.toLong())
        } else {
            // todo add as fragment -> all three stages with the same viewmodel
            if (einGeraet) {
                val nextFrag = AddPlayersFragment()
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_view_pregame, nextFrag, "findThisFragment")
                    .addToBackStack(null)
                    .commit()
                //   Intent(requireContext(), PlayerManagement::class.java)
            } else {
                val intent: Intent
                intent = Intent(requireContext(), HostConnectWithPlayers::class.java)
                intent.putExtra("EinGeraet", einGeraet)
                startActivity(intent)
                CustomIntent.customType(requireContext(), "left-to-right")
            }
        }
    }
}