package com.example.week7;

import android.content.Context;
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
import java.util.concurrent.ThreadLocalRandom;

public class GamePlayActivity extends AppCompatActivity implements QuestionRequest.Callback, PostRequestHelper.HSCallback  {


    private TextView question;
    private ListView answersView;
    private ArrayList<String> answers;
    private ArrayList<QuestionItem> questionItems;
    private int score, questionNumber, gameDifficulty;
    private AnswerAdapter adapter;
    private String name, url;
    private GameProperties gameProperties;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        Intent intent = getIntent();
        gameProperties = (GameProperties) intent.getSerializableExtra("Game Properties");
        name = gameProperties.getName();
        url = gameProperties.getUrl();
        gameDifficulty = gameProperties.getDifficulty();

        question = findViewById(R.id.question);
        answersView = findViewById(R.id.answers);

        // Question request, only 1 time during the game.
        if (savedInstanceState == null) {

            score = 0;
            questionNumber = 0;

            QuestionRequest qr = new QuestionRequest(getApplicationContext());
            qr.getQuestionsArray(this, url);
        }

        answersView.setOnItemClickListener(new ItemClickListener());
    }

    // save the page
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);;

        outState.putSerializable("question items", questionItems);
        outState.putInt("question number", questionNumber);
        outState.putStringArrayList("answers", answers);

        outState.putInt("score", score);
    }

    // Preserve the page after rotation
    public void onRestoreInstanceState(Bundle inState) {
        super.onRestoreInstanceState(inState);

        score = inState.getInt("score");
        answers = inState.getStringArrayList("answers");
        questionNumber = inState.getInt("question number");
        questionItems = (ArrayList<QuestionItem>) inState.getSerializable("question items");

        question.setText(fromHtml(questionItems.get(questionNumber).getQuestion()));
        adapter = new AnswerAdapter(this, R.layout.answers, answers);
        answersView.setAdapter(adapter);
    }


    @Override
    public void gotQuestionItems(ArrayList<QuestionItem> questions)  {
        questionItems = questions;
        showQuestion(questionNumber);
    }


    // show a message when question request failed
    @Override
    public void gotQuestionItemsError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }


    // Show the questions on the screen
    public void showQuestion(int number){

        question.setText(fromHtml(questionItems.get(number).getQuestion()));
        answers = questionItems.get(number).getIncorrect_answers();

        // add the correct answer on a random place in the possible answers
        int randomNum = ThreadLocalRandom.current().nextInt(0, answers.size());
        answers.add(randomNum,questionItems.get(number).getCorrect_answer());

        adapter = new AnswerAdapter(this, R.layout.answers, answers);
        answersView.setAdapter(adapter);
    }


    public static Spanned fromHtml(String text){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(text);
        }
    }

    @Override
    public void postedHighscoresSuccess() {
        Intent intent = new Intent(GamePlayActivity.this, HighscoreListActivity.class);
        startActivity(intent);
    }

    @Override
    public void postedHighscoreFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public class ItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            String answer = (String) parent.getItemAtPosition(position);

            // When the user pressed the correct score, upgrade the score by 1.
            String correctAnswer = questionItems.get(questionNumber).getCorrect_answer();
            if (answer.equals(correctAnswer)) {
                score++;
                Toast.makeText(GamePlayActivity.this, "Good answer!", Toast.LENGTH_SHORT).show();
            }

            // When the user pressed wrong answer, show the correct answer
            else{
                Toast.makeText(GamePlayActivity.this, "Wrong answer. Good answer: "+fromHtml(correctAnswer), Toast.LENGTH_SHORT).show();
            }

            // When the last question is answered, post the score to the server and open the highscore page
            if (questionItems.size() - 1 == questionNumber) {

                // Not clickable anymore
                answersView.setAdapter(null);
                question.setText("Loading Highscores");

                // Upgrade the score with the chosen difficulty
                score = score * gameDifficulty;
                HighscoreItem highscore = new HighscoreItem(name, score);

                Toast.makeText(GamePlayActivity.this, "SCORE: "+Integer.toString(score), Toast.LENGTH_LONG).show();
                PostRequestHelper pra = new PostRequestHelper(getApplicationContext());
                pra.postHighscores(GamePlayActivity.this, highscore);
            }

            // Show next question
            else{
                questionNumber++;
                showQuestion(questionNumber);
            }
        }
    }
}
