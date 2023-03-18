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


        // mDatabaseHelper = DatabaseHelper(requireContext())      // todo move to viewmodel
        //data = mDatabaseHelper!!.data

        /*
        todo
        1. liste mit allen Spielern laden
        2. Character recyclerview anzegn
        2. Jeden Spieler einzlen durchgehen -> in der db updaten um das mapping zu kennen
         */
      //  getNextPlayer()
        //    bilderFeldErstellen()
        characterCardAdapter = PlayerCardMatchingAdapter(requireContext())
        characterCardAdapter.updateCardsList(preGameViewModel.getCharactersForMatching())
        binding.rollen.layoutManager = LinearLayoutManager(context)
        binding.rollen.adapter = characterCardAdapter

        //todo click listener für die recyckerview elemente
       /* binding.rollen.onItemClickListener =
            AdapterView.OnItemClickListener { parent: AdapterView<*>?, view: View?, position: Int, id: Long ->
                val charakterHilfe = determineCharacter(position)
                Log.e("D", "Name:    $nameAkt")
                mDatabaseHelper!!.deleteOnlyName(nameAkt!!)
                mDatabaseHelper!!.addCharakter(nameAkt!!, charakterHilfe)
                getNextPlayer()
            }*/
        return binding.root
    }

    // todo query, die alle ohne mapping sucht - am anfang dann das mapping zurücksetzten -> päter poetentiell genuaer gestallten
    /*fun getNextPlayer() {
        if (data!!.moveToNext()) {
            nameAkt = data!!.getString(1)
            binding.persTxt.text = getString(R.string.nameWrapper, nameAkt)
        } else {
            startActivity(intent)
            CustomIntent.customType(requireContext(), "left-to-right")
        }
    }*/

    /*fun determineCharacter(position: Int): String {
        customAdapter!!.notifyDataSetChanged()
    }*/
}
    /*internal inner class PlayerCardMatchingAdapter : BaseAdapter() {
        override fun getCount(): Int {
            return preGameViewModel.getNumberOfUnmatchedPlayers()
        }

        override fun getItem(position: Int): Any? {
            return null
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View, parent: ViewGroup): View {
            // todo use dictionary again
            var convertView = convertView
            convertView = layoutInflater.inflate(R.layout.mylistitem, null)
            val charakter = convertView.findViewById<TextView>(R.id.charakter)
            val layoutcharakterRolle =
                convertView.findViewById<LinearLayout>(R.id.layoutcharakterRolle)
          //todo  if (position == WaechterId) {
            charakter.text = charakter.text
                layoutcharakterRolle.background?.setTint(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.witch
                    )
                )

            return convertView
        }
    }*/
