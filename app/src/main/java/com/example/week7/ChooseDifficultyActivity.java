package com.example.week7;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.Serializable;

public class ChooseDifficultyActivity extends AppCompatActivity {

    public String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_difficulty);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
    }

    public void getDifficulty(View view) {

        String url = "";
        int difficultyScaler = 0;

        int id = view.getId();
        switch (id) {
            case R.id.easy:
                url = "https://opentdb.com/api.php?amount=10&difficulty=easy";
                difficultyScaler = 1;
                break;

            case R.id.medium:
                url = "https://opentdb.com/api.php?amount=10&difficulty=medium";
                difficultyScaler = 2;
                break;

            case R.id.hard:
                url = "https://opentdb.com/api.php?amount=10&difficulty=hard";
                difficultyScaler = 3;
                break;
        }

        GameProperties gameProperties = new GameProperties(name, url, difficultyScaler);

        Intent intent = new Intent();
        intent.setClass(ChooseDifficultyActivity.this, GamePlayActivity.class);
        intent.putExtra("Game Properties", (Serializable) gameProperties);
        startActivity(intent);
    }
}
