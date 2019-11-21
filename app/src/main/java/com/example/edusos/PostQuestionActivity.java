package com.example.edusos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PostQuestionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_question);
    }

    public void onBackClick(View button) {
        Intent myIntent = new Intent(this, MainActivity.class);
        this.startActivity(myIntent);
    }
}
