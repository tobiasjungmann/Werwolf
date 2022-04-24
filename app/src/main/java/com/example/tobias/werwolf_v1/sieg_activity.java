package com.example.tobias.werwolf_v1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class sieg_activity extends AppCompatActivity implements View.OnClickListener {

    private Button startbildschirmButton;
    private TextView siegerText;
    private String sieger;
    private boolean zweiMalzurueck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sieg_activity);

        sieger=getIntent().getExtras().getString("sieger");
        startbildschirmButton= findViewById(R.id.statbildschirmbutton);
        startbildschirmButton.setOnClickListener(sieg_activity.this);

        siegerText=findViewById(R.id.siegerText);

        switch(sieger)
        {
            case "floetenspieler":siegerText.setText("Der Flötenspieler hat alle anderen Spieler verzaubert! Er hat gewonnen!");break;
            case "buerger":siegerText.setText("Alle Werwölfe sind tot. Die Bürger haben gewonnen! Ab jetzt können sie wieder ruhig schlafen.");break;
            case "werwoelfe":siegerText.setText("Die Werwölfe sind in Überzahl. Das Dorf liegt in ihrer Herrschaft, damit haben sie gewonnen!");break;
            case "liebespaar":siegerText.setText("Das Liebespaar hat sich gegen alle Anderen durchgesetzt! Sie sind wirklich wie füreinander geschaffen.");break;
            case "ww":siegerText.setText("Der Weiße Werwolf hat das Spiel alleine gewonnen.");break;
            default:siegerText.setText("Fehler");break;
        }
    }

    public void onBackPressed() {
        if(zweiMalzurueck==true)
        {
            Intent intent =new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            System.exit(0);
        }

        Toast.makeText(sieg_activity.this, "Um die App zu schließen Taste erneut drücken.", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                zweiMalzurueck=false;
            }
        },2000);
        zweiMalzurueck=true;
    }


    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.statbildschirmbutton:
                startbildschirmOeffnen();
                break;
        }
    }


    private void startbildschirmOeffnen()
    {
        Intent intent =new Intent(this, Startbildschirm.class);
        startActivity(intent);
    }
}
