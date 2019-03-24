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


/*
    Objects of this class can do POST requests with parameters.
*/

public class PostRequestHelper implements Response.ErrorListener, Response.Listener {

    private Context context;
    private HSCallback callbackActivity;

    public PostRequestHelper(Context context){
        this.context = context;
    }


    // een method die de request queue maakt etc
    public void postHighscores(HSCallback activity) {
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest postRequest = new StringRequest(Request.Method.POST, url, this, this ){
            // Method to supply parameters to the request
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                params.put("name", "Minor Programmeren");
                params.put("studentcount", "300");
                return params;
            }
        };
        queue.add(postRequest);
        callbackActivity = activity;
    }

    public interface HSCallback {
        void postedHighscoresSuccess();
        void postedHighscoreFailure(String message);
    }

    private int method;
    private String url = "http://localhost:8080/list";

    @Override
    public void onErrorResponse(VolleyError error) {
        callbackActivity.postedHighscoreFailure(error.getMessage());
    }

    @Override
    public void onResponse(Object response) {

    }

}
