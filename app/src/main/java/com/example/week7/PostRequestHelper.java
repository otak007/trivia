package com.example.week7;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;



public class PostRequestHelper implements Response.ErrorListener, Response.Listener {

    private Context context;
    private HSCallback callbackActivity;
    private String url = "http://ide50-gabeotagho.legacy.cs50.io:8080/highscore";

    public PostRequestHelper(Context context){
        this.context = context;
    }


    // een method die de request queue maakt etc
    public void postHighscores(HSCallback activity, final HighscoreItem highscore) {
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest postRequest = new StringRequest(Request.Method.POST, url, this, this ){
            // Method to supply parameters to the request
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                params.put("name", highscore.getName());
                params.put("score", Integer.toString(highscore.getScore()));
                return params;
            }
        };
        callbackActivity = activity;
        queue.add(postRequest);

    }

    public interface HSCallback {
        void postedHighscoresSuccess();
        void postedHighscoreFailure(String message);
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        callbackActivity.postedHighscoreFailure(error.getMessage());
    }

    @Override
    public void onResponse(Object response) {
        callbackActivity.postedHighscoresSuccess();
    }

}
