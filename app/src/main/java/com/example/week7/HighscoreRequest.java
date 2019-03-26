package com.example.week7;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class HighscoreRequest implements Response.Listener<JSONArray>, Response.ErrorListener{

    private Context context;
    private ArrayList<HighscoreItem> scoresArray = new ArrayList<HighscoreItem>();
    private HighscoreRequest.Callback activity;


    public interface Callback {
        void gotScores(ArrayList<HighscoreItem> scores) throws JSONException;
        void gotScoresError(String message);
    }

    // Constructor
    public HighscoreRequest(Context context){
        this.context = context;
    }


    void getHighscoreArray(HighscoreRequest.Callback activity){
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "http://ide50-gabeotagho.legacy.cs50.io:8080/highscore",
                null, this, this);
        queue.add(jsonArrayRequest);
        queue.start();
        this.activity = activity;
    }

    // Creates an error message when it's not possible to get the scores
    @Override
    public void onErrorResponse(VolleyError error) {
        activity.gotScoresError(error.getMessage());
    }

    // Save the scores in an arraylist
    @Override
    public void onResponse(JSONArray response) {


        // Get the names and scores and add them as a HighscoreItem to an array
        try {

            for (int i = 0; i < response.length(); i++) {

                JSONObject scoresJson = response.getJSONObject(i);

                String name = scoresJson.getString("name");
                int score = Integer.parseInt(scoresJson.getString("score"));

                // Order the highscores
                int betterScore =0;
                if (scoresArray.size() > 0) {
                    for (int j = 0; j < scoresArray.size(); j++) {
                        if (score < scoresArray.get(i - j - 1).getScore()) {
                            betterScore++;
                        }
                    }
                }

                // Add the highscore to the right place in the array
                scoresArray.add(betterScore, new HighscoreItem(name, score));
            }
            activity.gotScores(scoresArray);
        }
        catch(JSONException e){
            e.printStackTrace();
        }
    }
}
