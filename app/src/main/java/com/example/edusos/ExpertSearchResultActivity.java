package com.example.edusos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class ExpertSearchResultActivity extends AppCompatActivity {
    
    private RecyclerView recyclerView;
    private static RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_search_result);

        Intent intent = getIntent();

        ArrayList<Expert> matchedExperts = intent.getParcelableArrayListExtra("matchedExperts");
        Log.d("ACC_getExtra_name",String.valueOf( matchedExperts.get(0).getName()));
        Log.d("ACC_getExtra_google", matchedExperts.get(0).getGoogleAccount());
        ArrayList<String> matchExpertKeys = intent.getStringArrayListExtra("matchExpertKeys");
        adapter = new ExpertSearchAdapterClass(matchedExperts, matchExpertKeys);

        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setAdapter(adapter);
    }

}
