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

public class Manual extends AppCompatActivity implements View.OnClickListener {


    private NestedScrollView manualScrollView;
    private TextView characterHeadline;
    private TextView withoutCardsHeadline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_manual);

        Button backButton = findViewById(R.id.backmanual);
        backButton.setOnClickListener(Manual.this);

        manualScrollView = findViewById(R.id.manualScrollView);
        characterHeadline = findViewById(R.id.characterHeadline);
        withoutCardsHeadline = findViewById(R.id.withoutCardsHeadline);
        TextView characterText = findViewById(R.id.characterText);
        TextView withoutCardsText = findViewById(R.id.withoutCardsText);
        TextView withCardsText = findViewById(R.id.withCardsText);


        characterText.setOnClickListener(Manual.this);
        withoutCardsText.setOnClickListener(Manual.this);
        withCardsText.setOnClickListener(Manual.this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.backmanual) {
            Intent intent = new Intent(Manual.this, StartScreen.class);
            startActivity(intent);
            CustomIntent.customType(this, "up-to-bottom");
        } else if (v.getId() == R.id.characterText) {
            manualScrollView.smoothScrollTo(0, characterHeadline.getTop());
        } else if (v.getId() == R.id.withoutCardsText) {
            manualScrollView.smoothScrollTo(0, withoutCardsHeadline.getTop());
        } else if (v.getId() == R.id.withCardsText) {
            manualScrollView.smoothScrollTo(0, withoutCardsHeadline.getTop());
        }
    }


    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(this, "up-to-bottom");
    }
}
