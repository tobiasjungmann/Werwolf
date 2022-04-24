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


    private NestedScrollView anleitungScroll;
    private TextView charaktereUeberschrift;
    private TextView ohneKartenUeberschrift;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_handbuch);

        Button zurueck = findViewById(R.id.zurueck);
        zurueck.setOnClickListener(handbuch.this);

        anleitungScroll = findViewById(R.id.anleitungScroll);
        charaktereUeberschrift = findViewById(R.id.charakterUeberschrift);
        ohneKartenUeberschrift = findViewById(R.id.ohneKartenUeberschrift);
        TextView charaktereText = findViewById(R.id.charaktereText);
        TextView ohneKartenText = findViewById(R.id.ohneKartenText);
        TextView mitKartenText = findViewById(R.id.mitKartenText);


        charaktereText.setOnClickListener(handbuch.this);
        ohneKartenText.setOnClickListener(handbuch.this);
        mitKartenText.setOnClickListener(handbuch.this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.zurueck) {
            Intent intent = new Intent(handbuch.this, Startbildschirm.class);
            startActivity(intent);
            CustomIntent.customType(this, "up-to-bottom");
        } else if (v.getId() == R.id.charaktereText) {
            anleitungScroll.smoothScrollTo(0, charaktereUeberschrift.getTop());
        } else if (v.getId() == R.id.ohneKartenText) {
            anleitungScroll.smoothScrollTo(0, ohneKartenUeberschrift.getTop());
        } else if (v.getId() == R.id.mitKartenText) {
            anleitungScroll.smoothScrollTo(0, ohneKartenUeberschrift.getTop());
        }
    }


    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(this, "up-to-bottom");
    }
}
