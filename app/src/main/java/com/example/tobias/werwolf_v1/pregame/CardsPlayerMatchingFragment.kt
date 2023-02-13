package com.example.tobias.werwolf_v1.pregame

import android.content.Intent
import android.database.Cursor
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.tobias.werwolf_v1.DatabaseHelper
import com.example.tobias.werwolf_v1.ListNight
import com.example.tobias.werwolf_v1.R
import com.example.tobias.werwolf_v1.databinding.FragmentCardsPlayermatchingBinding
import com.example.tobias.werwolf_v1.databinding.FragmentCharacterSelectionBinding
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

   /* fun bilderFeldErstellen() {
        AmorId = -1
        WerwolfId = -1
        HexeId = -1
        DiebId = -1
        SeherId = -1
        JungesId = -1
        JaegerId = -1
        BuergerId = -1
        WaechterId = -1
        WeisserWerwolfId = -1
        MaedchenId = -1
        FloetenspielerId = -1
        UrwolfId = -1
        RitterId = -1
        FreundeId = -1
        var position = 0
        if (anzahlWerwolf > 0) {
            WerwolfId = position
            position++
        }
        if (anzahlBuerger > 0) {
            BuergerId = position
            position++
        }
        if (anzahlAmor > 0) {
            AmorId = position
            position++
        }
        if (anzahlHexe > 0) {
            HexeId = position
            position++
        }
        if (anzahlWaechter > 0) {
            WaechterId = position
            position++
        }
        if (anzahlMaedchen > 0) {
            MaedchenId = position
            position++
        }
        if (anzahlSeher > 0) {
            SeherId = position
            position++
        }
        if (anzahlDieb > 0) {
            DiebId = position
            position++
        }
        if (anzahlJaeger > 0) {
            JaegerId = position
            position++
        }
        if (anzahlRitter > 0) {
            RitterId = position
            position++
        }
        if (anzahlFloetenspieler > 0) {
            FloetenspielerId = position
            position++
        }
        if (anzahlFreunde > 0) {
            FreundeId = position
            position++
        }
        if (anzahlJunges > 0) {
            JungesId = position
            position++
        }
        if (anzahlWeisserWerwolf > 0) {
            WeisserWerwolfId = position
            position++
        }
        if (anzahlUrwolf > 0) {
            UrwolfId = position
            position++
        }
    }
*/
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
                charakter.setText(R.string.waechter)
                layoutcharakterRolle.background?.setTint(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.guradian
                    )
                )
           /* } else if (position == WerwolfId) {
                charakter.setText(R.string.werwolf)
                layoutcharakterRolle.setBackgroundColor(resources.getColor(R.color.farbe_werwolf))
            } else if (position == DiebId) {
                charakter.setText(R.string.dieb)
                layoutcharakterRolle.setBackgroundColor(resources.getColor(R.color.farbe_dieb))
            } else if (position == SeherId) {
                charakter.setText(R.string.seher)
                layoutcharakterRolle.setBackgroundColor(resources.getColor(R.color.farbe_seher))
            } else if (position == JaegerId) {
                charakter.setText(R.string.jaeger)
                layoutcharakterRolle.setBackgroundColor(resources.getColor(R.color.farbe_jaeger))
            } else if (position == JungesId) {
                charakter.setText(R.string.junges)
                layoutcharakterRolle.setBackgroundColor(resources.getColor(R.color.farbe_junges))
            } else if (position == BuergerId) {
                charakter.setText(R.string.buerger)
                layoutcharakterRolle.setBackgroundColor(resources.getColor(R.color.farbe_buerger))
            } else if (position == HexeId) {
                charakter.setText(R.string.hexe)
                layoutcharakterRolle.setBackgroundColor(resources.getColor(R.color.farbe_hexe))
            } else if (position == AmorId) {
                charakter.setText(R.string.amor)
                layoutcharakterRolle.setBackgroundColor(resources.getColor(R.color.farbe_amor))
            } else if (position == MaedchenId) {
                charakter.setText(R.string.maedchen)
                layoutcharakterRolle.setBackgroundColor(resources.getColor(R.color.farbe_maedchen))
            } else if (position == WeisserWerwolfId) {
                charakter.setText(R.string.weisser_werwolf)
                layoutcharakterRolle.setBackgroundColor(resources.getColor(R.color.farbe_weisserwolf))
            } else if (position == FloetenspielerId) {
                charakter.setText(R.string.floetenspieler)
                layoutcharakterRolle.setBackgroundColor(resources.getColor(R.color.farbe_floetenspieler))
            } else if (position == FreundeId) {
                charakter.setText(R.string.freunde)
                layoutcharakterRolle.setBackgroundColor(resources.getColor(R.color.farbe_freunde))
            } else if (position == RitterId) {
                charakter.setText(R.string.ritter)
                layoutcharakterRolle.setBackgroundColor(resources.getColor(R.color.farbe_ritter))
            } else if (position == UrwolfId) {
                charakter.setText(R.string.urwolf)
                layoutcharakterRolle.setBackgroundColor(resources.getColor(R.color.farbe_urwolf))
            }*/
            return convertView
        }
    }
}