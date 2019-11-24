package com.example.edusos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class AnswerQuestionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_question);
        TextView subject = (TextView) findViewById(R.id.cardview_subject1);
        TextView question = (TextView) findViewById(R.id.cardview_question1);

        Intent intent = getIntent();

        ArrayList<Question> chosenQuestion = intent.getParcelableArrayListExtra("chosenQuestion");
        Question questionObj = chosenQuestion.get(0);
        String key = intent.getStringExtra("chosenKey");
        //Log.d("KEY_", key);
        subject.setText(questionObj.getSubject());
        question.setText(questionObj.getQuestion());

    }
    public void onSubmit (View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
