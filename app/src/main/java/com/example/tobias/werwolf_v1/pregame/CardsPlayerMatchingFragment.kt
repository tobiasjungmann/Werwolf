package com.example.tobias.werwolf_v1.pregame

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.tobias.werwolf_v1.database.models.DatabaseHelper
import com.example.tobias.werwolf_v1.R
import com.example.tobias.werwolf_v1.databinding.FragmentCardsPlayermatchingBinding
import maes.tech.intentanim.CustomIntent


class CardsPlayerMatchingFragment : Fragment() {

    private var nameAkt: String? = null
    private var mDatabaseHelper: DatabaseHelper? = null
    private var data: Cursor? = null
    private var customAdapter: CustomAdapter? = null
    private var intent: Intent? = null

    private lateinit var binding: FragmentCardsPlayermatchingBinding
    private lateinit var preGameViewModel: PreGameViewModel


        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            // Inflate the layout for this fragment
            binding = FragmentCardsPlayermatchingBinding.inflate(layoutInflater)
            preGameViewModel = ViewModelProvider(requireActivity()).get(PreGameViewModel::class.java)


        mDatabaseHelper = DatabaseHelper(requireContext())      // todo move to viewmodel
        data = mDatabaseHelper!!.data

        namenSchreiben()
    //    bilderFeldErstellen()
       customAdapter = CustomAdapter()
        binding.rollen.adapter = customAdapter
        binding.rollen.onItemClickListener =
            AdapterView.OnItemClickListener { parent: AdapterView<*>?, view: View?, position: Int, id: Long ->
                val charakterHilfe = determineCharacter(position)
                Log.e("D", "Name:    $nameAkt")
                mDatabaseHelper!!.deleteOnlyName(nameAkt!!)
                mDatabaseHelper!!.addCharakter(nameAkt!!, charakterHilfe)
                namenSchreiben()
            }
            return binding.root
    }

    fun namenSchreiben() {
        if (data!!.moveToNext()) {
            nameAkt = data!!.getString(1)
            binding.persTxt.text = getString(R.string.nameWrapper, nameAkt)
        } else {
            startActivity(intent)
            CustomIntent.customType(requireContext(), "left-to-right")
        }
    }

    fun determineCharacter(position: Int): String {
       /* var retValue = ""
        if (position == WaechterId) {
            retValue = "waechter"
            anzahlWaechter--
        } else if (position == WerwolfId) {
            retValue = "werwolf"
            anzahlWerwolf--
        } else if (position == JaegerId) {
            retValue = "jaeger"
            anzahlJaeger--
        } else if (position == JungesId) {
            retValue = "junges"
            anzahlJunges--
        } else if (position == SeherId) {
            retValue = ""
            anzahlSeher--
        } else if (position == AmorId) {
            retValue = "amor"
            anzahlAmor--
        } else if (position == BuergerId) {
            retValue = "buerger"
            anzahlBuerger--
        } else if (position == DiebId) {
            retValue = "dieb"
            anzahlDieb--
        } else if (position == HexeId) {
            retValue = "hexe"
            anzahlHexe--
        } else if (position == WeisserWerwolfId) {
            retValue = "weisserwerwolf"
            anzahlWeisserWerwolf--
        } else if (position == FloetenspielerId) {
            retValue = "floetenspieler"
            anzahlFloetenspieler--
        } else if (position == UrwolfId) {
            retValue = "urwolf"
            anzahlUrwolf--
        } else if (position == RitterId) {
            retValue = "ritter"
            anzahlRitter--
        } else if (position == MaedchenId) {
            retValue = "maedchen"
            anzahlMaedchen--
        } else if (position == FreundeId) {
            retValue = "freunde"
            anzahlFreunde--
        }
        bilderFeldErstellen()*/
        customAdapter!!.notifyDataSetChanged()
        return "werwolf" // todo work with character classes
    }

    internal inner class CustomAdapter : BaseAdapter() {
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
    }
}