package com.example.edusos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DatabaseReference;

import android.widget.Button;

import java.util.ArrayList;

public class ExpertProfileActivity extends AppCompatActivity {
    DatabaseReference dbExpert;
    Expert expertObj;
//    EditText answerInput;
    String key;
    String name, email, phone, account;
    Double rating, ratePerQuestion;
    int questionAnswered;
    Boolean online;
    GoogleSignInAccount googleAccount;
    ArrayList<String> subjects;

    private Button chatWithExpertBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_profile);



        TextView textViewName= (TextView) findViewById(R.id.cardview_name);
        TextView textViewOnline = (TextView) findViewById(R.id.cardview_online);
        TextView textViewEmail = (TextView) findViewById(R.id.cardview_subjects);
        TextView textViewRating = (TextView) findViewById(R.id.cardview_rating);
        TextView textViewRatePerQuestion = (TextView) findViewById(R.id.cardview_ratePerQuestion);

        TextView textViewQuestionsAnswered = (TextView) findViewById(R.id.cardview_questionAnswered);
        TextView textViewSubjects = (TextView) findViewById(R.id.cardview_subjects);

        Intent intent = getIntent();

        key = intent.getStringExtra("chosenKey");
        Log.d("KEY_", key);

        name = intent.getStringExtra("name");
        account = intent.getStringExtra("googleAcc");
        email  = account + "@gmail.com";
        phone = intent.getStringExtra("phone");
        rating = intent.getDoubleExtra("rating", 4.0);
        ratePerQuestion = intent.getDoubleExtra("ratePerQuestion", 0.0);
        subjects = intent.getStringArrayListExtra("subjects");
        online = intent.getBooleanExtra("online", true);
        questionAnswered = intent.getIntExtra("questionAnswered", 10);

                String subjectStr = "Subjects: \n";
        if (subjects != null && subjects.size() >0) {
            for (String subject: subjects) {
                subjectStr += subject + "\n";

            }
        }

        textViewName.setText(name);
        textViewRatePerQuestion.setText("$"+ ratePerQuestion.toString());
        textViewSubjects.setText(subjectStr);
        if (!online) {
            textViewOnline.setText("Currently not online");
        }
        textViewRating.setText(rating + " / 5");
        textViewQuestionsAnswered.setText("Questions Answered: "+questionAnswered);
        textViewEmail.setText("Email: "+ email);

//        googleAccount = ((EduSOSApplication) this.getApplication()).getAccount();
//
//        if (googleAccount != null) {
//            Log.d("SIGNIN_POST_", googleAccount.getDisplayName() + ",   " + googleAccount.getEmail());
//            welcome.setText("Welcome " + googleAccount.getDisplayName().split(" ")[0] + "!");
//
//        }

        chatWithExpertBtn = findViewById(R.id.chatWithExpertBtn);
        chatWithExpertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCharActivity();
            }
        });

    }

    public void openCharActivity() {
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("googleAcc", account);
        startActivity(intent);
    }


}


