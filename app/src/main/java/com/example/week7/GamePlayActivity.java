package com.example.week7;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class GamePlayActivity extends AppCompatActivity implements QuestionRequest.Callback  {


    private TextView question;
    private ListView answersView;
    private ArrayList<String> answers;
    private ArrayList<QuestionItem> questionItems;
    private int score;
    private int questionNumber;
    private int request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        Intent intent = getIntent();
        String name = (String) intent.getSerializableExtra("name");
        Log.d("name", ""+name);

        question = findViewById(R.id.question);
        answersView = findViewById(R.id.answers);
        score = 0;
        questionNumber = 0;
        Log.d("request: ", ""+request);
//        questionRequest(request);
        if (request < 1){
            request++;
            Log.d("new question", "reload");
            new QuestionRequest(getApplicationContext()).getQuestionsArray(this);
        }
        Log.d("request after", ""+request);
        answersView.setOnItemClickListener(new ItemClickListener());
    }

    public void questionRequest(boolean alreadyRequest){
        if (alreadyRequest == false){
            request++;
            Log.d("new question", "reload");
            new QuestionRequest(getApplicationContext()).getQuestionsArray(this);
        }
    }


    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);;

        outState.putInt("question number", questionNumber);
        outState.putInt("already request", request);
    }

    // Preserve the page after rotation
    public void onRestoreInstanceState(Bundle inState) {
        super.onRestoreInstanceState(inState);

        questionNumber = inState.getInt("question number");
        request = inState.getInt("already request");
        Log.d("save request", ""+request);
    }

    @Override
    public void gotQuestionItems(ArrayList<QuestionItem> questions)  {
        questionItems = questions;
        showQuestion(questionNumber);
    }


    @Override
    public void gotQuestionItemsError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void showQuestion(int number){

        question.setText(fromHtml(questionItems.get(number).getQuestion()));
        answers = questionItems.get(number).getIncorrect_answers();

        //int correctAnswerPlace = new Random().nextInt(answers. - 1);
        //Log.d("size", ""+correctAnswerPlace);
        answers.add(questionItems.get(number).getCorrect_answer());
        AnswerAdapter adapter = new AnswerAdapter(this, R.layout.answers, answers);

        answersView.setAdapter(adapter);
    }

    public static Spanned fromHtml(String text){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(text);
        }
    }

    public class ItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            String answer = (String) parent.getItemAtPosition(position);

            if (answer.equals(questionItems.get(questionNumber).getCorrect_answer())) {
                score++;
            }


            if (questionItems.size() - 1 == questionNumber) {

                Intent intent = new Intent(GamePlayActivity.this, HighscoreListActivity.class);
                startActivity(intent);

            }
            else{
                questionNumber++;
                showQuestion(questionNumber);

            }
        }
    }
}
