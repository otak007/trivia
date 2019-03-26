package com.example.week7;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;

public class HighscoreListActivity extends AppCompatActivity implements HighscoreRequest.Callback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore_list);

        HighscoreRequest request = new HighscoreRequest(this);
        request.getHighscoreArray(this);
    }

    // Get scores and show them
    @Override
    public void gotScores(ArrayList<HighscoreItem> scores) {

        HighscoreAdapter highscoreAdapter = new HighscoreAdapter(this, R.layout.highscore, scores);
        ListView listView = findViewById(R.id.scores);
        listView.setAdapter(highscoreAdapter);
    }

    // When getting the scores is failed. Show an error message
    @Override
    public void gotScoresError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
