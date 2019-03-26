package com.example.week7;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AnswerAdapter extends ArrayAdapter<String> {

    private ArrayList<String> answers;

    public AnswerAdapter(Context context, int resource, ArrayList<String> strings) {
        super(context, resource, strings);
        answers = strings;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.answers, parent, false);
        }

        TextView possibleAnswer = convertView.findViewById(R.id.possibleAnswer);
        String answer = answers.get(position);
        possibleAnswer.setText(answer);

        return convertView;
    }
}
