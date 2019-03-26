package com.example.week7;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Go to new screen where the user can fill in his/her name
    public void startGame(View view){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, GetNameActivity.class);
        startActivity(intent);
    }

    // Go to new screen where the scores are visible
    public void showHighscorList(View view){

        Intent intent = new Intent();
        intent.setClass(MainActivity.this, HighscoreListActivity.class);
        startActivity(intent);

    }
}
