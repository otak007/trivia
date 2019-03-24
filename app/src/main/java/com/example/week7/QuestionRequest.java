package com.example.week7;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class QuestionRequest implements Response.Listener<JSONObject>, Response.ErrorListener{

    private Context context;
    private ArrayList<QuestionItem> questionsArray = new ArrayList<QuestionItem>();
    private Callback callbackActivity;

    public interface Callback {
        void gotQuestionItems(ArrayList<QuestionItem> questions) throws JSONException;
        void gotQuestionItemsError(String message);
    }

    // Constructor
    public QuestionRequest(Context context){
        this.context = context;
    }


    void getQuestionsArray(Callback activity){
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("https://opentdb.com/api.php?amount=10",
                null, this, this);
        queue.add(jsonObjectRequest);
        callbackActivity = activity;
    }

    // Creates an error message when it's not possible to get the categories
    @Override
    public void onErrorResponse(VolleyError error) {
        callbackActivity.gotQuestionItemsError(error.getMessage());
    }

    // Save the questions in an arraylist
    @Override
    public void onResponse(JSONObject response) {

        JSONArray jsonArray;


        try {
            jsonArray = response.getJSONArray("results");

            for (int i = 0; i < jsonArray.length(); i++) {

                ArrayList<String> incorrect_answers = new ArrayList<String>();

                JSONObject questionItemJson = jsonArray.getJSONObject(i);
//                Log.d("hole object", "" +questionItemJson);
                String category = questionItemJson.getString("category");
                String type = questionItemJson.getString("type");
                String difficulty = questionItemJson.getString("difficulty");
                String question = questionItemJson.getString("question");
                String correct_answer = questionItemJson.getString("correct_answer");
                JSONArray incorrect_answersJson = questionItemJson.getJSONArray("incorrect_answers");
//                Log.d("correct", ""+correct_answer);
//                Log.d("incorrect true", ""+incorrect_answersJson);

                for (int j = 0; j < incorrect_answersJson.length(); j++) {

                    incorrect_answers.add(incorrect_answersJson.getString(j));

                }

                questionsArray.add(new QuestionItem(category, type, difficulty, question, correct_answer, incorrect_answers));

            }

            callbackActivity.gotQuestionItems(questionsArray);
        }
        catch(JSONException e){
            e.printStackTrace();
        }
    }
}
