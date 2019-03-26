package com.example.week7;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class GetNameActivity extends AppCompatActivity {

    private  EditText nameText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_name);

        nameText = findViewById(R.id.name);
    }

    // If the name is filled in, save the name and start the game. Otherwise the user will be asked to fill in a name
    public void start(View view){

        String name = nameText.getText().toString();

        if (name.equals("")){
            Toast.makeText(this, "Please fill in your name", Toast.LENGTH_SHORT).show();
        }

        else{

            Intent intent = new Intent(GetNameActivity.this, ChooseDifficultyActivity.class);
            intent.putExtra("name", name);
            startActivity(intent);
        }
    }
}
