package com.example.tobias.werwolf_v1;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Spieler_Karte extends AppCompatActivity implements View.OnTouchListener {


    private TextView charakterKarte;
    private SquareLayout layoutKarteSpieler;
    private String rolle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_spieler__karte);

        charakterKarte = findViewById(R.id.charakterKarte);
        layoutKarteSpieler = findViewById(R.id.layoutKarteSpieler);
        charakterKarte.setOnTouchListener(this);

        rolle=getIntent().getExtras().getString("rolle");
        if(rolle==null || rolle.compareTo("")==0)
        {
            rolle="Fehler: keine Rolle übermittelt";
        }
        else {

            switch (rolle) {
                case "werwolf":
                    rolle = "Werwolf";
                    break;
                case "buerger":
                    rolle = "Bürger";
                    break;
                case "amor":
                    rolle = "Amor";
                    break;
                case "hexe":
                    rolle = "Hexe";
                    break;
                case "waechter":
                    rolle = "Wächter";
                    break;
                case "maedchen":
                    rolle = "Mädchen";
                    break;
                case "seher":
                    rolle = "Seher";
                    break;
                case "dieb":
                    rolle = "Dieb";
                    break;
                case "jaeger":
                    rolle = "Jäger";
                    break;
                case "freunde":
                    rolle = "Freunde";
                    break;
                case "junges":
                    rolle = "Junges";
                    break;
                case "urwolf":
                    rolle = "Urwolf";
                    break;
                case "weisserwerwolf":
                    rolle = "weißer Werwolf";
                    break;
                case "floetenspieler":
                    rolle = "Flötenspieler";
                    break;
                case "ritter":
                    rolle = "Ritter";
                    break;
                default:
                    rolle = "Fehler: keine Rolle übermittelt";
                    break;
            }
        }

    }

//todo rolle von SpielerAuswahl übergeben

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(v.getId()==R.id.charakterKarte) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                charakterKarte.setText("");
                charakterKarte.setBackground(getResources().getDrawable(R.drawable.knopf_orange));
                return super.onTouchEvent(event);
            } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                charakterKarte.setText(rolle);
                charakterKarte.setBackground(getResources().getDrawable(R.drawable.knopf_grau));
                return super.onTouchEvent(event);

            } else {
                return false;
            }
        }

        if(v.getId()==R.id.statbildschirmbutton)
        {
            startbildschirmOeffnen();
            return super.onTouchEvent(event);
        }
        else
        {
            return false;
        }
    }

    private void startbildschirmOeffnen()
    {
        Intent intent =new Intent(this, Startbildschirm.class);
        startActivity(intent);
    }
}