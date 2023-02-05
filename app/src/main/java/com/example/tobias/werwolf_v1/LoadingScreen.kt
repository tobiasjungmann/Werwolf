package com.example.tobias.werwolf_v1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class LoadingScreen extends AppCompatActivity {

    private static final int SPLASH_TIME_OUT = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_loadingscreen);

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(LoadingScreen.this, StartScreen.class);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            startActivity(intent);

            finish();
        }, SPLASH_TIME_OUT);
    }
}
