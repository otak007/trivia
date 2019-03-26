package com.example.week7;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;

public class HighscoreAdapter extends ArrayAdapter<HighscoreItem> {

    private ArrayList<HighscoreItem> scores;

    public HighscoreAdapter(Context context, int resource, ArrayList<HighscoreItem> scores) {
        super(context, resource);
        this.scores = scores;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // inflate layout
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.highscore, parent, false);
        }

        // initialize views to be changed
        TextView name = convertView.findViewById(R.id.name);
        TextView score = convertView.findViewById(R.id.score);
        TextView place = convertView.findViewById(R.id.place);

        // get correct information for item
        HighscoreItem convertScore = scores.get(position);

        // Only the highest score will be shown in bold letters
        if (position > 0){
            name.setTypeface(null, Typeface.NORMAL);
            score.setTypeface(null, Typeface.NORMAL);
            place.setTypeface(null, Typeface.NORMAL);
        }

        // set the name, place and score to the view
        name.setText(convertScore.getName());
        score.setText(Integer.toString(convertScore.getScore()));
        place.setText(""+ (position + 1) + ". ");

        return convertView;
    }

    @Override
    public int getCount() {
        return scores.size();
    }
}
