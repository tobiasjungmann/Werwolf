package com.example.tobias.werwolf_v1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import maes.tech.intentanim.CustomIntent;


public class Startbildschirm extends AppCompatActivity implements View.OnClickListener {


    private boolean zweiMalzurueck;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startbildschirm);


        Button mitKarten = findViewById(R.id.spielleiterEinzeln);
        mitKarten.setOnClickListener(Startbildschirm.this);
        Button ohneKarten = findViewById(R.id.spieler);
        ohneKarten.setOnClickListener(Startbildschirm.this);
        Button handbuch_knopf = findViewById(R.id.handbuch_knopf);
        handbuch_knopf.setOnClickListener(Startbildschirm.this);
        Button einzelspieler = findViewById(R.id.spielleiterServer);
        einzelspieler.setOnClickListener(Startbildschirm.this);
    }

    @Override
    public void onBackPressed() {
        if (zweiMalzurueck) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            System.exit(0);
        }

        Toast.makeText(Startbildschirm.this, "Um die App zu schließen Taste erneut drücken.", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                zweiMalzurueck = false;
            }
        }, 2000);
        zweiMalzurueck = true;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.spielleiterEinzeln:
                charakterauswahlOeffnenEinGeraet();
                break;

            case R.id.spielleiterServer:
                charakterauswahlOeffnen();
                break;

            case R.id.spieler:
                spielerAuswahlOeffnen();
                break;

            case R.id.handbuch_knopf:
                handbuchOeffnen();
                break;
        }
    }


    private void spielerAuswahlOeffnen() {
        Intent intent = new Intent(Startbildschirm.this, Spieler_Auswahl.class);
        intent.putExtra("Karten", "nein");
        startActivity(intent);
        CustomIntent.customType(this, "left-to-right");
    }


    private void charakterauswahlOeffnenEinGeraet() {
        Intent intent = new Intent(this, charakterauswahl.class);
        intent.putExtra("EinGeraet", true);
        startActivity(intent);
        CustomIntent.customType(this, "left-to-right");
    }

    private void charakterauswahlOeffnen() {
        Intent intent = new Intent(this, charakterauswahl.class);
        intent.putExtra("EinGeraet", false);
        startActivity(intent);
        CustomIntent.customType(this, "left-to-right");
    }

    private void handbuchOeffnen() {
        Intent intent = new Intent(this, handbuch.class);
        startActivity(intent);
        CustomIntent.customType(this, "bottom-to-up");
    }
}


