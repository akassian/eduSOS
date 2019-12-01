package com.example.edusos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ExpertSearchActivity extends AppCompatActivity {
    private ImageButton searchBoxButton;
    private EditText searchBox;

    ArrayList<Expert> allExperts;
    ArrayList<String> allExpertKeys;
    DatabaseReference dbExpert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_search);

        searchBoxButton = findViewById(R.id.searchBoxButton);
        searchBox = findViewById(R.id.searchBox);
        searchBoxButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = searchBox.getText().toString().trim().toLowerCase();
                searchExpert(searchText);
            }
        });

        dbExpert = FirebaseDatabase.getInstance().getReference("Experts");
        dbExpert.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    allExperts = new ArrayList<>();
                    allExpertKeys = new ArrayList<>();
                    for (DataSnapshot ds: dataSnapshot.getChildren()) {
                        Expert expert = ds.getValue(Expert.class);
                        String key = ds.getKey();
                        allExperts.add(expert);
                        allExpertKeys.add(key);
                    }
//                    ExpertSearchAdapterClass expertSearchAdapterClass = new ExpertSearchAdapterClass(allExperts);
//                    openExpertSearchResultActivity(expertSearchAdapterClass);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ExpertSearchActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }


    private void searchExpert(String searchText) {
        ArrayList<Expert> matchedExperts = new ArrayList<>();
        ArrayList<String> matchExpertKeys = new ArrayList<>();
        //Boolean match;
        Expert expert;
        searchText = searchText.trim().toLowerCase();
        //for (Expert expert: allExperts) {
        for (int i = 0; i < allExperts.size(); i++) {
            //match = Boolean.FALSE;
            expert = allExperts.get(i);
            if (expert.getSubjects() != null && expert.getSubjects().size() >0) {
                for (int j=0; j< expert.getSubjects().size(); j++) {
                    if (expert.getSubjects().get(j).toLowerCase().contains(searchText)) {
                        matchedExperts.add(allExperts.get(i));
                        Log.d("ACC_NAME", allExperts.get(i).getName());
                        Log.d("ACC_google", allExperts.get(i).getGoogleAccount());
                        matchExpertKeys.add(allExpertKeys.get(i));
                        break;

                    }

                }
            }

        }
        openExpertSearchResultActivity(matchedExperts, matchExpertKeys);
    }
    

//    public void openPostExpertActivity() {
//        Intent intent = new Intent(this, PostExpertActivity.class);
//        startActivity(intent);
//    }

    public void openExpertSearchResultActivity(ArrayList<Expert> matchedExperts, ArrayList<String> matchExpertKeys) {
        Intent intent = new Intent(this, ExpertSearchResultActivity.class);
        intent.putParcelableArrayListExtra("matchedExperts", matchedExperts);
        intent.putStringArrayListExtra("matchExpertKeys", matchExpertKeys);

        startActivity(intent);
    }

    public void onBackClick(View button) {
        Intent myIntent = new Intent(this, MainActivity.class);
        this.startActivity(myIntent);
    }
    
}
