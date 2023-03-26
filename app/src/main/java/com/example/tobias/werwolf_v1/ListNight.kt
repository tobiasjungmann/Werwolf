package com.example.tobias.werwolf_v1

import android.app.AlertDialog
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.tobias.werwolf_v1.database.models.CharacterClass
import com.example.tobias.werwolf_v1.database.models.DatabaseHelper
import com.example.tobias.werwolf_v1.databinding.ActivityNightlistBinding

class ListNight : AppCompatActivity(), View.OnClickListener, NightListContract.View {

    private lateinit var nightListAdapter: NightListAdapter
    private lateinit var binding: ActivityNightlistBinding
    private lateinit var nightListViewModel: NightListViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNightlistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        nightListViewModel =
            ViewModelProvider(this)[NightListViewModel::class.java]
        nightListViewModel.attachView(this)

        binding.weiterNacht.setOnClickListener(this@ListNight)
        binding.description.setOnClickListener(this@ListNight)
        binding.personen.visibility = View.INVISIBLE

        //Witch
        binding.opferRettenHexe.setOnClickListener(this)
        binding.personToetenHexe.setOnClickListener(this)
        binding.textViewOpferRetten.setOnClickListener(this)
        binding.textViewPersonToeten.setOnClickListener(this)


        //Urwolf
        binding.textViewUrwolfVeto.setOnClickListener(this)



        nightListAdapter = NightListAdapter(DatabaseHelper(this).data)
        binding.personen.adapter = nightListAdapter
        binding.personen.onItemClickListener = OnItemClickListener { parent, view, position, id ->
            nightListViewmodel.handleClickAtPosition(position)
            setStatusNextButton(true)
            nightListAdapter.notifyDataSetChanged()
        }
    }
    override fun setPlayerListVisibility(visibility: Int){
        binding.personen.visibility = visibility
    }
   override fun activateWitchDialog(trankLebenEinsetzbar: Boolean, trankTodEinsetzbar: Boolean){


       //todo, wenn nur eine Option übrig ist eien entsprechenden Rand einfügen
       if (trankLebenEinsetzbar || trankTodEinsetzbar) {
           binding.layoutHexeNacht.visibility = View.VISIBLE
           if (!(trankLebenEinsetzbar && trankTodEinsetzbar)) {
               if (trankLebenEinsetzbar) //leben anzaeigen, sonst nur tod
               {
                   binding.layoutRettenHexe.visibility = View.VISIBLE
                   binding.toetenLayoutHexe.visibility = View.GONE
               } else {
                   val params = LinearLayout.LayoutParams(
                       LinearLayout.LayoutParams.MATCH_PARENT,
                       LinearLayout.LayoutParams.WRAP_CONTENT
                   )
                   params.setMargins(16, 16, 16, 16)
                   binding.toetenLayoutHexe.layoutParams = params
                   binding.layoutRettenHexe.visibility = View.GONE
                   binding.toetenLayoutHexe.visibility = View.VISIBLE
               }
           }
       }
    }

    override fun onBackPressed() {
        val builder: AlertDialog.Builder
        builder =
            AlertDialog.Builder(this@ListNight, android.R.style.Theme_Material_Dialog_Alert)
        builder.setTitle("Warnung")
            .setMessage("Möchtest du die App wirklich verlassen? Der gesamte Spielstand geht verloren.")
            .setNegativeButton("Ja, App verlassen") { dialog, which ->
                val intent = Intent(Intent.ACTION_MAIN)
                intent.addCategory(Intent.CATEGORY_HOME)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                finish()
                System.exit(0)
            }
            .setPositiveButton("weiterspielen") { dialog, which -> }
            .show()
    }


   override fun updateUIForCharacter(currentCharacter: CharacterClass, b: Boolean, listVisible: Boolean) {
        setStatusNextButton(b)
        changeUIToNewCharacter(currentCharacter)
        if (listVisible) {
            nightListAdapter.notifyDataSetChanged()
            binding.personen.visibility = View.VISIBLE
        } else {
            binding.personen.visibility = View.GONE
        }
    }

    override fun setDescription(text: String, append: Boolean){
        if (append){
            val s = binding.description.text.toString()
            binding.description.text=s+text
        }else{
            binding.description.text=text
        }
    }

    fun setStatusNextButton(status: Boolean) {
        binding.weiterNacht.isClickable = status
        var help = R.color.blue_unclickable
        if (status) {
            help = R.color.blue
        }
        binding.weiterNacht.background.setTint(
            ContextCompat.getColor(
                this,
                help
            )
        )
    }


    private fun changeUIToNewCharacter(characterClass: CharacterClass) {
        binding.textSpielstand.setText(characterClass.name)
        binding.layoutSpielstand.background?.setTint(
            ContextCompat.getColor(
                this,
                characterClass.color
            )
        )
        binding.description.text =
            nightListViewModel.getDescToCharacter(characterClass, applicationContext)
    }


    override fun siegbildschirmOeffnen(character: String) {
        val intent = Intent(this, VictoryActivity::class.java)
        intent.putExtra("sieger", character)
        startActivity(intent)
    }


/*    private fun ritterDialogVorbereiten() {
        charakterPosition = 21
        changeUIToNewCharacter(nightListViewModel.generateCharacters()[0]) // todo change to knight

        setStatusNextButton(false)
        nightListAdapter.notifyDataSetChanged()
        binding.personen.visibility = View.VISIBLE
    }*/




    override fun onClick(v: View) {
        when (v.id) {
            R.id.personToetenHexe, R.id.textViewPersonToeten -> {
                val executeWitchKill = nightListViewModel.witchKill()
                binding.personToetenHexe.isChecked = executeWitchKill

                if (executeWitchKill) {
                    binding.personen.visibility = View.VISIBLE
                    setStatusNextButton(false)
                } else {
                    binding.personen.visibility = View.INVISIBLE

                    setStatusNextButton(true)
                }
                nightListAdapter.notifyDataSetChanged()
            }
            R.id.opferRettenHexe, R.id.textViewOpferRetten -> {
                binding.opferRettenHexe.isChecked = nightListViewModel.witchSaveVictim()
            }
            R.id.weiterNacht -> nightListViewModel.nextButtonClicked()
            R.id.textViewUrwolfVeto, R.id.checkboxUrwolfVeto -> binding.checkboxUrwolfVeto.isChecked =
                nightListViewModel.urwolfClicked()
            R.id.description ->
                binding.description.text =
                    nightListViewModel.toggleDescriptionLength(applicationContext)

            else -> {}
        }
    }
}