package com.example.edusos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AnswerQuestionActivity extends AppCompatActivity {
    DatabaseReference dbQuestion;
    Question questionObj;
    EditText answerInput;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_question);

        dbQuestion = FirebaseDatabase.getInstance().getReference("question");

        TextView subject = (TextView) findViewById(R.id.cardview_subject1);
        TextView question = (TextView) findViewById(R.id.cardview_question1);
        answerInput = (EditText) findViewById(R.id.cardview_answer1);

        Intent intent = getIntent();

        ArrayList<Question> chosenQuestion = intent.getParcelableArrayListExtra("chosenQuestion");
        questionObj = chosenQuestion.get(0);
        key = intent.getStringExtra("chosenKey");
        Log.d("KEY_", key);
        subject.setText(questionObj.getSubject());
        question.setText(questionObj.getQuestion());

    }
    public void onSubmit (View view) {
        ArrayList<String> answer = questionObj.getAnswer();
        String answerStr = answerInput.getText().toString();
        answer.add(answerStr);


        DatabaseReference updateData = FirebaseDatabase.getInstance()
                .getReference("question")
                .child(key);
        updateData.child("answer").setValue(answer);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
