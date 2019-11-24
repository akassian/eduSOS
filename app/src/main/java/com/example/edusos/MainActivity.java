package com.example.edusos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ImageButton searchBoxButton;
    private EditText searchBox;
    private Button postButton;

    ArrayList<Question> allQuestions;
    DatabaseReference dbQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchBoxButton = findViewById(R.id.searchBoxButton);
        searchBox = findViewById(R.id.searchBox);
        searchBoxButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = searchBox.getText().toString().trim().toLowerCase();
                searchQuestion(searchText);
            }
        });

        postButton = findViewById(R.id.postButton);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                openPostQuestionActivity();
            }
        });

        dbQuestion = FirebaseDatabase.getInstance().getReference("question");
        dbQuestion.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    allQuestions = new ArrayList<>();
                    for (DataSnapshot ds: dataSnapshot.getChildren()) {
                        Question question = ds.getValue(Question.class);
                        allQuestions.add(question);
                    }
//                    QuestionSearchAdapterClass questionSearchAdapterClass = new QuestionSearchAdapterClass(allQuestions);
//                    openQuestionSearchResultActivity(questionSearchAdapterClass);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void searchQuestion(String searchText) {
        ArrayList<Question> matchedQuestions = new ArrayList<>();
        for (Question question: allQuestions) {
            Boolean match = Boolean.FALSE;
            if (question.getQuestion().toLowerCase().contains(searchText)) {
                matchedQuestions.add(question);
                match = Boolean.TRUE;
            }

            if (!match && question.getAnswer() != null && question.getAnswer().size() > 0) {
                for (int i = 0; i < question.answer.size(); i++) {
                    if (!match && question.getAnswer().get(i).toLowerCase().contains(searchText)) {
                        matchedQuestions.add(question);
                        match = Boolean.TRUE;
                    }
                }
            }
        }
        openQuestionSearchResultActivity(matchedQuestions);
    }



    public void openPostQuestionActivity() {
        Intent intent = new Intent(this, PostQuestionActivity.class);
        startActivity(intent);
    }

    public void onPostClick(View button) {
        Intent myIntent = new Intent(this, PostQuestionActivity.class);
        this.startActivity(myIntent);
    }

    public void openQuestionSearchResultActivity(ArrayList<Question> matchedQuestions) {
        Intent intent = new Intent(this, QuestionSearchResultActivity.class);
        intent.putParcelableArrayListExtra("matchedQuestions", matchedQuestions);

        startActivity(intent);
    }
}
