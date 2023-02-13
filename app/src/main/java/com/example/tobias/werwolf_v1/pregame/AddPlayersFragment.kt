package com.example.tobias.werwolf_v1.pregame

import android.content.Intent
import android.database.Cursor
import android.graphics.Color
import android.os.Bundle
import android.os.Vibrator
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.tobias.werwolf_v1.CardsToPlayerMatching
import com.example.tobias.werwolf_v1.DatabaseHelper
import com.example.tobias.werwolf_v1.R
import com.example.tobias.werwolf_v1.databinding.FragmentAddPlayersBinding
import maes.tech.intentanim.CustomIntent

class AddPlayersFragment : Fragment(), View.OnClickListener {
    private var nameText: EditText? = null
    private var anzahlPersoenenText: TextView? = null
    private var anzahlPersonen = 0
    private var mDatabaseHelper: DatabaseHelper? = null
    private var weiter: Button? = null
    private var customAdapter: CustomAdapter? = null
    private var data: Cursor? = null

    private var gesamtPer = 0

    // private String karten;
    private var vibrator: Vibrator? = null
    private var vibrationsdauer = 0
    private var lastPersonAdded = false
    private lateinit var binding: FragmentAddPlayersBinding
    private lateinit var preGameViewModel: PreGameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentAddPlayersBinding.inflate(layoutInflater)
        preGameViewModel = ViewModelProvider(requireActivity()).get(PreGameViewModel::class.java)

        //setContentView(R.layout.activity_playermanagement)
        mDatabaseHelper = DatabaseHelper(requireContext())
        data = mDatabaseHelper!!.data
        anzahlPersonen = 0
        // val listePers = findViewById<ListView>(R.id.listePers)
        customAdapter = CustomAdapter()
        binding.listePers.adapter = customAdapter
        binding.listePers.onItemClickListener =
            AdapterView.OnItemClickListener { parent: AdapterView<*>?, view: View?, position: Int, id: Long ->
                vibrator!!.vibrate(vibrationsdauer.toLong())
                data?.moveToPosition(position)
                val name = data?.getString(1)
                val hilfedata = mDatabaseHelper!!.getItemID(name ?: "invalid")
                var itemID = -1
                while (hilfedata.moveToNext()) {
                    itemID = hilfedata.getInt(0)
                }
                if (itemID > -1) {
                    mDatabaseHelper!!.deleteName("" + itemID)
                }
                listeAktualisieren()
                anzahlAktualisieren()
                if (gesamtPer == anzahlPersonen) {
                    lastPersonAdded = true
                    weiter!!.setText(R.string.weiter)
                    nameText!!.isClickable = false
                } else {
                    lastPersonAdded = false
                    weiter!!.text = "Person einfügen"
                    nameText!!.isClickable = true
                }
            }
        vibrationsdauer = 8
        vibrator =
            requireActivity().getSystemService(AppCompatActivity.VIBRATOR_SERVICE) as Vibrator
        binding.weiter.setOnClickListener(this)
        binding.nameText.setTextColor(Color.WHITE)
        //  charakterDatenHolen()
        listeAktualisieren()
        anzahlAktualisieren()
        if (gesamtPer == anzahlPersonen) {
            lastPersonAdded = true
            weiter?.setText(R.string.weiter)
            nameText?.setClickable(false)
        }
    }

    /* private fun charakterDatenHolen() {
         anzahlAmor = intent.extras!!.getInt("anzahlAmor")
         anzahlBuerger = intent.extras!!.getInt("anzahlBuerger")
         anzahlWaechter = intent.extras!!.getInt("anzahlWaechter")
         anzahlDieb = intent.extras!!.getInt("anzahlDieb")
         anzahlHexe = intent.extras!!.getInt("anzahlHexe")
         anzahlJaeger = intent.extras!!.getInt("anzahlJaeger")
         anzahlJunges = intent.extras!!.getInt("anzahlJunges")
         anzahlSeher = intent.extras!!.getInt("anzahlSeher")
         anzahlWerwolf = intent.extras!!.getInt("anzahlWerwolf")
         anzahlWeisserWerwolf = intent.extras!!.getInt("anzahlWeisserWerwolf")
         anzahlRitter = intent.extras!!.getInt("anzahlRitter")
         anzahlFloetenspieler = intent.extras!!.getInt("anzahlFloetenspieler")
         anzahlFreunde = intent.extras!!.getInt("anzahlFreunde")
         anzahlMaedchen = intent.extras!!.getInt("anzahlMaedchen")
         anzahlUrwolf = intent.extras!!.getInt("anzahlUrwolf")
         gesamtPer = intent.extras!!.getInt("gesamtPer")
     }*/

    private fun listeAktualisieren() {
        data = mDatabaseHelper!!.data
        customAdapter!!.notifyDataSetChanged()
    }

    fun anzahlAktualisieren() {
        data!!.moveToFirst()
        var anzahl = 1
        while (data!!.moveToNext()) {
            anzahl++
        }
        anzahlPersonen = anzahl
        anzahlPersoenenText!!.text = "Personen: $anzahl von $gesamtPer"
    }

    override fun onClick(v: View) {
        if (v.id == R.id.weiter) {
            anzahlAktualisieren()
            if (gesamtPer > anzahlPersonen) {
                val name = nameText!!.text.toString()
                if (name.length == 0) {
                    Toast.makeText(requireContext(), "Namen eingegeben", Toast.LENGTH_SHORT)
                        .show()
                    vibrator!!.vibrate(vibrationsdauer.toLong())
                } else {
                    nameText!!.setText("")
                    data = mDatabaseHelper!!.data
                    var nameDoppelt = false
                    while (data?.moveToNext() == true && !nameDoppelt) {
                        val hilfe = data?.getString(1)
                        if (0 == hilfe?.compareTo(name)) {
                            nameDoppelt = true
                        }
                    }
                    if (nameDoppelt) {
                        Toast.makeText(
                            requireContext(),
                            "Dieser Name ist schon vergeben",
                            Toast.LENGTH_LONG
                        ).show()
                        vibrator!!.vibrate(vibrationsdauer.toLong())
                    } else {
                        val insertData = mDatabaseHelper!!.addData(name)
                        if (!insertData) {
                            Toast.makeText(
                                requireContext(),
                                "unbekannter Fehler, Person konnte nicht eingfügt werden.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                    listeAktualisieren()
                    anzahlAktualisieren()
                }
            } else if (gesamtPer < anzahlPersonen) {
                vibrator!!.vibrate(vibrationsdauer.toLong())
                Toast.makeText(
                    requireContext(),
                    "Mehr Charakterkarten als Personen",
                    Toast.LENGTH_SHORT
                ).show()
            }
            if (gesamtPer == anzahlPersonen) {
                if (lastPersonAdded) {
                    val intent: Intent
                    intent = Intent(requireContext(), CardsToPlayerMatching::class.java)
                    /*   intent.putExtra("anzahlAmor", anzahlAmor)
                       intent.putExtra("anzahlBuerger", anzahlBuerger)
                       intent.putExtra("anzahlWaechter", anzahlWaechter)
                       intent.putExtra("anzahlDieb", anzahlDieb)
                       intent.putExtra("anzahlHexe", anzahlHexe)
                       intent.putExtra("anzahlJaeger", anzahlJaeger)
                       intent.putExtra("anzahlJunges", anzahlJunges)
                       intent.putExtra("anzahlSeher", anzahlSeher)
                       intent.putExtra("anzahlWerwolf", anzahlWerwolf)
                       intent.putExtra("anzahlWeisserWerwolf", anzahlWeisserWerwolf)
                       intent.putExtra("anzahlRitter", anzahlRitter)
                       intent.putExtra("anzahlFloetenspieler", anzahlFloetenspieler)
                       intent.putExtra("anzahlFreunde", anzahlFreunde)
                       intent.putExtra("anzahlMaedchen", anzahlMaedchen)
                       intent.putExtra("anzahlUrwolf", anzahlUrwolf)
                       intent.putExtra("gesamtPer", gesamtPer)*/
                    startActivity(intent)
                    CustomIntent.customType(requireContext(), "left-to-right")
                } else {
                    lastPersonAdded = true
                    weiter!!.setText(R.string.weiter)
                    nameText!!.isClickable = false
                }
            }
        }
    }

    internal inner class CustomAdapter : BaseAdapter() {
        override fun getCount(): Int {
            return data!!.count
        }

        override fun getItem(position: Int): Any? {
            return null
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View, parent: ViewGroup): View {
            var convertView = convertView
            convertView = layoutInflater.inflate(R.layout.mylistitemwhitetext, null)
            val pers = convertView.findViewById<TextView>(R.id.textPer)
            data!!.moveToPosition(position)
            pers.text = "" + data!!.getString(1)
            return convertView
        }
    }
}