package com.example.edusos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AnswerQuestionActivity1 extends AppCompatActivity {
    ArrayList<Question> allQuestions;
    DatabaseReference dbQuestion;
    private EditText subjectInput;
    private Button subjectButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_question1);
        subjectInput = (EditText) findViewById(R.id.subjectInput);
        subjectButton = (Button) findViewById(R.id.subjectButton);

        subjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subjectText = subjectInput.getText().toString().trim().toLowerCase();
                searchQuestion(subjectText);
            }
        });

        dbQuestion = FirebaseDatabase.getInstance().getReference("question");
        dbQuestion.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    allQuestions = new ArrayList<>();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Question question = ds.getValue(Question.class);
                        allQuestions.add(question);
                    }
//                    QuestionSearchAdapterClass questionSearchAdapterClass = new QuestionSearchAdapterClass(allQuestions);
//                    openQuestionSearchResultActivity(questionSearchAdapterClass);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AnswerQuestionActivity1.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

        private void searchQuestion(String subjectText) {
            ArrayList<Question> matchedQuestions = new ArrayList<>();
            for (Question question: allQuestions) {
                Boolean match = Boolean.FALSE;
                if (question.getSubject().toLowerCase().contains(subjectText) || question.getQuestion().toLowerCase().contains(subjectText)) {
                    matchedQuestions.add(question);
                    //match = Boolean.TRUE;
                }

            }
        }

    }

