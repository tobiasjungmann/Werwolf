package com.example.tobias.werwolf_v1;

import static com.example.tobias.werwolf_v1.R.id.minusAmor;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tobias.werwolf_v1.multipleDevices.HostConnectWithPlayers;

import maes.tech.intentanim.CustomIntent;

public class CharacterSelection extends AppCompatActivity implements View.OnClickListener {

    private TextView nummerAmor;
    private TextView nummerHexe;
    private TextView nummerWaechter;
    private TextView nummerDieb;
    private TextView nummerSeher;
    private TextView nummerBuerger;
    private TextView nummerWerwolf;
    private TextView nummerJunges;
    private TextView nummerJaeger;
    private TextView nummerWeisserWerwolf;
    private TextView nummerMaedchen;
    private TextView nummerFloetenspieler;
    private TextView nummerUrwolf;
    private TextView nummerRitter;
    private TextView nummerFreunde;

    private TextView infoTextAmor;
    private TextView infoTextHexe;
    private TextView infoTextWaechter;
    private TextView infoTextDieb;
    private TextView infoTextSeher;
    private TextView infoTextBuerger;
    private TextView infoTextWerwolf;
    private TextView infoTextJunges;
    private TextView infoTextJaeger;
    private TextView infoTextWeisserWerwolf;
    private TextView infoTextMaedchen;
    private TextView infoTextFloetenspieler;
    private TextView infoTextUrwolf;
    private TextView infoTextRitter;
    private TextView infoTextFreunde;

    private int anzahlAmor;
    private int anzahlWerwolf;
    private int anzahlHexe;
    private int anzahlDieb;
    private int anzahlSeher;
    private int anzahlJunges;
    private int anzahlJaeger;
    private int anzahlBuerger;
    private int anzahlWaechter;
    private int anzahlWeisserWerwolf;
    private int anzahlMaedchen;
    private int anzahlFloetenspieler;
    private int anzahlUrwolf;
    private int anzahlRitter;
    private int anzahlFreunde;
    private int gesamtPer;

    private TextView gesamtTxt;
    private boolean einGeraet;
    private Vibrator vibrator;
    private int vibrationsdauer;


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_charakterselection);

        einGeraet = getIntent().getExtras().getBoolean("EinGeraet", true);
        anzahlAmor = 0;
        anzahlBuerger = 0;
        anzahlWaechter = 0;
        anzahlDieb = 0;
        anzahlHexe = 0;
        anzahlJaeger = 0;
        anzahlJunges = 0;
        anzahlSeher = 0;
        anzahlWerwolf = 0;
        anzahlWeisserWerwolf = 0;
        anzahlMaedchen = 0;
        anzahlFloetenspieler = 0;
        anzahlUrwolf = 0;
        anzahlRitter = 0;
        anzahlFreunde = 0;


        TextView textModus = findViewById(R.id.textModus);
        LinearLayout layoutModus = findViewById(R.id.layoutModus);
        if (einGeraet) {
            layoutModus.setBackgroundResource(R.drawable.leiste_hellgruen);
        } else {
            layoutModus.setBackgroundResource(R.drawable.leiste_orange);

        }
        textModus.setText(R.string.spielleiter);


        vibrationsdauer = 8;
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);


        gesamtPer = 0;
        gesamtTxt = findViewById(R.id.gesamtTxt);
        gesamtTxt.setTextColor(Color.WHITE);

        Button spiel_starten = findViewById(R.id.spiel_starten);
        spiel_starten.setOnClickListener(CharacterSelection.this);

        TextView iconAmor = findViewById(R.id.iconAmor);
        iconAmor.setOnClickListener(CharacterSelection.this);
        TextView iconHexe = findViewById(R.id.iconHexe);
        iconHexe.setOnClickListener(CharacterSelection.this);
        TextView iconWaechter = findViewById(R.id.iconWaechter);
        iconWaechter.setOnClickListener(CharacterSelection.this);
        TextView iconDieb = findViewById(R.id.iconDieb);
        iconDieb.setOnClickListener(CharacterSelection.this);
        TextView iconSeher = findViewById(R.id.iconSeher);
        iconSeher.setOnClickListener(CharacterSelection.this);
        TextView iconBuerger = findViewById(R.id.iconBuerger);
        iconBuerger.setOnClickListener(CharacterSelection.this);
        TextView iconWerwolf = findViewById(R.id.iconWerwolf);
        iconWerwolf.setOnClickListener(CharacterSelection.this);
        TextView iconJunges = findViewById(R.id.iconJunges);
        iconJunges.setOnClickListener(CharacterSelection.this);
        TextView iconJaeger = findViewById(R.id.iconJaeger);
        iconJaeger.setOnClickListener(CharacterSelection.this);
        TextView iconWeisserWerwolf = findViewById(R.id.iconWeisserWerwolf);
        iconWeisserWerwolf.setOnClickListener(CharacterSelection.this);
        TextView iconMaedchen = findViewById(R.id.iconMaedchen);
        iconMaedchen.setOnClickListener(CharacterSelection.this);
        TextView iconFloetenspieler = findViewById(R.id.iconFloetenspieler);
        iconFloetenspieler.setOnClickListener(CharacterSelection.this);
        TextView iconUrwolf = findViewById(R.id.iconUrwolf);
        iconUrwolf.setOnClickListener(CharacterSelection.this);
        TextView iconRitter = findViewById(R.id.iconRitter);
        iconRitter.setOnClickListener(CharacterSelection.this);
        TextView iconFreunde = findViewById(R.id.iconFreunde);
        iconFreunde.setOnClickListener(CharacterSelection.this);

        ImageView minusAmor = findViewById(R.id.minusAmor);
        minusAmor.setOnClickListener(CharacterSelection.this);
        ImageView plusAmor = findViewById(R.id.plusAmor);
        plusAmor.setOnClickListener(CharacterSelection.this);
        nummerAmor = findViewById(R.id.nummerAmor);
        nummerAmor.setTextColor(Color.WHITE);

        ImageView minusHexe = findViewById(R.id.minusHexe);
        minusHexe.setOnClickListener(CharacterSelection.this);
        ImageView plusHexe = findViewById(R.id.plusHexe);
        plusHexe.setOnClickListener(CharacterSelection.this);
        nummerHexe = findViewById(R.id.nummerHexe);
        nummerHexe.setTextColor(Color.WHITE);

        ImageView minusWaechter = findViewById(R.id.minusWaechter);
        minusWaechter.setOnClickListener(CharacterSelection.this);
        ImageView plusWaechter = findViewById(R.id.plusWaechter);
        plusWaechter.setOnClickListener(CharacterSelection.this);
        nummerWaechter = findViewById(R.id.nummerWaechter);
        nummerWaechter.setTextColor(Color.WHITE);

        ImageView minusDieb = findViewById(R.id.minusDieb);
        minusDieb.setOnClickListener(CharacterSelection.this);
        ImageView plusDieb = findViewById(R.id.plusDieb);
        plusDieb.setOnClickListener(CharacterSelection.this);
        nummerDieb = findViewById(R.id.nummerDieb);
        nummerDieb.setTextColor(Color.WHITE);

        ImageView minusSeher = findViewById(R.id.minusSeher);
        minusSeher.setOnClickListener(CharacterSelection.this);
        ImageView plusSeher = findViewById(R.id.plusSeher);
        plusSeher.setOnClickListener(CharacterSelection.this);
        nummerSeher = findViewById(R.id.nummerSeher);
        nummerSeher.setTextColor(Color.WHITE);

        ImageView minusBuerger = findViewById(R.id.minusBuerger);
        minusBuerger.setOnClickListener(CharacterSelection.this);
        ImageView plusBuerger = findViewById(R.id.plusBuerger);
        plusBuerger.setOnClickListener(CharacterSelection.this);
        nummerBuerger = findViewById(R.id.nummerBuerger);
        nummerBuerger.setTextColor(Color.WHITE);

        ImageView minusWerwolf = findViewById(R.id.minusWerwolf);
        minusWerwolf.setOnClickListener(CharacterSelection.this);
        ImageView plusWerwolf = findViewById(R.id.plusWerwolf);
        plusWerwolf.setOnClickListener(CharacterSelection.this);
        nummerWerwolf = findViewById(R.id.nummerWerwolf);
        nummerWerwolf.setTextColor(Color.WHITE);

        ImageView minusJunges = findViewById(R.id.minusJunges);
        minusJunges.setOnClickListener(CharacterSelection.this);
        ImageView plusJunges = findViewById(R.id.plusJunges);
        plusJunges.setOnClickListener(CharacterSelection.this);
        nummerJunges = findViewById(R.id.nummerJunges);
        nummerJunges.setTextColor(Color.WHITE);

        ImageView minusJaeger = findViewById(R.id.minusJaeger);
        minusJaeger.setOnClickListener(CharacterSelection.this);
        ImageView plusJaeger = findViewById(R.id.plusJaeger);
        plusJaeger.setOnClickListener(CharacterSelection.this);
        nummerJaeger = findViewById(R.id.nummerJaeger);
        nummerJaeger.setTextColor(Color.WHITE);

        ImageView minusMaedchen = findViewById(R.id.minusMaedchen);
        minusMaedchen.setOnClickListener(CharacterSelection.this);
        ImageView plusMaedchen = findViewById(R.id.plusMaedchen);
        plusMaedchen.setOnClickListener(CharacterSelection.this);
        nummerMaedchen = findViewById(R.id.nummerMaedchen);
        nummerMaedchen.setTextColor(Color.WHITE);

        ImageView minusRitter = findViewById(R.id.minusRitter);
        minusRitter.setOnClickListener(CharacterSelection.this);
        ImageView plusRitter = findViewById(R.id.plusRitter);
        plusRitter.setOnClickListener(CharacterSelection.this);
        nummerRitter = findViewById(R.id.nummerRitter);
        nummerRitter.setTextColor(Color.WHITE);

        ImageView minusUrwolf = findViewById(R.id.minusUrwolf);
        minusUrwolf.setOnClickListener(CharacterSelection.this);
        ImageView plusUrwolf = findViewById(R.id.plusUrwolf);
        plusUrwolf.setOnClickListener(CharacterSelection.this);
        nummerUrwolf = findViewById(R.id.nummerUrwolf);
        nummerUrwolf.setTextColor(Color.WHITE);

        ImageView minusWeisserWerwolf = findViewById(R.id.minusWeisserWerwolf);
        minusWeisserWerwolf.setOnClickListener(CharacterSelection.this);
        ImageView plusWeisserWerwolf = findViewById(R.id.plusWeisserWerwolf);
        plusWeisserWerwolf.setOnClickListener(CharacterSelection.this);
        nummerWeisserWerwolf = findViewById(R.id.nummerWeisserWerwolf);
        nummerWeisserWerwolf.setTextColor(Color.WHITE);

        ImageView minusFloetenspieler = findViewById(R.id.minusFloetenspieler);
        minusFloetenspieler.setOnClickListener(CharacterSelection.this);
        ImageView plusFloetenspieler = findViewById(R.id.plusFloetenspieler);
        plusFloetenspieler.setOnClickListener(CharacterSelection.this);
        nummerFloetenspieler = findViewById(R.id.nummerFloetenspieler);
        nummerFloetenspieler.setTextColor(Color.WHITE);

        ImageView minusFreunde = findViewById(R.id.minusFreunde);
        minusFreunde.setOnClickListener(CharacterSelection.this);
        ImageView plusFreunde = findViewById(R.id.plusFreunde);
        plusFreunde.setOnClickListener(CharacterSelection.this);
        nummerFreunde = findViewById(R.id.nummerFreunde);
        nummerFreunde.setTextColor(Color.WHITE);

        infoTextAmor = findViewById(R.id.infoTextAmor);
        infoTextHexe = findViewById(R.id.infoTextHexe);
        infoTextWaechter = findViewById(R.id.infoTextWaechter);
        infoTextDieb = findViewById(R.id.infoTextDieb);
        infoTextSeher = findViewById(R.id.infoTextSeher);
        infoTextBuerger = findViewById(R.id.infoTextBuerger);
        infoTextWerwolf = findViewById(R.id.infoTextWerwolf);
        infoTextJunges = findViewById(R.id.infoTextJunges);
        infoTextJaeger = findViewById(R.id.infoTextJaeger);
        infoTextWeisserWerwolf = findViewById(R.id.infoTextWeisserWerwolf);
        infoTextMaedchen = findViewById(R.id.infoTextMaedchen);
        infoTextFloetenspieler = findViewById(R.id.infoTextFloetenspieler);
        infoTextUrwolf = findViewById(R.id.infoTextUrwolf);
        infoTextRitter = findViewById(R.id.infoTextRitter);
        infoTextFreunde = findViewById(R.id.infoTextFreunde);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == minusAmor) {
            anzahlAmor--;
            if (anzahlAmor == -1) {
                anzahlAmor = 0;
                vibrator.vibrate(vibrationsdauer);
            }
            nummerAmor.setText(getString(R.string.numberWrapper, anzahlAmor));
        } else if (v.getId() == R.id.plusAmor) {
            anzahlAmor++;
            nummerAmor.setText(getString(R.string.numberWrapper, anzahlAmor));
        } else if (v.getId() == R.id.minusHexe) {
            anzahlHexe--;
            if (anzahlHexe == -1) {
                anzahlHexe = 0;
                vibrator.vibrate(vibrationsdauer);
            }
            nummerHexe.setText(getString(R.string.numberWrapper, anzahlHexe));
        } else if (v.getId() == R.id.plusHexe) {
            anzahlHexe++;
            if (anzahlHexe == 2) {
                anzahlHexe = 1;
                vibrator.vibrate(vibrationsdauer);
            }

            nummerHexe.setText(getString(R.string.numberWrapper, anzahlHexe));
        } else if (v.getId() == R.id.minusWaechter) {
            anzahlWaechter--;
            if (anzahlWaechter == -1) {
                anzahlWaechter = 0;
                vibrator.vibrate(vibrationsdauer);
            }
            nummerWaechter.setText(getString(R.string.numberWrapper, anzahlWaechter));
        } else if (v.getId() == R.id.plusWaechter) {
            anzahlWaechter++;
            if (anzahlWaechter == 2) {
                anzahlWaechter = 1;
                vibrator.vibrate(vibrationsdauer);
            }
            nummerWaechter.setText(getString(R.string.numberWrapper, anzahlWaechter));
        } else if (v.getId() == R.id.minusSeher) {
            anzahlSeher--;
            if (anzahlSeher == -1) {
                anzahlSeher = 0;
                vibrator.vibrate(vibrationsdauer);
            }
            nummerSeher.setText(getString(R.string.numberWrapper, anzahlSeher));
        } else if (v.getId() == R.id.plusSeher) {
            anzahlSeher++;
            nummerSeher.setText(getString(R.string.numberWrapper, anzahlSeher));
        } else if (v.getId() == R.id.minusDieb) {
            anzahlDieb--;
            if (anzahlDieb == -1) {
                anzahlDieb = 0;
                vibrator.vibrate(vibrationsdauer);
            }
            nummerDieb.setText(getString(R.string.numberWrapper, anzahlDieb));
        } else if (v.getId() == R.id.plusDieb) {
            anzahlDieb++;
            if (anzahlDieb == 2) {
                anzahlDieb = 1;
                vibrator.vibrate(vibrationsdauer);
            }
            nummerDieb.setText(getString(R.string.numberWrapper, anzahlDieb));
        } else if (v.getId() == R.id.minusBuerger) {
            anzahlBuerger--;
            if (anzahlBuerger == -1) {
                anzahlBuerger = 0;
                vibrator.vibrate(vibrationsdauer);
            }
            nummerBuerger.setText(getString(R.string.numberWrapper, anzahlBuerger));
        } else if (v.getId() == R.id.plusBuerger) {
            anzahlBuerger++;
            nummerBuerger.setText(getString(R.string.numberWrapper, anzahlBuerger));
        } else if (v.getId() == R.id.minusWerwolf) {
            anzahlWerwolf--;
            if (anzahlWerwolf == -1) {
                anzahlWerwolf = 0;
                vibrator.vibrate(vibrationsdauer);
            }
            nummerWerwolf.setText(getString(R.string.numberWrapper, anzahlWerwolf));
        } else if (v.getId() == R.id.plusWerwolf) {
            anzahlWerwolf++;
            nummerWerwolf.setText(getString(R.string.numberWrapper, anzahlWerwolf));
        } else if (v.getId() == R.id.minusJunges) {
            anzahlJunges--;
            if (anzahlJunges == -1) {
                anzahlJunges = 0;
                vibrator.vibrate(vibrationsdauer);
            }
            nummerJunges.setText(getString(R.string.numberWrapper, anzahlJunges));
        } else if (v.getId() == R.id.plusJunges) {
            anzahlJunges++;
            if (anzahlJunges == 2) {
                anzahlJunges = 1;
                vibrator.vibrate(vibrationsdauer);
            }
            nummerJunges.setText(getString(R.string.numberWrapper, anzahlJunges));
        } else if (v.getId() == R.id.minusMaedchen) {
            anzahlMaedchen--;
            if (anzahlMaedchen == -1) {
                anzahlMaedchen = 0;
                vibrator.vibrate(vibrationsdauer);
            }
            nummerMaedchen.setText(getString(R.string.numberWrapper, anzahlMaedchen));
        } else if (v.getId() == R.id.plusMaedchen) {
            anzahlMaedchen++;
            nummerMaedchen.setText(getString(R.string.numberWrapper, anzahlMaedchen));
        } else if (v.getId() == R.id.minusJaeger) {
            anzahlJaeger--;
            if (anzahlJaeger == -1) {
                anzahlJaeger = 0;
                vibrator.vibrate(vibrationsdauer);
            }
            nummerJaeger.setText(getString(R.string.numberWrapper, anzahlJaeger));
        } else if (v.getId() == R.id.plusJaeger) {
            anzahlJaeger++;

            if (anzahlJaeger == 2) {
                anzahlJaeger = 1;
                vibrator.vibrate(vibrationsdauer);
            }
            nummerJaeger.setText(getString(R.string.numberWrapper, anzahlJaeger));
        } else if (v.getId() == R.id.minusFreunde) {
            anzahlFreunde--;
            if (anzahlFreunde == -1 || anzahlFreunde == 1) {
                anzahlFreunde = 0;
                vibrator.vibrate(vibrationsdauer);
            }
            nummerFreunde.setText(getString(R.string.numberWrapper, anzahlFreunde));
        } else if (v.getId() == R.id.plusFreunde) {
            anzahlFreunde++;
            if (anzahlFreunde == 1) {
                anzahlFreunde = 2;
            }
            nummerFreunde.setText(getString(R.string.numberWrapper, anzahlFreunde));
        } else if (v.getId() == R.id.minusFloetenspieler) {
            anzahlFloetenspieler--;
            if (anzahlFloetenspieler == -1) {
                anzahlFloetenspieler = 0;
                vibrator.vibrate(vibrationsdauer);
            }
            nummerFloetenspieler.setText(getString(R.string.numberWrapper, anzahlFloetenspieler));
        } else if (v.getId() == R.id.plusFloetenspieler) {
            anzahlFloetenspieler++;
            if (anzahlFloetenspieler == 2) {
                anzahlFloetenspieler = 1;
                vibrator.vibrate(vibrationsdauer);
            }
            nummerFloetenspieler.setText(getString(R.string.numberWrapper, anzahlFloetenspieler));
        } else if (v.getId() == R.id.minusRitter) {
            anzahlRitter--;
            if (anzahlRitter == -1) {
                anzahlRitter = 0;
                vibrator.vibrate(vibrationsdauer);
            }
            nummerRitter.setText(getString(R.string.numberWrapper, anzahlRitter));
        } else if (v.getId() == R.id.plusRitter) {
            anzahlRitter++;
            if (anzahlRitter == 2) {
                anzahlRitter = 1;
                vibrator.vibrate(vibrationsdauer);
            }
            nummerRitter.setText(getString(R.string.numberWrapper, anzahlRitter));
        } else if (v.getId() == R.id.minusUrwolf) {
            anzahlUrwolf--;
            if (anzahlUrwolf == -1) {
                anzahlUrwolf = 0;
                vibrator.vibrate(vibrationsdauer);
            }
            nummerUrwolf.setText(getString(R.string.numberWrapper, anzahlUrwolf));
        } else if (v.getId() == R.id.plusUrwolf) {
            anzahlUrwolf++;
            if (anzahlUrwolf == 2) {
                anzahlUrwolf = 1;
                vibrator.vibrate(vibrationsdauer);
            }
            nummerUrwolf.setText(getString(R.string.numberWrapper, anzahlUrwolf));
        } else if (v.getId() == R.id.minusWeisserWerwolf) {
            anzahlWeisserWerwolf--;
            if (anzahlWeisserWerwolf == -1) {
                anzahlWeisserWerwolf = 0;
                vibrator.vibrate(vibrationsdauer);
            }
            nummerWeisserWerwolf.setText(getString(R.string.numberWrapper, anzahlWeisserWerwolf));
        } else if (v.getId() == R.id.plusWeisserWerwolf) {

            anzahlWeisserWerwolf++;
            if (anzahlWeisserWerwolf == 2) {
                anzahlWeisserWerwolf = 1;
                vibrator.vibrate(vibrationsdauer);
            }
            nummerWeisserWerwolf.setText(getString(R.string.numberWrapper, anzahlWeisserWerwolf));
        } else if (v.getId() == R.id.spiel_starten) {

            weiter();
        } else if (v.getId() == R.id.iconAmor) {
            if (infoTextAmor.getVisibility() == View.VISIBLE)
                infoTextAmor.setVisibility(View.GONE);
            else
                infoTextAmor.setVisibility(View.VISIBLE);

        } else if (v.getId() == R.id.iconHexe) {
            if (infoTextHexe.getVisibility() == View.VISIBLE)
                infoTextHexe.setVisibility(View.GONE);
            else
                infoTextHexe.setVisibility(View.VISIBLE);
        } else if (v.getId() == R.id.iconWaechter) {
            if (infoTextWaechter.getVisibility() == View.VISIBLE)
                infoTextWaechter.setVisibility(View.GONE);
            else
                infoTextWaechter.setVisibility(View.VISIBLE);
        } else if (v.getId() == R.id.iconDieb) {
            if (infoTextDieb.getVisibility() == View.VISIBLE)
                infoTextDieb.setVisibility(View.GONE);
            else
                infoTextDieb.setVisibility(View.VISIBLE);
        } else if (v.getId() == R.id.iconSeher) {
            if (infoTextSeher.getVisibility() == View.VISIBLE)
                infoTextSeher.setVisibility(View.GONE);
            else
                infoTextSeher.setVisibility(View.VISIBLE);
        } else if (v.getId() == R.id.iconBuerger) {
            if (infoTextBuerger.getVisibility() == View.VISIBLE)
                infoTextBuerger.setVisibility(View.GONE);
            else
                infoTextBuerger.setVisibility(View.VISIBLE);
        } else if (v.getId() == R.id.iconWerwolf) {
            if (infoTextWerwolf.getVisibility() == View.VISIBLE)
                infoTextWerwolf.setVisibility(View.GONE);
            else
                infoTextWerwolf.setVisibility(View.VISIBLE);
        } else if (v.getId() == R.id.iconJunges) {
            if (infoTextJunges.getVisibility() == View.VISIBLE)
                infoTextJunges.setVisibility(View.GONE);
            else
                infoTextJunges.setVisibility(View.VISIBLE);
        } else if (v.getId() == R.id.iconJaeger) {
            if (infoTextJaeger.getVisibility() == View.VISIBLE)
                infoTextJaeger.setVisibility(View.GONE);
            else
                infoTextJaeger.setVisibility(View.VISIBLE);
        } else if (v.getId() == R.id.iconWeisserWerwolf) {
            if (infoTextWeisserWerwolf.getVisibility() == View.VISIBLE)
                infoTextWeisserWerwolf.setVisibility(View.GONE);
            else
                infoTextWeisserWerwolf.setVisibility(View.VISIBLE);
        } else if (v.getId() == R.id.iconMaedchen) {
            if (infoTextMaedchen.getVisibility() == View.VISIBLE)
                infoTextMaedchen.setVisibility(View.GONE);
            else
                infoTextMaedchen.setVisibility(View.VISIBLE);
        } else if (v.getId() == R.id.iconFloetenspieler) {
            if (infoTextFloetenspieler.getVisibility() == View.VISIBLE)
                infoTextFloetenspieler.setVisibility(View.GONE);
            else
                infoTextFloetenspieler.setVisibility(View.VISIBLE);
        } else if (v.getId() == R.id.iconUrwolf) {
            if (infoTextUrwolf.getVisibility() == View.VISIBLE)
                infoTextUrwolf.setVisibility(View.GONE);
            else
                infoTextUrwolf.setVisibility(View.VISIBLE);
        } else if (v.getId() == R.id.iconRitter) {
            if (infoTextRitter.getVisibility() == View.VISIBLE)
                infoTextRitter.setVisibility(View.GONE);
            else
                infoTextRitter.setVisibility(View.VISIBLE);
        } else if (v.getId() == R.id.iconFreunde) {
            if (infoTextFreunde.getVisibility() == View.VISIBLE)
                infoTextFreunde.setVisibility(View.GONE);
            else
                infoTextFreunde.setVisibility(View.VISIBLE);
        }

        gesamtPer = anzahlAmor + anzahlWaechter + anzahlBuerger + anzahlJunges + anzahlJaeger + anzahlWerwolf + anzahlSeher + anzahlHexe + anzahlDieb + anzahlWeisserWerwolf + anzahlRitter + anzahlFloetenspieler + anzahlFreunde + anzahlMaedchen + anzahlUrwolf;
        gesamtTxt.setText(getString(R.string.combinedWrapper, gesamtPer));
    }


    private void weiter() {
        if (anzahlWerwolf + anzahlWeisserWerwolf + anzahlUrwolf == 0 || anzahlWerwolf + anzahlWeisserWerwolf + anzahlUrwolf == gesamtPer) {
            Toast.makeText(CharacterSelection.this, "ungültige Eingabe", Toast.LENGTH_LONG).show();
            vibrator.vibrate(vibrationsdauer);
        } else {
            Intent intent;
            if (einGeraet) {
                intent = new Intent(this, PlayerManagement.class);
            } else {
                intent = new Intent(this, HostConnectWithPlayers.class);
            }
            intent.putExtra("anzahlAmor", anzahlAmor);
            intent.putExtra("anzahlBuerger", anzahlBuerger);
            intent.putExtra("anzahlWaechter", anzahlWaechter);
            intent.putExtra("anzahlDieb", anzahlDieb);
            intent.putExtra("anzahlHexe", anzahlHexe);
            intent.putExtra("anzahlJaeger", anzahlJaeger);
            intent.putExtra("anzahlJunges", anzahlJunges);
            intent.putExtra("anzahlSeher", anzahlSeher);
            intent.putExtra("anzahlWerwolf", anzahlWerwolf);
            intent.putExtra("anzahlWeisserWerwolf", anzahlWeisserWerwolf);
            intent.putExtra("anzahlRitter", anzahlRitter);
            intent.putExtra("anzahlFloetenspieler", anzahlFloetenspieler);
            intent.putExtra("anzahlFreunde", anzahlFreunde);
            intent.putExtra("anzahlMaedchen", anzahlMaedchen);
            intent.putExtra("anzahlUrwolf", anzahlUrwolf);
            intent.putExtra("gesamtPer", gesamtPer);
            intent.putExtra("EinGeraet", einGeraet);
            startActivity(intent);

            CustomIntent.customType(this, "left-to-right");
        }
    }

    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(this, "right-to-left");
    }

}