package com.example.whac_a_mole;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LastActivity extends AppCompatActivity {
    Button btn_replay, btn_menu;
    MainActivity mainActivity;
    TextView tvScore;
    String string_score;
    Bundle arguments;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.last_activity);

        btn_replay = findViewById(R.id.btn_replay);
        btn_menu = findViewById(R.id.btn_menu);
        tvScore = findViewById(R.id.tvScore);
        arguments = getIntent().getExtras();
        string_score = arguments.getString("score");
        tvScore.setText(string_score);

        mainActivity = new MainActivity();
        Replay();
        openMenu();
    }

    public void Replay(){
        btn_replay.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        });

    }

    public void openMenu(){
        btn_menu.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), App.class);
            startActivity(intent);
        });

    }

}
