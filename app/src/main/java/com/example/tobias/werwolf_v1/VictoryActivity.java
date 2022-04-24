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

public class VictoryActivity extends AppCompatActivity implements View.OnClickListener {

    private boolean twoTimesBackPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_victory);

        String winner = getIntent().getExtras().getString("sieger");
        Button sartScreenButton = findViewById(R.id.victoryStartScreenButton);
        sartScreenButton.setOnClickListener(VictoryActivity.this);

        TextView winnerText = findViewById(R.id.siegerText);

        switch (winner) {
            case "floetenspieler":
                winnerText.setText(R.string.VictoryFlute);
                break;
            case "buerger":
                winnerText.setText(R.string.VictoryCiticens);
                break;
            case "werwoelfe":
                winnerText.setText(R.string.VictoryWolfes);
                break;
            case "liebespaar":
                winnerText.setText(R.string.VictoryLovers);
                break;
            case "ww":
                winnerText.setText(R.string.victoryWhiteWolf);
                break;
            default:
                winnerText.setText(R.string.victoryError);
                break;
        }
    }

    public void onBackPressed() {
        if (twoTimesBackPressed) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            System.exit(0);
        }

        Toast.makeText(VictoryActivity.this, R.string.pressBackAgain, Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(() -> twoTimesBackPressed = false, 2000);
        twoTimesBackPressed = true;
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.victoryStartScreenButton)
            openStartScreen();

    }


    private void openStartScreen() {
        Intent intent = new Intent(this, StartScreen.class);
        startActivity(intent);
    }
}
