package com.example.whac_a_mole;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Field field;
    private TextView tvLevel;
    private TextView tvScore, tvTime;
    private Button btnStart;
    CountDownTimer countDownTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        field = findViewById(R.id.field);
        tvLevel = findViewById(R.id.tvLevel);
        btnStart = findViewById(R.id.btnStart);
        tvScore = findViewById(R.id.tvScore);
        tvTime = findViewById(R.id.tvTime);

        setEventListeners();
    }

    void setTimer(){
       countDownTimer = new CountDownTimer(30000, 1000) {


            public void onTick(long millisUntilFinished) {

                tvTime.setText(getString(R.string.Left) + " "
                                        + millisUntilFinished / 1000 + " " + "ms");
            }

            public void onFinish() {
                countDownTimer.cancel();
               newActivity(getApplicationContext(),LastActivity.class);
            }
        }
                .start();
    }

    void newActivity(Context context, Class activity){
        Intent intent = new Intent(context, activity);
        startActivity(intent);
    }



    void setEventListeners() {
        btnStart.setOnClickListener(view -> {
            btnStart.setVisibility(View.GONE);
        tvScore.setVisibility(View.GONE);
        field.startGame();
            setTimer();
        });

        field.setListener(listener);
    }

    private final Field.Listener listener = new Field.Listener() {

        @Override
        public void onGameEnded(int score) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    countDownTimer.cancel();
                    newActivity(getApplicationContext(),LastActivity.class);
//                    btnStart.setVisibility(View.VISIBLE);
//                    showScore(score);

                }
            });
        }

        @Override
        public void updateScore(int score) {
            showScore(score);
        }

        @Override
        public void onLevelChange(int level) {
            tvLevel.setText(String.format(getString(R.string.level), level));
        }
    };

    private void showScore(int score) {
        if (tvScore.getVisibility() != View.VISIBLE) {
            tvScore.setVisibility(View.VISIBLE);
        }
        tvScore.setText(getString(R.string.your_score, score));
    }
}