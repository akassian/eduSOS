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

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;





public class MainActivity extends AppCompatActivity {

    private ImageButton searchBoxButton;
    private EditText searchBox;
    private Button postButton;

    ArrayList<Question> allQuestions;
    DatabaseReference dbQuestion;

    // For Sign in
    GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 007;


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

    // For Sign in
    public void onLoginClick(View button) {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
        signIn();
    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        //startActivityForResult(signInIntent,1);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            Log.d("SIGNIN_!: ", account.getDisplayName() + ",   " + account.getEmail());

            //String personName = account.getDisplayName();

//            if (account.getPhotoUrl()!= null) {
//                String personPhotoUrl = account.getPhotoUrl().toString();
//            }

            //String email = account.getEmail();

            //txt_welcome.setText("Welcome "+personName+"\n"+ email);

            updateUI(account);   // Signed in successfully, show authenticated UI.
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("SIGNIN_", "signInResult:failed code=" + e.getStatusCode());
            Log.d("SIGNIN_", "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    private void updateUI(GoogleSignInAccount account) {
        // For example, display Welcome name if account is not null
        if (account != null) {
            //Toast.makeText(this, account.getDisplayName(), Toast. LENGTH_LONG).show();
            Log.d("SIGNIN_UI", account.getDisplayName() + ",   " + account.getEmail());
        }


    }

}
