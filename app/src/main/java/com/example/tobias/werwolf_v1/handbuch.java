package com.example.tobias.werwolf_v1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import maes.tech.intentanim.CustomIntent;

public class handbuch extends AppCompatActivity implements View.OnClickListener {


    private Button zurueck;
    private NestedScrollView anleitungScroll;
    private TextView charaktereUeberschrift;
    private TextView ohneKartenUeberschrift;
    private TextView mitKartenUeberschrift;
    private TextView charaktereText;
    private TextView mitKartenText;
    private TextView ohneKartenText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_handbuch);

        zurueck= findViewById(R.id.zurueck);
        zurueck.setOnClickListener(handbuch.this);

        anleitungScroll=findViewById(R.id.anleitungScroll);
        charaktereUeberschrift=findViewById(R.id.charakterUeberschrift);
        ohneKartenUeberschrift=findViewById(R.id.ohneKartenUeberschrift);
        mitKartenUeberschrift=findViewById(R.id.mitKartenUeberschrift);
        charaktereText=findViewById(R.id.charaktereText);
        ohneKartenText=findViewById(R.id.ohneKartenText);
        mitKartenText=findViewById(R.id.mitKartenText);

       // charaktereUeberschrift.setOnClickListener(handbuch.this);
       // ohneKartenUeberschrift.setOnClickListener(handbuch.this);
       // mitKartenUeberschrift.setOnClickListener(handbuch.this);
        charaktereText.setOnClickListener(handbuch.this);
        ohneKartenText.setOnClickListener(handbuch.this);
        mitKartenText.setOnClickListener(handbuch.this);
        //handbuchText.setText("Handbuch\n\nI. Charaktere\nII. Mit Karten\nIII. Ohne Karten\nIV. Spielphase\n\n\nI. Charaktere\n\n");//Werwolf:\n"+getString(R.string.beschreibungWerwolf)+"\n\nBürger:\n"+R.string.beschreibungBuerger+"\n\nAmor:\n"+R.string.beschreibungAmor+"\n\nHexe:\n"+R.string.beschreibungHexe+"\n\nWächter:\n"+R.string.beschreibungWaechter+"\n\nMädchen:\n"+R.string.beschreibungMaedchen+"\n\nSeher:\n"+R.string.beschreibungSeher+"\n\nDieb:\n"+R.string.beschreibungDieb+"\n\nJaeger:\n"+R.string.beschreibungJaeger+"\n\nRitter:\n"+R.string.beschreibungRitter+"\n\nFlötenspieler:\n"+R.string.beschreibungFloetenspieler+"\n\nFreunde:\n"+R.string.beschreibungFreunde+"\n\nJunges:\n"+R.string.beschreibungJunges+"\n\nWeier Werwolf:\n"+R.string.beschreibungWerwolf+"\n\nUr-Wolf:\n"+R.string.beschreibungUrwolf);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.zurueck:
                Intent intent =new Intent(handbuch.this, Startbildschirm.class);
                startActivity(intent);
                CustomIntent.customType(this, "up-to-bottom");
                break;

            case R.id.charaktereText:
               anleitungScroll.smoothScrollTo(0, charaktereUeberschrift.getTop());

                break;

            case R.id.ohneKartenText:
                anleitungScroll.smoothScrollTo(0, ohneKartenUeberschrift.getTop());
                break;

            case R.id.mitKartenText:
                anleitungScroll.smoothScrollTo(0, ohneKartenUeberschrift.getTop());
                break;

            default: break;


        }

    }

    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(this, "up-to-bottom");
    }
}
